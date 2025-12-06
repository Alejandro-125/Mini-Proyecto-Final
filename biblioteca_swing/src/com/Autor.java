package com;

public class Autor {
    String nombre;
    String fechaNacimiento;
    String pais;
    boolean vivo;

    public Autor(String nombre, String fechaNacimiento, String pais, boolean vivo) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.vivo = vivo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}