package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final List<Cliente> clientes = new ArrayList<>();

    @GetMapping
    public List<Cliente> getClientes() {
        logger.info("Solicitando lista de clientes.");
        return clientes;
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Long id) {
        logger.info("Solicitando cliente con ID: {}", id);
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElseGet(() -> {
                    logger.warn("Cliente con ID {} no encontrado.", id);
                    return null;
                });
    }

    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente) {
        clientes.add(cliente);
        logger.info("Cliente agregado con ID: {}", cliente.getId());
        return cliente;
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        logger.info("Actualizando cliente con ID: {}", id);
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, cliente);
                logger.info("Cliente con ID {} actualizado.", id);
                return cliente;
            }
        }
        logger.warn("No se encontró cliente con ID {} para actualizar.", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Cliente patchCliente(@PathVariable Long id, @RequestBody Cliente partial) {
        logger.info("Actualización parcial para cliente con ID: {}", id);
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                if (partial.getNombres() != null) {
                    cliente.setNombres(partial.getNombres());
                    logger.info("Nombres actualizados.");
                }
                if (partial.getApellidos() != null) {
                    cliente.setApellidos(partial.getApellidos());
                    logger.info("Apellidos actualizados.");
                }
                if (partial.getCi() != null) {
                    cliente.setCi(partial.getCi());
                    logger.info("CI actualizado.");
                }
                if (partial.getTelefono() != null) {
                    cliente.setTelefono(partial.getTelefono());
                    logger.info("Teléfono actualizado.");
                }
                return cliente;
            }
        }
        logger.warn("No se encontró cliente con ID {} para actualización parcial.", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id) {
        logger.info("Intentando eliminar cliente con ID: {}", id);
        boolean removed = clientes.removeIf(cliente -> cliente.getId().equals(id));
        if (removed) {
            logger.info("Cliente con ID {} eliminado con éxito.", id);
            return "Cliente eliminado con éxito.";
        } else {
            logger.error("No se pudo eliminar el cliente con ID {}.", id);
            return "Cliente no encontrado.";
        }
    }
}