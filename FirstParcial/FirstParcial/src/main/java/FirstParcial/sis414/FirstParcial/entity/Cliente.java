package FirstParcial.sis414.FirstParcial.model;
public class Cliente {
    private Long id;
    private String nombres;
    private String apellidos;
    private String ci;
    private String telefono;
    private List<Reserva> reservas;

    public Cliente(Long id, String nombres, String apellidos, String ci, String telefono){
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ci = ci;
        this.telefono = telefono;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
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

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}



}