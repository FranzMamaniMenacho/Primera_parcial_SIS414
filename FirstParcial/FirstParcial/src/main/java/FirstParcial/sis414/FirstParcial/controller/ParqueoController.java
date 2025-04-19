package FirstParcial.sis414.FirstParcial.controller;

import FirstParcial.sis414.FirstParcial.entity.Parqueo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parqueos")
public class ParqueoController {

    private static final Logger logger = LoggerFactory.getLogger(ParqueoController.class);
    private List<Parqueo> parqueos = new ArrayList<>();

    @GetMapping
    public List<Parqueo> getParqueos() {
        logger.info("Solicitando lista de parqueos.");
        return parqueos;
    }

    @GetMapping("/{id}")
    public Parqueo getParqueo(@PathVariable Long id) {
        logger.info("Solicitando parqueo con ID: {}", id);
        for (Parqueo parqueo : parqueos) {
            if (parqueo.getId().equals(id)) {
                logger.info("Parqueo encontrado con ID: {}", id);
                return parqueo;
            }
        }
        logger.warn("Parqueo con ID {} no encontrado.", id);
        return null;
    }

    @PostMapping
    public Parqueo addParqueo(@RequestBody Parqueo parqueo) {
        parqueos.add(parqueo);
        logger.info("Nuevo parqueo creado con ID: {}", parqueo.getId());
        return parqueo;
    }

    @PutMapping("/{id}")
    public Parqueo updateParqueo(@PathVariable Long id, @RequestBody Parqueo parqueo) {
        logger.info("Actualizando parqueo con ID: {}", id);
        for (int i = 0; i < parqueos.size(); i++) {
            if (parqueos.get(i).getId().equals(id)) {
                parqueos.set(i, parqueo);
                logger.info("Parqueo con ID {} actualizado.", id);
                return parqueo;
            }
        }
        logger.warn("No se encontró parqueo con ID {} para actualizar.", id);
        return null;
    }

    @PatchMapping("/{id}")
    public Parqueo patchParqueo(@PathVariable Long id, @RequestBody Parqueo partialParqueo) {
        logger.info("Actualizando parcialmente parqueo con ID: {}", id);
        for (Parqueo parqueo : parqueos) {
            if (parqueo.getId().equals(id)) {
                if (partialParqueo.getMarca() != null) {
                    parqueo.setMarca(partialParqueo.getMarca());
                    logger.info("Marca actualizada para parqueo con ID: {}", id);
                }
                if (partialParqueo.getColor() != null) {
                    parqueo.setColor(partialParqueo.getColor());
                    logger.info("Color actualizado para parqueo con ID: {}", id);
                }
                if (partialParqueo.getPrecioPorNoche() != 0) {
                    parqueo.setPrecioPorNoche(partialParqueo.getPrecioPorNoche());
                    logger.info("Precio por noche actualizado para parqueo con ID: {}", id);
                }
                if (partialParqueo.getPlaca() != null) {
                    parqueo.setPlaca(partialParqueo.getPlaca());
                    logger.info("Placa actualizada para parqueo con ID: {}", id);
                }
                parqueo.setDisponible(partialParqueo.isDisponible());
                logger.info("Disponibilidad actualizada para parqueo con ID: {}", id);
                return parqueo;
            }
        }
        logger.warn("No se encontró parqueo con ID {} para actualización parcial.", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteParqueo(@PathVariable Long id) {
        logger.info("Intentando eliminar parqueo con ID: {}", id);
        boolean removed = parqueos.removeIf(parqueo -> parqueo.getId().equals(id));
        if (removed) {
            logger.info("Parqueo con ID {} eliminado con éxito.", id);
            return "Parqueo eliminado con éxito.";
        } else {
            logger.error("No se pudo eliminar el parqueo con ID {}. No encontrado.", id);
            return "Parqueo no encontrado.";
        }
    }
}
