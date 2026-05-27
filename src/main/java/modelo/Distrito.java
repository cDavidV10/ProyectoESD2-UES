package modelo;

public class Distrito {
    private int idDistrito;
    private String nombre;
    private Municipio municipio;

    public Distrito(int idDistrito, String nombre, Municipio municipio) {
        this.idDistrito = idDistrito;
        this.nombre = nombre;
        this.municipio = municipio;
    }

    public Distrito() {
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
