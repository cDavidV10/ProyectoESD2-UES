package modelo;

import java.time.LocalDate;

public class Pago {
    private int id;
    private LocalDate fechaPago;
    private String estado;
    // ? Clases Relacionadas
    private Factura factura;

    public Pago() {
    }

    public Pago(int id, LocalDate fechaPago, String estado) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    
}
