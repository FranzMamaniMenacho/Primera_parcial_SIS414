package FirstParcial.sis414.FirstParcial.entity;

public class Parqueo {
    private Long id;
    private boolean disponible;
    private double precioPorNoche;
    private String marca;
    private String color;
    private String placa;

    public Parqueo(Long id, boolean disponible, double precioPorNoche, String marca, String color, String placa) {
        this.id = id;
        this.disponible = disponible;
        this.precioPorNoche = precioPorNoche;
        this.marca = marca;
        this.color = color;
        this.placa = placa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
