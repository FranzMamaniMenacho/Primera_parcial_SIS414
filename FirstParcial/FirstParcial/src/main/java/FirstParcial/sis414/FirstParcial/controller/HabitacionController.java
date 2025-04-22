package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Habitacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(HabitacionController.class);
    private List<Habitacion> habitaciones = new ArrayList<>();

    // Obtener todas las habitaciones
    @GetMapping
    public List<Habitacion> getHabitaciones() {
        logger.info("Solicitando lista de habitaciones.");
        return habitaciones;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> getHabitacion(@PathVariable Long id) {
        logger.info("Solicitando habitación con ID: {}", id);
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId().equals(id)) {
                logger.info("Habitación encontrada: {}", habitacion.getId());
                return ResponseEntity.ok(habitacion);
            }
        }
        logger.warn("Habitación con ID {} no encontrada.", id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Habitacion addHabitacion(@RequestBody Habitacion habitacion) {
        if (habitacion.getId() == null) {
            habitacion.setId(generateId());
        }
        habitaciones.add(habitacion);
        logger.info("Se ha creado una nueva habitación con ID: {}", habitacion.getId());
        return habitacion;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> updateHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        if (habitaciones.isEmpty()) {
            logger.warn("No hay habitaciones para actualizar.");
            return ResponseEntity.status(404).body(null);
        }

        for (int i = 0; i < habitaciones.size(); i++) {
            Habitacion existingHabitacion = habitaciones.get(i);

            if (existingHabitacion.getId().equals(id)) {
                existingHabitacion.setTipoHabitacion(habitacion.getTipoHabitacion());
                existingHabitacion.setDisponible(habitacion.isDisponible());
                existingHabitacion.setPrecioNoche(habitacion.getPrecioNoche());

                logger.info("Habitación con ID {} actualizada: {}", id, existingHabitacion);

                return ResponseEntity.ok(existingHabitacion);
            }
        }

        logger.warn("No se encontró habitación con ID {} para actualizar.", id);
        return ResponseEntity.status(404).body(null);  // 404 Not Found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHabitacion(@PathVariable Long id) {
        boolean removed = habitaciones.removeIf(habitacion -> habitacion.getId().equals(id));
        if (removed) {
            logger.info("Habitación con ID {} eliminada con éxito.", id);
            return ResponseEntity.ok("Habitación eliminada con éxito.");
        } else {
            logger.warn("No se pudo eliminar la habitación con ID {}. No encontrada.", id);
            return ResponseEntity.status(404).body("Habitación no encontrada.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Habitacion> patchHabitacion(@PathVariable Long id, @RequestBody Habitacion partialHabitacion) {
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
                if (partialHabitacion.getPrecioNoche() > 0) {
                    habitacion.setPrecioNoche(partialHabitacion.getPrecioNoche());
                    logger.info("Precio de habitación actualizado para ID: {}", id);
                }
                return ResponseEntity.ok(habitacion);
            }
        }
        logger.warn("No se encontró habitación con ID {} para actualizar parcialmente.", id);
        return ResponseEntity.status(404).body(null);
    }

    private Long generateId() {
        return (long) (habitaciones.size() + 1);
    }
}
