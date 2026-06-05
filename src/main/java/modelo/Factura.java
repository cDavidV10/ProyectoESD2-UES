package modelo;

import java.time.LocalDate;

public class Factura {
    private int id;
    private LocalDate fechaLimite;
    private double mora;
    private double montoConsumo;
    private double montoServicio;
    private double montoNeto;
    private double montoTotal;
    // ? Clases Relacionadas
    private Empleado empleado;
    private Pago pago;
    // Correcciones
    private Lectura lectura;

    public Factura() {
    }

    public Factura(int id, LocalDate fechaLimite, double mora, double montoConsumo, double montoServicio, double montoNeto, double montoTotal) {
        this.id = id;
        this.fechaLimite = fechaLimite;
        this.mora = mora;
        this.montoConsumo = montoConsumo;
        this.montoServicio = montoServicio;
        this.montoNeto = montoNeto;
        this.montoTotal = montoTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public double getMora() {
        return mora;
    }

    public void setMora(double mora) {
        this.mora = mora;
    }

    public double getMontoConsumo() {
        return montoConsumo;
    }

    public void setMontoConsumo(double montoConsumo) {
        this.montoConsumo = montoConsumo;
    }

    public double getMontoServicio() {
        return montoServicio;
    }

    public void setMontoServicio(double montoServicio) {
        this.montoServicio = montoServicio;
    }

    public double getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(double montoNeto) {
        this.montoNeto = montoNeto;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Lectura getLectura() {
        return lectura;
    }

    public void setLectura(Lectura lectura) {
        this.lectura = lectura;
    }
}
