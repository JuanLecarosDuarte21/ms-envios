
package com.tiendapc.envios.controller;

import com.tiendapc.envios.model.Envio;
import com.tiendapc.envios.service.EnvioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    // Crear un envío
    @PostMapping
    public ResponseEntity<Envio> crearEnvio(
            @Valid @RequestBody Envio envio) {

        Envio nuevoEnvio = envioService.crearEnvio(envio);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoEnvio);
    }

    // Listar todos los envíos
    @GetMapping
    public List<Envio> listarEnvios() {
        return envioService.listarEnvios();
    }

    // Buscar envío por ID
    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarPorId(
            @PathVariable Long id) {

        return envioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Buscar envíos por pedido
    @GetMapping("/pedido/{pedidoId}")
    public List<Envio> buscarPorPedido(
            @PathVariable Long pedidoId) {

        return envioService.buscarPorPedido(pedidoId);
    }

    // Buscar envíos por estado
    @GetMapping("/estado/{estado}")
    public List<Envio> buscarPorEstado(
            @PathVariable String estado) {

        return envioService.buscarPorEstado(estado);
    }

    // Buscar envíos por transportista
    @GetMapping("/transportista/{transportista}")
    public List<Envio> buscarPorTransportista(
            @PathVariable String transportista) {

        return envioService.buscarPorTransportista(
                transportista);
    }

    // Buscar envíos por pedido y estado
    @GetMapping("/pedido/{pedidoId}/estado/{estado}")
    public List<Envio> buscarPorPedidoYEstado(
            @PathVariable Long pedidoId,
            @PathVariable String estado) {

        return envioService.buscarPorPedidoYEstado(
                pedidoId, estado);
    }

    // Actualizar estado del envío
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestBody ActualizarEstadoRequest request) {

        try {
            return envioService
                    .actualizarEstado(id, request.estado())
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() ->
                            ResponseEntity.notFound().build());

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Actualizar número de seguimiento
    @PutMapping("/{id}/seguimiento")
    public ResponseEntity<?> actualizarSeguimiento(
            @PathVariable Long id,
            @RequestBody ActualizarSeguimientoRequest request) {

        try {
            return envioService
                    .actualizarSeguimiento(
                            id, request.numeroSeguimiento())
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() ->
                            ResponseEntity.notFound().build());

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // Eliminar un envío
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEnvio(
            @PathVariable Long id) {

        if (envioService.eliminarEnvio(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    public record ActualizarEstadoRequest(String estado) {
    }

    public record ActualizarSeguimientoRequest(
            String numeroSeguimiento) {
    }
}
