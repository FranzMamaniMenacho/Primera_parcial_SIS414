package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Personal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalController.class);
    private final List<Personal> personalList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Personal>> getAllPersonal() {
        logger.info("Solicitando todos los registros de personal.");
        return ResponseEntity.ok(personalList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personal> getPersonalById(@PathVariable Long id) {
        logger.info("Buscando personal con ID: {}", id);
        return findPersonalById(id)
                .map(personal -> {
                    logger.info("Personal encontrado: {}", personal);
                    return ResponseEntity.ok(personal);
                })
                .orElseGet(() -> {
                    logger.warn("Personal no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Personal> createPersonal(@RequestBody Personal personal) {
        personalList.add(personal);
        logger.info("Nuevo personal agregado: {}", personal);
        return ResponseEntity.status(201).body(personal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personal> updatePersonal(@PathVariable Long id, @RequestBody Personal updated) {
        logger.info("Intentando actualizar personal con ID: {}", id);
        return findPersonalById(id)
                .map(existing -> {
                    int index = personalList.indexOf(existing);
                    personalList.set(index, updated);
                    logger.info("Personal actualizado: {}", updated);
                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() -> {
                    logger.warn("Personal no encontrado para actualización con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Personal> patchPersonal(@PathVariable Long id, @RequestBody Personal partial) {
        logger.info("personal con ID: {}", id);
        return findPersonalById(id)
                .map(existing -> {
                    if (partial.getNombre() != null) existing.setNombre(partial.getNombre());
                    if (partial.getRol() != null) existing.setRol(partial.getRol());
                    if (partial.getIdEmpleado() != null) existing.setIdEmpleado(partial.getIdEmpleado());

                    logger.info("Personal parcialmente actualizado: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    logger.warn("Personal no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonal(@PathVariable Long id) {
        logger.info("eliminar personal con ID: {}", id);
        boolean removed = personalList.removeIf(p -> p.getId().equals(id));

        if (removed) {
            logger.info("Personal eliminado con ID: {}", id);
            return ResponseEntity.ok("Personal eliminado con éxito.");
        } else {
            logger.warn("No se encontró personal con ID: {} para eliminar.", id);
            return ResponseEntity.status(404).body("Personal no encontrado.");
        }
    }

    private Optional<Personal> findPersonalById(Long id) {
        return personalList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}