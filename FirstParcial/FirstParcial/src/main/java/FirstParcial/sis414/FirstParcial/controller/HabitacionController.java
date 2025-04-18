package FirstParcial.sis414.FirstParcial.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import FirstParcial.sis414.FirstParcial.entity.Habitacion;
import FirstParcial.sis414.FirstParcial.entity.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private List<Habitacion> habitaciones = new ArrayList<>();

    public HabitacionController() {
        TipoHabitacion tipo1 = new TipoHabitacion(1L, "Sencilla", 2, 50);
        Habitacion habitacion = new Habitacion(1L, tipo1, true, 50);
        habitaciones.add(habitacion);
        logger.info("Inicializando el controlador con una habitación de ejemplo.");
    }

    @GetMapping
    public List<Habitacion> getHabitaciones() {
        logger.info("Solicitando lista de habitaciones.");
        return habitaciones;
    }

    @GetMapping("/{id}")
    public Habitacion getHabitacion(@PathVariable Long id) {
        logger.info("Solicitando habitación con ID: {}", id);
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId().equals(id)) {
                logger.info("Habitación encontrada: {}", habitacion.getId());
                return habitacion;
            }
        }
        logger.warn("Habitación con ID {} no encontrada.", id);
        return null;
    }

    @PostMapping
    public Habitacion addHabitacion(@RequestBody Habitacion habitacion) {
        logger.info("Agregando nueva habitación con ID: {}", habitacion.getId());
        habitaciones.add(habitacion);
        return habitacion;
    }

    @PutMapping("/{id}")
    public Habitacion updateHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        logger.info("Actualizando habitación con ID: {}", id);
        for (int i = 0; i < habitaciones.size(); i++) {
            if (habitaciones.get(i).getId().equals(id)) {
                habitaciones.set(i, habitacion);
                logger.info("Habitación con ID {} actualizada.", id);
                return habitacion;
            }
        }
        logger.warn("No se encontró habitación con ID {} para actualizar.", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteHabitacion(@PathVariable Long id) {
        logger.info("Intentando eliminar habitación con ID: {}", id);
        boolean removed = habitaciones.removeIf(h -> h.getId().equals(id));
        if (removed) {
            logger.info("Habitación con ID {} eliminada con éxito.", id);
            return "Habitación eliminada con éxito.";
        } else {
            logger.error("No se pudo eliminar la habitación con ID {}.", id);
            return "No se encontró la habitación para eliminar.";
        }
    }
    
    @PatchMapping("/{id}")
    public Habitacion patchHabitacion(@PathVariable Long id, @RequestBody Habitacion partialHabitacion) {
        logger.info("Actualización parcial para habitación con ID: {}", id);
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId().equals(id)) {
                if (partialHabitacion.getTipo() != null) {
                    habitacion.setTipo(partialHabitacion.getTipo());
                    logger.info("Tipo de habitación actualizado.");
                }
                if (partialHabitacion.isDisponible() != habitacion.isDisponible()) {
                    habitacion.setDisponible(partialHabitacion.isDisponible());
                    logger.info("Disponibilidad de la habitación actualizada.");
                }
                if (partialHabitacion.getPrecioNoche() != 0) {
                    habitacion.setPrecioNoche(partialHabitacion.getPrecioNoche());
                    logger.info("Precio por noche actualizado.");
                }
                return habitacion;
            }
        }
        logger.warn("No se encontró habitación con ID {} para actualización parcial.", id);
        return null;
    }
}
