package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Habitacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private List<Habitacion> habitaciones = new ArrayList<>();

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
        habitaciones.add(habitacion);
        logger.info("Se ha creado una nueva habitación con ID: {}", habitacion.getId());
        return habitacion;
    }

    @PutMapping("/{id}")
    public Habitacion updateHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
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
        boolean removed = habitaciones.removeIf(habitacion -> habitacion.getId().equals(id));
        if (removed) {
            logger.info("Habitación con ID {} eliminada con éxito.", id);
            return "Habitación eliminada con éxito.";
        } else {
            logger.error("No se pudo eliminar la habitación con ID {}. No encontrada.", id);
            return "Habitación no encontrada.";
        }
    }

    @PatchMapping("/{id}")
    public Habitacion patchHabitacion(@PathVariable Long id, @RequestBody Habitacion partialHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId().equals(id)) {
                if (partialHabitacion.getTipoHabitacion() != null) {
                    habitacion.setTipoHabitacion(partialHabitacion.getTipoHabitacion());
                    logger.info("Tipo de habitación actualizado para ID: {}", id);
                }
                if (partialHabitacion.isDisponible() != habitacion.isDisponible()) {
                    habitacion.setDisponible(partialHabitacion.isDisponible());
                    logger.info("Disponibilidad de habitación actualizada para ID: {}", id);
                }
                if (partialHabitacion.getPrecioNoche() != 0) {
                    habitacion.setPrecioNoche(partialHabitacion.getPrecioNoche());
                    logger.info("Precio de habitación actualizado para ID: {}", id);
                }
                return habitacion;
            }
        }
        logger.warn("No se encontró habitación con ID {} para actualizar parcialmente.", id);
        return null;
    }

}
