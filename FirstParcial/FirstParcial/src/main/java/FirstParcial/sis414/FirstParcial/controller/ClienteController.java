package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final List<Cliente> clientes = new ArrayList<>();

    @GetMapping
    public List<Cliente> getClientes() {
        return clientes;
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId().equals(id)) {
                clientes.set(i, cliente);
                return cliente;
            }
        }
        return null;
    }

    @PatchMapping("/{id}")
    public Cliente patchCliente(@PathVariable Long id, @RequestBody Cliente partial) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                if (partial.getNombres() != null) cliente.setNombres(partial.getNombres());
                if (partial.getApellidos() != null) cliente.setApellidos(partial.getApellidos());
                if (partial.getCi() != null) cliente.setCi(partial.getCi());
                if (partial.getTelefono() != null) cliente.setTelefono(partial.getTelefono());
                return cliente;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id) {
        boolean removed = clientes.removeIf(cliente -> cliente.getId().equals(id));
        return removed ? "Cliente eliminado con éxito." : "Cliente no encontrado.";
    }
}