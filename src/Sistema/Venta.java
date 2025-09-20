package Sistema;

import java.util.ArrayList;
import java.util.Date;

public abstract class Venta {
    protected String id;
    protected int local;
    protected String empleado;
    protected Date fecha;
    protected ArrayList<Object[]> productos;
    protected String comentario;
    protected double subtotal;
    protected double descuento;
    protected double importe_final;
    
    public Venta() {
        fecha = new Date();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public int getLocal() {
        return local;
    }
    
    public void setLocal(int local) {
        this.local = local;
    }
    
    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Object[]> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Object[]> productos) {
        this.productos = productos;
    }
    
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
    public double getImporteFinal() {
        return importe_final;
    }

    public void setImporteFinal(double importe_final) {
        this.importe_final = importe_final;
    }
}