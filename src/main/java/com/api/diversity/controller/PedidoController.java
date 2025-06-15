package com.api.diversity.controller;

import com.api.diversity.model.Pedidos;
import com.api.diversity.model.DetallePedido;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private static final String URL = "jdbc:mysql://localhost:3306/JC_Diversity";
    private static final String USER = "root";
    private static final String PASS = ""; // tu contraseña

    @GetMapping("/listar")
    public List<Pedidos> listarPedidos() {
        List<Pedidos> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pedidos pedido = new Pedidos();
                pedido.setIdPedido(rs.getInt("ID_Pedido"));
                pedido.setClienteId(rs.getInt("ID_Cliente"));
                pedido.setFechaPedido(rs.getTimestamp("Fecha_Pedido"));
                pedido.setTotalPedido(rs.getBigDecimal("Total_Pedido"));
                pedido.setEstadoPedido(rs.getString("Estado_Pedido"));
                pedido.setUsuarioId(rs.getInt("ID_Usuario"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @GetMapping("/buscar/{id}")
    public Pedidos buscarPedido(@PathVariable Integer id) {
        Pedidos pedido = null;
        String sql = "SELECT * FROM Pedidos WHERE ID_Pedido = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedidos();
                    pedido.setIdPedido(rs.getInt("ID_Pedido"));
                    pedido.setClienteId(rs.getInt("ID_Cliente"));
                    pedido.setFechaPedido(rs.getTimestamp("Fecha_Pedido"));
                    pedido.setTotalPedido(rs.getBigDecimal("Total_Pedido"));
                    pedido.setEstadoPedido(rs.getString("Estado_Pedido"));
                    pedido.setUsuarioId(rs.getInt("ID_Usuario"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @PostMapping("/crear")
    public Pedidos crearPedido(@RequestBody Pedidos pedido) {
        String sql = "INSERT INTO Pedidos (ID_Cliente, Fecha_Pedido, Total_Pedido, Estado_Pedido, ID_Usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, pedido.getClienteId(), Types.INTEGER);
            ps.setTimestamp(2, pedido.getFechaPedido());
            ps.setBigDecimal(3, pedido.getTotalPedido());
            ps.setString(4, pedido.getEstadoPedido());
            ps.setObject(5, pedido.getUsuarioId(), Types.INTEGER);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @PutMapping("/actualizar/{id}")
    public Pedidos actualizarPedido(@PathVariable Integer id, @RequestBody Pedidos pedido) {
        String sql = "UPDATE Pedidos SET ID_Cliente = ?, Fecha_Pedido = ?, Total_Pedido = ?, Estado_Pedido = ?, ID_Usuario = ? WHERE ID_Pedido = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, pedido.getClienteId(), Types.INTEGER);
            ps.setTimestamp(2, pedido.getFechaPedido());
            ps.setBigDecimal(3, pedido.getTotalPedido());
            ps.setString(4, pedido.getEstadoPedido());
            ps.setObject(5, pedido.getUsuarioId(), Types.INTEGER);
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarPedido(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Integer id) {
        String sql = "DELETE FROM Pedidos WHERE ID_Pedido = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Pedido eliminado correctamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se encontró el pedido con el ID proporcionado.";
    }



    //para los detalles
    @GetMapping("/detalles/{id}")
    public List<DetallePedido> listarDetallesPedido(@PathVariable Integer id) {
        List<DetallePedido> detalles = new ArrayList<>();
        String sql = "SELECT * FROM Detalle_Pedido WHERE ID_Pedido = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setIdDetalle(rs.getInt("ID_Detalle"));
                    detalle.setPedidoId(rs.getInt("ID_Pedido"));
                    detalle.setProductoId(rs.getString("ID_Producto"));
                    detalle.setCantidad(rs.getInt("Cantidad"));
                    detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}