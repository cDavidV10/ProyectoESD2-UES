package modelo;

import java.util.ArrayList;

public class Municipio {
    private int id;
    private String nombre;
    // ? Clases Relacionada
    private ArrayList<Distrito> distritos;

    public Municipio() {
    }

    public Municipio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(ArrayList<Distrito> distritos) {
        this.distritos = distritos;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
