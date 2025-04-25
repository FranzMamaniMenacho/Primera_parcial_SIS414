package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Reserv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservController {

    private static final Logger logger = LoggerFactory.getLogger(ReservController.class);
    private final List<Reserv> reservas = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Reserv>> getAllReservas() {
        logger.info("Obteniendo listado completo de reservas");
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable Long id) {
        logger.info("Consultando reserva con ID: {}", id);
        Optional<Reserv> reserva = reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reserva.isPresent()) {
            return ResponseEntity.ok(reserva.get());
        } else {
            logger.warn("Reserva con ID {} no encontrada", id);
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> createReserva(@RequestBody Reserv reserva) {
        if (reserva.getId() == null) {
            logger.error("Error: ID es requerido para crear reserva");
            return ResponseEntity.badRequest().body("El ID es requerido para crear una reserva");
        }

        if (reservas.stream().anyMatch(r -> r.getId().equals(reserva.getId()))) {
            logger.error("Error: Reserva con ID {} ya existe", reserva.getId());
            return ResponseEntity.badRequest().body("El ID de la reserva ya está registrado");
        }

        reservas.add(reserva);
        logger.info("Reserva creada exitosamente con ID: {}", reserva.getId());
        return ResponseEntity.ok(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @RequestBody Reserv reserva) {
        Optional<Reserv> existingReserva = reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (existingReserva.isEmpty()) {
            logger.warn("No existe reserva con ID {} para actualizar", id);
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        Reserv toUpdate = existingReserva.get();
        toUpdate.setCliente(reserva.getCliente());
        toUpdate.setHabitacion(reserva.getHabitacion());
        toUpdate.setFechaEntrada(reserva.getFechaEntrada());
        toUpdate.setFechaSalida(reserva.getFechaSalida());
        toUpdate.setPago(reserva.getPago());

        logger.info("Reserva con ID {} actualizada exitosamente", id);
        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        boolean removed = reservas.removeIf(r -> r.getId().equals(id));
        if (removed) {
            logger.info("Reserva con ID {} eliminada correctamente", id);
            return ResponseEntity.ok("Reserva eliminada exitosamente");
        }
        logger.warn("No se encontró reserva con ID {} para eliminar", id);
        return ResponseEntity.status(404).body("Reserva no encontrada");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateReserva(@PathVariable Long id, @RequestBody Reserv updates) {
        Optional<Reserv> reservaOpt = reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (reservaOpt.isEmpty()) {
            logger.warn("Reserva con ID {} no encontrada para actualización", id);
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        Reserv reserva = reservaOpt.get();

        if (updates.getCliente() != null) {
            reserva.setCliente(updates.getCliente());

            MaZorca, [24/4/2025 21:39]
            logger.info("Cliente actualizado para reserva ID: {}", id);
        }
        if (updates.getHabitacion() != null) {
            reserva.setHabitacion(updates.getHabitacion());
            logger.info("Habitación actualizada para reserva ID: {}", id);
        }
        if (updates.getFechaEntrada() != null) {
            reserva.setFechaEntrada(updates.getFechaEntrada());
            logger.info("Fecha de entrada actualizada para reserva ID: {}", id);
        }
        if (updates.getFechaSalida() != null) {
            reserva.setFechaSalida(updates.getFechaSalida());
            logger.info("Fecha de salida actualizada para reserva ID: {}", id);
        }
        if (updates.getPago() != null) {
            reserva.setPago(updates.getPago());
            logger.info("Pago actualizado para reserva ID: {}", id);
        }

        logger.info("Reserva con ID {} actualizada parcialmente", id);
        return ResponseEntity.ok(reserva);
    }
}