package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Reserv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservController {
    private static final Logger logger = LoggerFactory.getLogger(ReservController.class);
    private final List<Reserv> reservas = new ArrayList<>();

    @GetMapping
    public List<Reserv> getReservas() {
        logger.info("Solicitando lista de reservas.");
        return reservas;
    }

    @GetMapping("/{id}")
    public Reserv getReserva(@PathVariable Long id) {
        logger.info("Solicitando reserva con ID: {}", id);
        return reservas.stream()
                .filter(reserva -> reserva.getId().equals(id))
                .findFirst()
                .orElseGet(() -> {
                    logger.warn("Reserva con ID {} no encontrada.", id);
                    return null;
                });
    }

    @PostMapping
    public Reserv addReserva(@RequestBody Reserv reserva) {
        reservas.add(reserva);
        logger.info("Reserva agregada con ID: {}", reserva.getId());
        return reserva;
    }

    @PutMapping("/{id}")
    public Reserv updateReserva(@PathVariable Long id, @RequestBody Reserv reserva) {
        logger.info("Actualizando reserva con ID: {}", id);
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(id)) {
                reservas.set(i, reserva);
                logger.info("Reserva con ID {} actualizada.", id);
                return reserva;
            }
        }
        logger.warn("No se encontró reserva con ID {} para actualizar.", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Reserv patchReserva(@PathVariable Long id, @RequestBody Reserv partial) {
        logger.info("Actualización parcial para reserva con ID: {}", id);
        for (Reserv reserva : reservas) {
            if (reserva.getId().equals(id)) {
                if (partial.getCliente() != null) {
                    reserva.setCliente(partial.getCliente());
                    logger.info("Cliente actualizado.");
                }
                if (partial.getHabitacion() != null) {
                    reserva.setHabitacion(partial.getHabitacion());
                    logger.info("Habitación actualizada.");
                }
                if (partial.getFechaEntrada() != null) {
                    reserva.setFechaEntrada(partial.getFechaEntrada());
                    logger.info("Fecha de entrada actualizada.");
                }
                if (partial.getFechaSalida() != null) {
                    reserva.setFechaSalida(partial.getFechaSalida());
                    logger.info("Fecha de salida actualizada.");
                }
                if (partial.getPago() != null) {
                    reserva.setPago(partial.getPago());
                    logger.info("Pago actualizado.");
                }
                return reserva;
            }
        }
        logger.warn("No se encontró reserva con ID {} para actualización parcial.", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteReserva(@PathVariable Long id) {
        logger.info("Intentando eliminar reserva con ID: {}", id);
        boolean removed = reservas.removeIf(reserva -> reserva.getId().equals(id));
        if (removed) {
            logger.info("Reserva con ID {} eliminada con éxito.", id);
            return "Reserva eliminada con éxito.";
        } else {
            logger.error("No se pudo eliminar la reserva con ID {}.", id);
            return "Reserva no encontrada.";
        }
    }
}