package Sistema;

import java.util.ArrayList;

public class Proveedor {
    private int codigo;
    private String nombre;
    private String telefono;
    private ArrayList<Integer> productos;
    private double descuento;
    
    public Proveedor() {
        codigo = -1;
        productos = new ArrayList<>();
    }
    
    public Proveedor(int codigo, String nombre, String telefono, double descuento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.descuento = descuento;
        productos = new ArrayList<>();
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Integer> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Integer> productos) {
        this.productos = productos;
    }

    public double getDescuento() {
        return descuento;
    }
    
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
