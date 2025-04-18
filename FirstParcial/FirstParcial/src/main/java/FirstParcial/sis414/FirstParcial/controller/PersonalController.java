package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Personal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    private final List<Personal> personalList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Personal>> getAllPersonal() {
        return ResponseEntity.ok(personalList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personal> getPersonalById(@PathVariable Long id) {
        return findPersonalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Personal> createPersonal(@RequestBody Personal personal) {
        personalList.add(personal);
        return ResponseEntity.status(201).body(personal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Personal> updatePersonal(@PathVariable Long id, @RequestBody Personal updated) {
        return findPersonalById(id).map(existing -> {
            int index = personalList.indexOf(existing);
            personalList.set(index, updated);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Personal> patchPersonal(@PathVariable Long id, @RequestBody Personal partial) {
        return findPersonalById(id).map(existing -> {
            if (partial.getNombre() != null) existing.setNombre(partial.getNombre());
            if (partial.getRol() != null) existing.setRol(partial.getRol());
            if (partial.getIdEmpleado() != null) existing.setIdEmpleado(partial.getIdEmpleado());
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonal(@PathVariable Long id) {
        boolean removed = personalList.removeIf(p -> p.getId().equals(id));
        return removed ?
                ResponseEntity.ok("Personal eliminado con éxito.") :
                ResponseEntity.status(404).body("Personal no encontrado.");
    }
    private Optional<Personal> findPersonalById(Long id) {
        return personalList.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}