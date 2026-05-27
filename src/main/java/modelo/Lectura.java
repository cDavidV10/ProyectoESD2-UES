package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lectura {
    private int id;
    private double consumo;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    // ? Clases Relacionadas
    private Medidor medidor;
    private ArrayList<Factura> facturas;

    public Lectura() {
    }

    public Lectura(int id, double consumo, LocalDate fechaInicial, LocalDate fechaFinal) {
        this.id = id;
        this.consumo = consumo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public LocalDate getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Medidor getMedidor() {
        return medidor;
    }

    public void setMedidor(Medidor medidor) {
        this.medidor = medidor;
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }
    
    

}
