package FirstParcial.sis414.FirstParcial.entity;

public class Habitacion {

    private Long id;
    private TipoHabitacion tipo;
    private boolean disponible;
    private double precioNoche;

    public Habitacion(Long id,TipoHabitacion tipo, boolean disponible, double precioNoche)
    {
        this.id = id;
        this.tipo = tipo;
        this.disponible = disponible;
        this.precioNoche = precioNoche;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public TipoHabitacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoHabitacion tipo) {
        this.tipo = tipo;
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

//ignor este comentario

