package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Pago;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final List<Pago> pagos = new ArrayList<>();
    
    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        return ResponseEntity.ok(pagos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        return findPagoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        pagos.add(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago updatedPago) {
        Optional<Pago> optionalPago = findPagoById(id);

        if (optionalPago.isPresent()) {
            int index = pagos.indexOf(optionalPago.get());
            pagos.set(index, updatedPago);
            return ResponseEntity.ok(updatedPago);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Pago> patchPago(@PathVariable Long id, @RequestBody Pago partial) {
        Optional<Pago> optionalPago = findPagoById(id);

        if (optionalPago.isPresent()) {
            Pago existing = optionalPago.get();

            if (partial.getMetodo() != null) existing.setMetodo(partial.getMetodo());
            if (partial.getEstado() != null) existing.setEstado(partial.getEstado());
            if (partial.getMonto() != 0) existing.setMonto(partial.getMonto());

            return ResponseEntity.ok(existing);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePago(@PathVariable Long id) {
        boolean removed = pagos.removeIf(p -> p.getId().equals(id));

        if (removed) {
            return ResponseEntity.ok("Pago eliminado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado.");
        }
    }
    private Optional<Pago> findPagoById(Long id) {
        return pagos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}