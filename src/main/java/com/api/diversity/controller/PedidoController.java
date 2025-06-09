package com.api.diversity.controller;

import com.api.diversity.model.DetallePedido;
import com.api.diversity.model.Pedido;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    // Simulando una base de datos en memoria
    private Map<Integer, Pedido> pedidos = new HashMap<>();
    private Map<Integer, DetallePedido> detallesPedido = new HashMap<>();
    private int nextPedidoId = 1;
    private int nextDetallePedidoId = 1;

    // 1. Registrar un nuevo Pedido
    @PostMapping("/registrar")
    public String crearPedido(@RequestBody Pedido pedido) {
        pedido.setIdPedido(nextPedidoId++);
        pedidos.put(pedido.getIdPedido(), pedido);
        return "Pedido registrado: " + pedido;
    }

    // 2. Obtener la lista de todos los Pedidos
    @GetMapping("/listar")
    public Map<Integer, Pedido> listarPedidos() {
        return pedidos;
    }

    // 3. Buscar un Pedido por ID
    @GetMapping("/buscar/{id}")
    public String buscarPedido(@PathVariable Integer id) {
        Pedido pedido = pedidos.get(id);
        return pedido != null ? "Pedido encontrado: " + pedido : "Pedido no encontrado.";
    }

    // 4. Actualizar un Pedido por ID
    @PutMapping("/actualizar/{id}")
    public String actualizarPedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        Pedido existingPedido = pedidos.get(id);
        if (existingPedido != null) {
            pedido.setIdPedido(id);
            pedidos.put(id, pedido);
            return "Pedido con ID " + id + " actualizado: " + pedido;
        }
        return "Pedido no encontrado para actualizar.";
    }

    // 5. Eliminar un Pedido por ID
    @DeleteMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Integer id) {
        if (pedidos.containsKey(id)) {
            pedidos.remove(id);
            return "Pedido con ID " + id + " eliminado.";
        }
        return "Pedido no encontrado para eliminar.";
    }

    // 6. Registrar un Detalle de Pedido
    @PostMapping("/detalle/registrar")
    public String registrarDetallePedido(@RequestBody DetallePedido detallePedido) {
        detallePedido.setIdDetalle(nextDetallePedidoId++);
        detallesPedido.put(detallePedido.getIdDetalle(), detallePedido);
        return "Detalle de pedido registrado: " + detallePedido;
    }

    // 7. Obtener la lista de todos los Detalles de Pedido
    @GetMapping("/detalle/listar")
    public Map<Integer, DetallePedido> listarDetallesPedido() {
        return detallesPedido;
    }

    // 8. Buscar un Detalle de Pedido por ID
    @GetMapping("/detalle/buscar/{id}")
    public String buscarDetallePedido(@PathVariable Integer id) {
        DetallePedido detalle = detallesPedido.get(id);
        return detalle != null ? "Detalle de pedido encontrado: " + detalle : "Detalle de pedido no encontrado.";
    }

    // 9. Actualizar un Detalle de Pedido por ID
    @PutMapping("/detalle/actualizar/{id}")
    public String actualizarDetallePedido(@PathVariable Integer id, @RequestBody DetallePedido detallePedido) {
        DetallePedido existingDetalle = detallesPedido.get(id);
        if (existingDetalle != null) {
            detallePedido.setIdDetalle(id);
            detallesPedido.put(id, detallePedido);
            return "Detalle de pedido con ID " + id + " actualizado: " + detallePedido;
        }
        return "Detalle de pedido no encontrado para actualizar.";
    }

    // 10. Eliminar un Detalle de Pedido por ID
    @DeleteMapping("/detalle/eliminar/{id}")
    public String eliminarDetallePedido(@PathVariable Integer id) {
        if (detallesPedido.containsKey(id)) {
            detallesPedido.remove(id);
            return "Detalle de pedido con ID " + id + " eliminado.";
        }
        return "Detalle de pedido no encontrado para eliminar.";
    }
}
