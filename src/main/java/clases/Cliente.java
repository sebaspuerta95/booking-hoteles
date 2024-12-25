package src.main.java.clases;

public class Cliente {

    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String email;
    private String nacionalidad;
    private String telefono;

    public Cliente(String nombre, String apellido, String fechaNacimiento, String email, String nacionalidad, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

}
