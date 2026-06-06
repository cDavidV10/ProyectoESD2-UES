package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lectura implements Comparable<Lectura> {

    private int id;
    private int consumo;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    // ? Clases Relacionadas
    private Medidor medidor;
    private ArrayList<Factura> facturas;

    public Lectura() {
    }

    public Lectura(int id, int consumo, LocalDate fechaInicial, LocalDate fechaFinal) {
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

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
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

    @Override
    public String toString() {
        // Para la lectura vacia
        if (this.medidor == null) {
            return "Seleccione...";
        }
        
        return "Lectura N° " + this.id + " [" + this.medidor.getCodigo() + "]";
    }

    @Override
    public int compareTo(Lectura otra) {
        return Integer.compare(this.id, otra.getId());
    }
}
