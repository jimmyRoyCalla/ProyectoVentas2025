package Sistema;

import java.util.Date;

public class Empleado extends Persona {
    private String cargo;
    private double salario;
    private Integer local_asignado;
    
    public Empleado() {
    }
    
    public Empleado(String dni, String nombres, String apellidos, String telefono, String estado_civil,
                    String direccion, Date fecha_nacimiento, String cargo, double salario, Integer local_asignado) {
        super(dni, nombres, apellidos, telefono, estado_civil, direccion, fecha_nacimiento);
        this.cargo = cargo;
        this.salario = salario;
        this.local_asignado = local_asignado;
    }
    
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Integer getLocalAsignado() {
        return local_asignado;
    }

    public void setLocalAsignado(Integer local_asignado) {
        this.local_asignado = local_asignado;
    }
}
