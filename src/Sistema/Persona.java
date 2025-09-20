package Sistema;

import java.util.Date;

public abstract class Persona {
    protected String dni;
    protected String nombres;
    protected String apellidos;
    protected String telefono;
    protected String estado_civil;
    protected String direccion;
    protected Date fecha_nacimiento;
    
    public Persona() {
    }
    
    public Persona(String dni, String nombres, String apellidos, String telefono, String estado_civil, String direccion, Date fecha_nacimiento) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.estado_civil = estado_civil;
        this.direccion = direccion;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstadoCivil() {
        return estado_civil;
    }

    public void setEstadoCivil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public Date getFechaNacimiento() {
        return fecha_nacimiento;
    }

    public void setFechaNacimiento(Date edad) {
        this.fecha_nacimiento = edad;
    }
    
    public String getNombresApellidos() {
        return nombres + " " + apellidos;
    }
}
