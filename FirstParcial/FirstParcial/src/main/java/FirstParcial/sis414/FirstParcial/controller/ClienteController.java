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

    // Obtener todos los clientes
    @GetMapping
    public List<Cliente> getClientes() {
        logger.info("Solicitando lista de clientes.");
        return clientes;
    }

//    // Obtener un cliente por ID
//    @GetMapping("/{id}")
//    public Cliente getCliente(@PathVariable Long id) {
//        logger.info("Solicitando cliente con ID: {}", id);
//        return clientes.stream()
//                .filter(cliente -> cliente.getId().equals(id))
//                .findFirst()
//                .orElseGet(() -> {
//                    logger.warn("Cliente con ID {} no encontrado.", id);
//                    return null;
//                });
//    }
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


    // Agregar un nuevo cliente
    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente) {
        // Si el cliente tiene un ID proporcionado, usarlo. Si no, generar uno
        if (cliente.getId() == null) {
            cliente.setId(generateId()); // Generar ID único si no se proporciona
        } else {
            // Verificar si el ID ya existe
            if (clientes.stream().anyMatch(c -> c.getId().equals(cliente.getId()))) {
                throw new IllegalArgumentException("El ID ya está en uso.");
            }
        }

        clientes.add(cliente);
        logger.info("Cliente agregado con ID: {}", cliente.getId());
        return cliente;
    }

    // Método para generar un ID único para los clientes si no se proporciona
    private Long generateId() {
        return (long) (clientes.size() + 1); // Genera un ID basado en el tamaño de la lista
    }

    // Actualizar cliente
    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, cliente);
                return cliente;
            }
        }
        return null;  // No se encontró el cliente con ese ID
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id) {
        boolean removed = clientes.removeIf(cliente -> cliente.getId().equals(id));
        return removed ? "Cliente eliminado" : "Cliente no encontrado";
    }

    // Actualización parcial de cliente (PATCH)
    @PatchMapping("/{id}")
    public Cliente patchCliente(@PathVariable Long id, @RequestBody Cliente clienteParcial) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                // Actualiza los campos solo si están presentes en el objeto clienteParcial
                if (clienteParcial.getNombres() != null) {
                    cliente.setNombres(clienteParcial.getNombres());
                    logger.info("Nombres actualizados.");
                }
                if (clienteParcial.getApellidos() != null) {
                    cliente.setApellidos(clienteParcial.getApellidos());
                    logger.info("Apellidos actualizados.");
                }
                if (clienteParcial.getCi() != null) {
                    cliente.setCi(clienteParcial.getCi());
                    logger.info("CI actualizado.");
                }
                if (clienteParcial.getTelefono() != null) {
                    cliente.setTelefono(clienteParcial.getTelefono());
                    logger.info("Teléfono actualizado.");
                }
                return cliente;
            }
        }
        return null; // No se encontró el cliente con el ID proporcionado
    }
}
