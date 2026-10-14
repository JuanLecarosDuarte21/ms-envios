
package com.tiendapc.envios.service;

import com.tiendapc.envios.model.Envio;
import com.tiendapc.envios.repository.EnvioRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    // Crear un envío
    public Envio crearEnvio(Envio envio) {
        envio.setId(null);
        envio.setEstado("PENDIENTE");
        envio.setFechaEnvio(null);

        return envioRepository.save(envio);
    }

    // Listar todos los envíos
    public List<Envio> listarEnvios() {
        return envioRepository.findAll();
    }

    // Buscar envío por ID
    public Optional<Envio> buscarPorId(Long id) {
        return envioRepository.findById(id);
    }

    // Buscar envíos por pedido
    public List<Envio> buscarPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId);
    }

    // Buscar envíos por estado
    public List<Envio> buscarPorEstado(String estado) {
        return envioRepository.findByEstado(
            estado.trim().toUpperCase()
        );
    }

    // Buscar envíos por transportista
    public List<Envio> buscarPorTransportista(String transportista) {
        return envioRepository.findByTransportista(
            transportista.trim().toUpperCase()
        );
    }

    // Buscar envíos por pedido y estado
    public List<Envio> buscarPorPedidoYEstado(
            Long pedidoId, String estado) {

        return envioRepository.findByPedidoIdAndEstado(
            pedidoId,
            estado.trim().toUpperCase()
        );
    }

    // Actualizar el estado de un envío
    public Optional<Envio> actualizarEstado(
            Long id, String estado) {

        if (estado == null || estado.isBlank()) {
            throw new IllegalArgumentException(
                "El estado no puede estar vacío"
            );
        }

        return envioRepository.findById(id).map(envio -> {
            envio.setEstado(estado.trim().toUpperCase());
            return envioRepository.save(envio);
        });
    }

    // Actualizar el número de seguimiento
    public Optional<Envio> actualizarSeguimiento(
            Long id, String numeroSeguimiento) {

        if (numeroSeguimiento == null ||
                numeroSeguimiento.isBlank()) {
            throw new IllegalArgumentException(
                "El número de seguimiento no puede estar vacío"
            );
        }

        return envioRepository.findById(id).map(envio -> {
            envio.setNumeroSeguimiento(
                numeroSeguimiento.trim()
            );
            return envioRepository.save(envio);
        });
    }

    // Eliminar un envío
    public boolean eliminarEnvio(Long id) {

        if (!envioRepository.existsById(id)) {
            return false;
        }

        envioRepository.deleteById(id);
        return true;
    }
}
