package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Factura implements Comparable<Factura> {
    private int id;
    private LocalDate fechaLimite;
    private BigDecimal mora;
    private BigDecimal montoConsumo;
    private BigDecimal montoServicio;
    private BigDecimal montoNeto;
    private BigDecimal montoTotal;
    // ? Clases Relacionadas
    private Empleado empleado;
    private Pago pago;
    // Correcciones
    private Lectura lectura;

    public Factura() {
    }

    public Factura(int id) {
        this.id = id;
    }
    

    public Factura(int id, LocalDate fechaLimite, BigDecimal mora, BigDecimal montoConsumo, BigDecimal montoServicio,
            BigDecimal montoNeto, BigDecimal montoTotal) {
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

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public BigDecimal getMontoConsumo() {
        return montoConsumo;
    }

    public void setMontoConsumo(BigDecimal montoConsumo) {
        this.montoConsumo = montoConsumo;
    }

    public BigDecimal getMontoServicio() {
        return montoServicio;
    }

    public void setMontoServicio(BigDecimal montoServicio) {
        this.montoServicio = montoServicio;
    }

    public BigDecimal getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(BigDecimal montoNeto) {
        this.montoNeto = montoNeto;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
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

    @Override
    public int compareTo(Factura o) {
        return Integer.compare(this.id, o.id);
    }
}
