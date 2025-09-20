package Sistema;

import java.util.ArrayList;

public class Local {
    private int codigo;
    private String direccion;
    private String telefono;
    private ArrayList<Integer> productos;
    private ArrayList<String> empleados;
    private String comentario;
    
    public Local() {
        productos = new ArrayList<>();
        empleados  = new ArrayList<>();
    }
    
    public Local(int codigo, String direccion, String telefono, String comentario) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.comentario = comentario;
        productos = new ArrayList<>();
        empleados  = new ArrayList<>();
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public ArrayList<String> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<String> empleados) {
        this.empleados = empleados;
    }
    
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
