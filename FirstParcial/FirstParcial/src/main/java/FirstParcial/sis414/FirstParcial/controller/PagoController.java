package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Pago;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import FirstParcial.sis414.FirstParcial.entity.Pago;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
    private final List<Pago> pagos = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        logger.info("Solicitando lista de todos los pagos.");
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> getPagoById(@PathVariable Long id) {
        logger.info("Buscando pago con ID: {}", id);
        return findPagoById(id)
                .map(pago -> {
                    logger.info("Pago encontrado: {}", pago);
                    return ResponseEntity.ok(pago);
                })
                .orElseGet(() -> {
                    logger.warn("Pago con ID {} no encontrado.", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PostMapping
    public ResponseEntity<Pago> createPago(@RequestBody Pago pago) {
        pagos.add(pago);
        logger.info("Nuevo pago creado: {}", pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> updatePago(@PathVariable Long id, @RequestBody Pago updatedPago) {
        logger.info("Actualizando pago con ID: {}", id);
        return findPagoById(id)
                .map(existing -> {
                    int index = pagos.indexOf(existing);
                    pagos.set(index, updatedPago);
                    logger.info("Pago actualizado: {}", updatedPago);
                    return ResponseEntity.ok(updatedPago);
                })
                .orElseGet(() -> {
                    logger.warn("Pago con ID {} no encontrado para actualización.", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pago> patchPago(@PathVariable Long id, @RequestBody Pago partial) {
        logger.info(" pago con ID: {}", id);
        return findPagoById(id)
                .map(existing -> {
                    if (partial.getMetodo() != null) existing.setMetodo(partial.getMetodo());
                    if (partial.getEstado() != null) existing.setEstado(partial.getEstado());
                    if (partial.getMonto() != 0) existing.setMonto(partial.getMonto());

                    logger.info("Pago parcialmente actualizado: {}", existing);
                    return ResponseEntity.ok(existing);
                })
                .orElseGet(() -> {
                    logger.warn("Pago con ID {} no encontrado.", id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePago(@PathVariable Long id) {
        logger.info("Intentando eliminar pago con ID: {}", id);
        boolean removed = pagos.removeIf(p -> p.getId().equals(id));

        if (removed) {
            logger.info("Pago con ID {} eliminado.", id);
            return ResponseEntity.ok("Pago eliminado.");
        } else {
            logger.warn("Pago con ID {} no encontrado para eliminar.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado.");
        }
    }

    private Optional<Pago> findPagoById(Long id) {
        return pagos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
}