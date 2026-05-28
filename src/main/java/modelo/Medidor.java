package modelo;

import java.util.ArrayList;

public class Medidor {
    private int id;
    private String codigo;
    private String diametroNomila;
    private String unidadMedida;
    // ? Clases Relacionadas
    private Direccion direccion;
    private ArrayList<Lectura> lecturas;

    public Medidor() {
    }

    public Medidor(int id, String diametroNomila, String unidadMedida) {
        this.id = id;
        this.diametroNomila = diametroNomila;
        this.unidadMedida = unidadMedida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiametroNomila() {
        return diametroNomila;
    }

    public void setDiametroNomila(String diametroNomila) {
        this.diametroNomila = diametroNomila;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Lectura> getLecturas() {
        return lecturas;
    }

    public void setLecturas(ArrayList<Lectura> lecturas) {
        this.lecturas = lecturas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    

}
