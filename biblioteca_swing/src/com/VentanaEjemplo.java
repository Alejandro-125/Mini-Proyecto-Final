package com;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaEjemplo extends JFrame {

    private List<Autor> listaAutores;
    private List<Libro> coleccionLibros;
    private JTextArea areaResultados;

    public VentanaEjemplo() {
        listaAutores = new ArrayList<>();
        coleccionLibros = new ArrayList<>();

        setTitle("Gestión de Biblioteca");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = crearPanelSuperior();
        JPanel panelCentral = crearPanelCentral();

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(230, 240, 250));

        JButton btnAddAutor = new JButton("1. Agregar Autor");
        JButton btnAddLibro = new JButton("2. Agregar Libro");
        JButton btnVerAutor = new JButton("Buscar por Autor");
        JButton btnVerGenero = new JButton("Buscar por Género");
        JButton btnVerTodo = new JButton("Ver Todo");

        btnAddAutor.addActionListener(e -> {
            JTextField campoNombre = new JTextField();
            JTextField campoFecha = new JTextField();
            JTextField campoPais = new JTextField();
            JCheckBox checkVivo = new JCheckBox("¿Vive actualmente?");

            Object[] mensaje = {
                "Nombre:", campoNombre,
                "Fecha Nacimiento:", campoFecha,
                "País:", campoPais,
                "Estado:", checkVivo
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Autor", JOptionPane.OK_CANCEL_OPTION);
            
            if (opcion == JOptionPane.OK_OPTION && !campoNombre.getText().isEmpty()) {
                Autor nuevoAutor = new Autor(
                    campoNombre.getText(), 
                    campoFecha.getText(), 
                    campoPais.getText(), 
                    checkVivo.isSelected()
                );
                listaAutores.add(nuevoAutor);
                areaResultados.setText(">>> Autor registrado con éxito: " + nuevoAutor.nombre);
            }
        });

        btnAddLibro.addActionListener(e -> {
            if (listaAutores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "¡Error! Primero debes registrar al menos un Autor.");
                return;
            }

            JTextField campoTitulo = new JTextField();
            JTextField campoGenero = new JTextField();
            JTextField campoAnio = new JTextField();
            
            Autor[] autoresArray = listaAutores.toArray(new Autor[0]);
            JComboBox<Autor> comboAutores = new JComboBox<>(autoresArray);

            Object[] mensaje = {
                "Título del Libro:", campoTitulo,
                "Género:", campoGenero,
                "Año Publicación:", campoAnio,
                "Seleccionar Autor:", comboAutores
            };

            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Nuevo Libro", JOptionPane.OK_CANCEL_OPTION);

            if (opcion == JOptionPane.OK_OPTION && !campoTitulo.getText().isEmpty()) {
                try {
                    int anio = Integer.parseInt(campoAnio.getText());
                    Autor autorSeleccionado = (Autor) comboAutores.getSelectedItem();
                    Libro nuevoLibro = new Libro(campoTitulo.getText(), campoGenero.getText(), anio, autorSeleccionado);
                    coleccionLibros.add(nuevoLibro);
                    areaResultados.setText(">>> Libro agregado a la colección:\n" + nuevoLibro);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El año debe ser un número válido.");
                }
            }
        });

        btnVerAutor.addActionListener(e -> {
            String busqueda = JOptionPane.showInputDialog("Escribe el nombre del autor a buscar:");
            if (busqueda != null && !busqueda.isEmpty()) {
                List<Libro> resultados = coleccionLibros.stream()
                    .filter(l -> l.autorPrincipal.nombre.toLowerCase().contains(busqueda.toLowerCase()))
                    .collect(Collectors.toList());
                mostrarResultados(resultados, "Resultados para autor: " + busqueda);
            }
        });

        btnVerGenero.addActionListener(e -> {
            String busqueda = JOptionPane.showInputDialog("Escribe el género a buscar (ej. Novela):");
            if (busqueda != null && !busqueda.isEmpty()) {
                List<Libro> resultados = coleccionLibros.stream()
                    .filter(l -> l.genero.equalsIgnoreCase(busqueda))
                    .collect(Collectors.toList());
                mostrarResultados(resultados, "Resultados para género: " + busqueda);
            }
        });
        
        btnVerTodo.addActionListener(e -> mostrarResultados(coleccionLibros, "Colección Completa"));

        panel.add(btnAddAutor);
        panel.add(btnAddLibro);
        panel.add(btnVerAutor);
        panel.add(btnVerGenero);
        panel.add(btnVerTodo);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel etiqueta = new JLabel("Consola de Biblioteca");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultados.setText("Bienvenido. Usa los botones superiores para gestionar la biblioteca.");
        
        JScrollPane scroll = new JScrollPane(areaResultados);

        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }
    
    private void mostrarResultados(List<Libro> libros, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ").append(titulo).append(" ===\n\n");
        
        if (libros.isEmpty()) {
            sb.append("No se encontraron libros.");
        } else {
            for (Libro libro : libros) {
                sb.append("- ").append(libro).append("\n");
            }
        }
        areaResultados.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaEjemplo::new);
    }
}