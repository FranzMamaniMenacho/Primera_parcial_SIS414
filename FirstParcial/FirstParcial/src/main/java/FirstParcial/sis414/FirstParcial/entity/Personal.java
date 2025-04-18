public class Personal {
    private String nombre;
    private String rol;
    private String idEmpleado;

    public Personal(String nombre, String rol, String idEmpleado) {
        this.nombre = nombre;
        this.rol = rol;
        this.idEmpleado = idEmpleado;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }
}