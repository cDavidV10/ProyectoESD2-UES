package modelo;

public class Direccion {

    private int idDireccion;
    private String zona;
    private String numCasa;
    private Distrito distrito;

    public Direccion(int idDireccion, String zona, String numCasa, Distrito distrito) {
        this.idDireccion = idDireccion;
        this.zona = zona;
        this.numCasa = numCasa;
        this.distrito = distrito;
    }

    public Direccion() {
    }
    
    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }
}
