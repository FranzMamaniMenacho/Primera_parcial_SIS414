package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Pago;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private static final Logger logger = LoggerFactory.getLogger(PagoController.class);
    private final List<Pago> pagos = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        logger.info("Obteniendo listado completo de pagos");
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPagoById(@PathVariable Long id) {
        logger.info("Consultando pago con ID: {}", id);
        Optional<Pago> pago = pagos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (pago.isPresent()) {
            return ResponseEntity.ok(pago.get());
        } else {
            logger.warn("Pago con ID {} no encontrado", id);
            return ResponseEntity.status(404).body("Pago no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> createPago(@RequestBody Pago pago) {
        if (pago.getId() == null) {
            logger.error("Error: ID es requerido para crear pago");
            return ResponseEntity.badRequest().body("El ID es requerido para crear un pago");
        }

        if (pagos.stream().anyMatch(p -> p.getId().equals(pago.getId()))) {
            logger.error("Error: Pago con ID {} ya existe", pago.getId());
            return ResponseEntity.badRequest().body("El ID del pago ya está registrado");
        }

        pagos.add(pago);
        logger.info("Pago creado exitosamente con ID: {}", pago.getId());
        return ResponseEntity.ok(pago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePago(@PathVariable Long id, @RequestBody Pago pago) {
        Optional<Pago> existingPago = pagos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (existingPago.isEmpty()) {
            logger.warn("No existe pago con ID {} para actualizar", id);
            return ResponseEntity.status(404).body("Pago no encontrado");
        }

        Pago toUpdate = existingPago.get();
        toUpdate.setMetodo(pago.getMetodo());
        toUpdate.setEstado(pago.getEstado());
        toUpdate.setMonto(pago.getMonto());
        toUpdate.setFecha(pago.getFecha());

        logger.info("Pago con ID {} actualizado exitosamente", id);
        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePago(@PathVariable Long id) {
        boolean removed = pagos.removeIf(p -> p.getId().equals(id));
        if (removed) {
            logger.info("Pago con ID {} eliminado correctamente", id);
            return ResponseEntity.ok("Pago eliminado exitosamente");
        }
        logger.warn("No se encontró pago con ID {} para eliminar", id);
        return ResponseEntity.status(404).body("Pago no encontrado");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdatePago(@PathVariable Long id, @RequestBody Pago updates) {
        Optional<Pago> pagoOpt = pagos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (pagoOpt.isEmpty()) {
            logger.warn("Pago con ID {} no encontrado para actualización", id);
            return ResponseEntity.status(404).body("Pago no encontrado");
        }

        Pago pago = pagoOpt.get();

        if (updates.getMetodo() != null) {
            pago.setMetodo(updates.getMetodo());
            logger.info("Método actualizado para pago ID: {}", id);
        }
        if (updates.getEstado() != null) {
            pago.setEstado(updates.getEstado());
            logger.info("Estado actualizado para pago ID: {}", id);
        }
        if (updates.getMonto() > 0) {
            pago.setMonto(updates.getMonto());
            logger.info("Monto actualizado para pago ID: {}", id);
        }
        if (updates.getFecha() != null) {
            pago.setFecha(updates.getFecha());
            logger.info("Fecha actualizada para pago ID: {}", id);
        }

        logger.info("Pago con ID {} actualizado parcialmente", id);
        return ResponseEntity.ok(pago);
    }
}