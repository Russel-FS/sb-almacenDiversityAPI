package com.api.diversity.controller;

import com.api.diversity.model.Entrada;
import com.api.diversity.model.DetalleEntrada;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private static final String URL = "jdbc:mysql://localhost:3306/diversity_inventory";
    private static final String USER = "root";
    private static final String PASS = ""; 

    @GetMapping("/listar")
    public List<Entrada> listarEntradas() {
        List<Entrada> entradas = new ArrayList<>();
        String sql = "SELECT * FROM Entradas";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Entrada entrada = new Entrada();
                entrada.setIdEntrada(rs.getLong("ID_Entrada"));
                entrada.setNumeroFactura(rs.getString("Numero_Factura"));
                entrada.setIdProveedor(rs.getLong("ID_Proveedor"));
                entrada.setFechaEntrada(rs.getTimestamp("Fecha_Entrada"));
                entrada.setCostoTotal(rs.getBigDecimal("Costo_Total"));
                entrada.setEstado(rs.getString("Estado"));
                entrada.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                entrada.setIdUsuarioAprobacion(rs.getObject("ID_Usuario_Aprobacion") != null ? rs.getLong("ID_Usuario_Aprobacion") : null);
                entrada.setFechaAprobacion(rs.getTimestamp("Fecha_Aprobacion"));
                entrada.setObservaciones(rs.getString("Observaciones"));
                entradas.add(entrada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entradas;
    }

    @GetMapping("/buscar/{id}")
    public Entrada buscarEntrada(@PathVariable Long id) {
        Entrada entrada = null;
        String sql = "SELECT * FROM Entradas WHERE ID_Entrada = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entrada = new Entrada();
                    entrada.setIdEntrada(rs.getLong("ID_Entrada"));
                    entrada.setNumeroFactura(rs.getString("Numero_Factura"));
                    entrada.setIdProveedor(rs.getLong("ID_Proveedor"));
                    entrada.setFechaEntrada(rs.getTimestamp("Fecha_Entrada"));
                    entrada.setCostoTotal(rs.getBigDecimal("Costo_Total"));
                    entrada.setEstado(rs.getString("Estado"));
                    entrada.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                    entrada.setIdUsuarioAprobacion(rs.getObject("ID_Usuario_Aprobacion") != null ? rs.getLong("ID_Usuario_Aprobacion") : null);
                    entrada.setFechaAprobacion(rs.getTimestamp("Fecha_Aprobacion"));
                    entrada.setObservaciones(rs.getString("Observaciones"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrada;
    }

    @PostMapping("/crear")
    public Entrada crearEntrada(@RequestBody Entrada entrada) {
        String sql = "INSERT INTO Entradas (Numero_Factura, ID_Proveedor, Fecha_Entrada, Costo_Total, Estado, ID_Usuario_Registro, ID_Usuario_Aprobacion, Fecha_Aprobacion, Observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entrada.getNumeroFactura());
            ps.setLong(2, entrada.getIdProveedor());
            ps.setTimestamp(3, entrada.getFechaEntrada());
            ps.setBigDecimal(4, entrada.getCostoTotal());
            ps.setString(5, entrada.getEstado());
            ps.setLong(6, entrada.getIdUsuarioRegistro());
            if (entrada.getIdUsuarioAprobacion() != null) {
                ps.setLong(7, entrada.getIdUsuarioAprobacion());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            ps.setTimestamp(8, entrada.getFechaAprobacion());
            ps.setString(9, entrada.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entrada.setIdEntrada(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrada;
    }

    @PutMapping("/actualizar/{id}")
    public Entrada actualizarEntrada(@PathVariable Long id, @RequestBody Entrada entrada) {
        String sql = "UPDATE Entradas SET Numero_Factura=?, ID_Proveedor=?, Fecha_Entrada=?, Costo_Total=?, Estado=?, ID_Usuario_Registro=?, ID_Usuario_Aprobacion=?, Fecha_Aprobacion=?, Observaciones=? WHERE ID_Entrada=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entrada.getNumeroFactura());
            ps.setLong(2, entrada.getIdProveedor());
            ps.setTimestamp(3, entrada.getFechaEntrada());
            ps.setBigDecimal(4, entrada.getCostoTotal());
            ps.setString(5, entrada.getEstado());
            ps.setLong(6, entrada.getIdUsuarioRegistro());
            if (entrada.getIdUsuarioAprobacion() != null) {
                ps.setLong(7, entrada.getIdUsuarioAprobacion());
            } else {
                ps.setNull(7, Types.BIGINT);
            }
            ps.setTimestamp(8, entrada.getFechaAprobacion());
            ps.setString(9, entrada.getObservaciones());
            ps.setLong(10, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarEntrada(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarEntrada(@PathVariable Long id) {
        String sql = "DELETE FROM Entradas WHERE ID_Entrada = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Entrada eliminada correctamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se encontró la entrada con el ID proporcionado.";
    }


    //detalles
    @GetMapping("/detalles/{idEntrada}")
    public List<DetalleEntrada> listarDetallesEntrada(@PathVariable Long idEntrada) {
        List<DetalleEntrada> detalles = new ArrayList<>();
        String sql = "SELECT * FROM Detalle_Entrada WHERE ID_Entrada = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idEntrada);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleEntrada detalle = new DetalleEntrada();
                    detalle.setIdDetalleEntrada(rs.getLong("ID_Detalle_Entrada"));
                    detalle.setIdEntrada(rs.getLong("ID_Entrada"));
                    detalle.setIdProducto(rs.getLong("ID_Producto"));
                    detalle.setCantidad(rs.getInt("Cantidad"));
                    detalle.setPrecioUnitario(rs.getBigDecimal("Precio_Unitario"));
                    detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                    detalle.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                    detalle.setFechaRegistro(rs.getTimestamp("Fecha_Registro"));
                    detalle.setEstado(rs.getString("Estado"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}