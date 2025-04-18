package FirstParcial.sis414.FirstParcial.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import FirstParcial.sis414.FirstParcial.entity.TipoHabitacion;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tipohabitaciones")
public class TipoHabitacionController {

    private static final Logger logger = LoggerFactory.getLogger(TipoHabitacionController.class);
    private List<TipoHabitacion> tipos = new ArrayList<>();

    public TipoHabitacionController() {
        TipoHabitacion tipo1 = new TipoHabitacion(1L, "Sencilla", 2, 50);
        tipos.add(tipo1);
        logger.info("Inicializando el controlador con un tipo de habitación de ejemplo.");
    }

    @GetMapping
    public List<TipoHabitacion> getTipos() {
        logger.info("Solicitando lista de tipos de habitaciones.");
        return tipos;
    }

    @GetMapping("/{id}")
    public TipoHabitacion getTipo(@PathVariable Long id) {
        logger.info("Solicitando tipo de habitación con ID: {}", id);
        for (TipoHabitacion tipo : tipos) {
            if (tipo.getId().equals(id)) {
                logger.info("Tipo de habitación encontrado: {}", tipo.getDescripcion());
                return tipo;
            }
        }
        logger.warn("Tipo de habitación con ID {} no encontrado.", id);
        return null;
    }

    @PostMapping
    public TipoHabitacion addTipo(@RequestBody TipoHabitacion tipo) {
        logger.info("Agregando nuevo tipo de habitación con ID: {}", tipo.getId());
        tipos.add(tipo);
        return tipo;
    }

    @PutMapping("/{id}")
    public TipoHabitacion updateTipo(@PathVariable Long id, @RequestBody TipoHabitacion tipo) {
        logger.info("Actualizando tipo de habitación con ID: {}", id);
        for (int i = 0; i < tipos.size(); i++) {
            if (tipos.get(i).getId().equals(id)) {
                tipos.set(i, tipo);
                logger.info("Tipo de habitación con ID {} actualizado.", id);
                return tipo;
            }
        }
        logger.warn("No se encontró tipo de habitación con ID {} para actualizar.", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteTipo(@PathVariable Long id) {
        logger.info("Intentando eliminar tipo de habitación con ID: {}", id);
        boolean removed = tipos.removeIf(t -> t.getId().equals(id));
        if (removed) {
            logger.info("Tipo de habitación con ID {} eliminado con éxito.", id);
            return "Tipo de habitación eliminado con éxito.";
        } else {
            logger.error("No se pudo eliminar el tipo de habitación con ID {}.", id);
            return "No se encontró el tipo de habitación para eliminar.";
        }
    }
    
    @PatchMapping("/{id}")
    public TipoHabitacion patchTipo(@PathVariable Long id, @RequestBody TipoHabitacion partialTipo) {
        logger.info("Actualización parcial para tipo de habitación con ID: {}", id);
        for (TipoHabitacion tipo : tipos) {
            if (tipo.getId().equals(id)) {
                if (partialTipo.getDescripcion() != null) {
                    tipo.setDescripcion(partialTipo.getDescripcion());
                    logger.info("Descripción de tipo de habitación actualizada.");
                }
                if (partialTipo.getCapacidadHabitacion() != 0) {
                    tipo.setCapacidadHabitacion(partialTipo.getCapacidadHabitacion());
                    logger.info("Capacidad de habitación actualizada.");
                }
                if (partialTipo.getPrecioBase() != 0) {
                    tipo.setPrecioBase(partialTipo.getPrecioBase());
                    logger.info("Precio base actualizado.");
                }
                return tipo;
            }
        }
        logger.warn("No se encontró tipo de habitación con ID {} para actualización parcial.", id);
        return null;
    }
}
