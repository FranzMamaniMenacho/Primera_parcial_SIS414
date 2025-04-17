package FirstParcial.sis414.FirstParcial.model;

public class TipoHabitacion {
    private Long id;
    private String descripcion;
    private int capacidadHabitacion;
    private double precioBase;

    public TipoHabitacion(Long id, String descripcion,int capacidadHabitacion,double precioBase)
    {
        this.id = id;
        this.descripcion = descripcion;
        this.capacidadHabitacion = capacidadHabitacion;
        this.precioBase =precioBase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadHabitacion() {
        return capacidadHabitacion;
    }

    public void setCapacidadHabitacion(int capacidadHabitacion) {
        this.capacidadHabitacion = capacidadHabitacion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
}
