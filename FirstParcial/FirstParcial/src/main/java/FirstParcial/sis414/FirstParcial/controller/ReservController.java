package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Reserv;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservController {
    private final List<Reserv> reservas = new ArrayList<>();

    @GetMapping
    public List<Reserv> getReservas() {
        return reservas;
    }

    @GetMapping("/{id}")
    public Reserv getReserva(@PathVariable Long id) {
        return reservas.stream()
                .filter(reserva -> reserva.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Reserv addReserva(@RequestBody Reserv reserva) {
        reservas.add(reserva);
        return reserva;
    }

    @PutMapping("/{id}")
    public Reserv updateReserva(@PathVariable Long id, @RequestBody Reserv reserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(id)) {
                reservas.set(i, reserva);
                return reserva;
            }
        }
        return null;
    }

    @PatchMapping("/{id}")
    public Reserv patchReserva(@PathVariable Long id, @RequestBody Reserv partial) {
        for (Reserv reserva : reservas) {
            if (reserva.getId().equals(id)) {
                if (partial.getCliente() != null) reserva.setCliente(partial.getCliente());
                if (partial.getHabitacion() != null) reserva.setHabitacion(partial.getHabitacion());
                if (partial.getFechaEntrada() != null) reserva.setFechaEntrada(partial.getFechaEntrada());
                if (partial.getFechaSalida() != null) reserva.setFechaSalida(partial.getFechaSalida());
                if (partial.getPago() != null) reserva.setPago(partial.getPago());
                return reserva;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteReserva(@PathVariable Long id) {
        boolean removed = reservas.removeIf(reserva -> reserva.getId().equals(id));
        return removed ? "Reserva eliminada con éxito." : "Reserva no encontrada.";
    }
}