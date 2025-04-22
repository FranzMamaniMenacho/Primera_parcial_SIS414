package FirstParcial.sis414.FirstParcial.entity;

public class Habitacion {
    private Long id;
    private String tipoHabitacion;
    private boolean disponible;
    private double precioNoche;

    public Habitacion(Long id, String tipoHabitacion, boolean disponible, double precioNoche) {
        this.id = id;
        this.tipoHabitacion = tipoHabitacion;
        this.disponible = disponible;
        this.precioNoche = precioNoche;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }
}
