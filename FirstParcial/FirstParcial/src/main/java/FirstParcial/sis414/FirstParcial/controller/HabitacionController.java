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

    // Obtener una habitación por ID
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
        return ResponseEntity.notFound().build();  // Retorna 404 Not Found si no se encuentra
    }

    // Crear una nueva habitación
    @PostMapping
    public Habitacion addHabitacion(@RequestBody Habitacion habitacion) {
        // Si el ID no se proporciona, lo generamos
        if (habitacion.getId() == null) {
            habitacion.setId(generateId()); // Generamos un ID único
        }
        habitaciones.add(habitacion);
        logger.info("Se ha creado una nueva habitación con ID: {}", habitacion.getId());
        return habitacion;
    }

    // Actualizar una habitación existente
    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> updateHabitacion(@PathVariable Long id, @RequestBody Habitacion habitacion) {
        // Verificamos si la lista está vacía
        if (habitaciones.isEmpty()) {
            logger.warn("No hay habitaciones para actualizar.");
            return ResponseEntity.status(404).body(null);  // Retornamos un 404 si no hay habitaciones
        }

        // Iteramos por la lista de habitaciones
        for (int i = 0; i < habitaciones.size(); i++) {
            Habitacion existingHabitacion = habitaciones.get(i);

            // Comprobamos si el ID coincide
            if (existingHabitacion.getId().equals(id)) {
                // Si encontramos la habitación, actualizamos los campos
                existingHabitacion.setTipoHabitacion(habitacion.getTipoHabitacion());
                existingHabitacion.setDisponible(habitacion.isDisponible());
                existingHabitacion.setPrecioNoche(habitacion.getPrecioNoche());

                // Registramos la actualización
                logger.info("Habitación con ID {} actualizada: {}", id, existingHabitacion);

                // Retornamos la habitación actualizada con un estado 200 OK
                return ResponseEntity.ok(existingHabitacion);
            }
        }

        // Si no encontramos la habitación con el ID, devolvemos un 404 Not Found
        logger.warn("No se encontró habitación con ID {} para actualizar.", id);
        return ResponseEntity.status(404).body(null);  // 404 Not Found
    }

    // Eliminar una habitación
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

    // Actualizar parcialmente una habitación (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Habitacion> patchHabitacion(@PathVariable Long id, @RequestBody Habitacion partialHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId().equals(id)) {
                // Solo actualizamos los campos que no son nulos
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
        return ResponseEntity.status(404).body(null);  // 404 Not Found
    }

    // Método para generar un ID único para habitaciones si es necesario
    private Long generateId() {
        return (long) (habitaciones.size() + 1); // Este es un ejemplo simple de generación de ID
    }
}
