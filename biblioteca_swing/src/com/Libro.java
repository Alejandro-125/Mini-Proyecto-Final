package com;

public class Libro {
    String titulo;
    String genero;
    int anio;
    Autor autorPrincipal;

    public Libro(String titulo, String genero, int anio, Autor autorPrincipal) {
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.autorPrincipal = autorPrincipal;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + " | Autor: " + autorPrincipal.nombre + " | Género: " + genero + " | Año: " + anio;
    }
}