package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contrato implements Comparable<Contrato>{

    private int id;
    private BigDecimal tarifa;
    private String tipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    //Clases relacionadas
    private Cliente cliente;
    private Medidor medidor;

    public Contrato() {
    }

    public Contrato(int id) {
        this.id = id;
    }

    
    public Contrato(int id, BigDecimal tarifa, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.id = id;
        this.tarifa = tarifa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Contrato(int id, String tipo, BigDecimal tarifa, LocalDate fechaInicio, LocalDate fechaFin,
            Cliente cliente, Medidor medidor) {
        this.id = id;
        this.tipo = tipo;
        this.tarifa = tarifa;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cliente = cliente;
        this.medidor = medidor;
    }

    public Contrato(int id, BigDecimal tarifa, String tipo, LocalDate fechaInicio, LocalDate fechaFin, String estado, Cliente cliente, Medidor medidor) {
        this.id = id;
        this.tarifa = tarifa;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.cliente = cliente;
        this.medidor = medidor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Medidor getMedidor() {
        return medidor;
    }

    public void setMedidor(Medidor medidor) {
        this.medidor = medidor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int compareTo(Contrato otro) {
        return Integer.compare(this.getId(), otro.getId());
    }

   
}
