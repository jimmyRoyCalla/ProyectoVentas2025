package Sistema;

import java.util.Date;

public class Cliente extends Persona {
    private String membresia;
    
    public Cliente() {
    }
    
    public Cliente(String dni, String nombres, String apellidos, String telefono, String estado_civil,
                    String direccion, Date fecha_nacimiento, String membresia) {
        super(dni, nombres, apellidos, telefono, estado_civil, direccion, fecha_nacimiento);
        this.membresia = membresia;
    }

    public String getMembresia() {
        return membresia;
    }

    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }
}
