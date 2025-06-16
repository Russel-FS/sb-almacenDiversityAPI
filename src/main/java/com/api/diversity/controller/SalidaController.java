package com.api.diversity.controller;

import com.api.diversity.model.Salida;
import com.api.diversity.model.DetalleSalida;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/salidas")
public class SalidaController {

    
    private static final String URL = "jdbc:mysql://localhost:3306/diversity_inventory";
    private static final String USER = "root";
    private static final String PASS = ""; 

    // --- Métodos para Salida ---

    @GetMapping("/listar")
    public List<Salida> listarSalidas() {
        List<Salida> salidas = new ArrayList<>();
        String sql = "SELECT * FROM Salidas";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Salida salida = new Salida();
                salida.setIdSalida(rs.getLong("ID_Salida"));
                salida.setNumeroDocumento(rs.getString("Numero_Documento"));
                salida.setTipoDocumento(rs.getString("Tipo_Documento"));
                salida.setFechaSalida(rs.getTimestamp("Fecha_Salida"));
                salida.setMotivoSalida(rs.getString("Motivo_Salida"));
                salida.setTotalVenta(rs.getBigDecimal("Total_Venta"));
                salida.setEstado(rs.getString("Estado"));
                salida.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                salida.setIdUsuarioAprobacion(rs.getObject("ID_Usuario_Aprobacion") != null ? rs.getLong("ID_Usuario_Aprobacion") : null);
                salida.setFechaAprobacion(rs.getTimestamp("Fecha_Aprobacion"));
                salida.setObservaciones(rs.getString("Observaciones"));
                salidas.add(salida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salidas;
    }

    @GetMapping("/buscar/{id}")
    public Salida buscarSalida(@PathVariable Long id) {
        Salida salida = null;
        String sql = "SELECT * FROM Salidas WHERE ID_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    salida = new Salida();
                    salida.setIdSalida(rs.getLong("ID_Salida"));
                    salida.setNumeroDocumento(rs.getString("Numero_Documento"));
                    salida.setTipoDocumento(rs.getString("Tipo_Documento"));
                    salida.setFechaSalida(rs.getTimestamp("Fecha_Salida"));
                    salida.setMotivoSalida(rs.getString("Motivo_Salida"));
                    salida.setTotalVenta(rs.getBigDecimal("Total_Venta"));
                    salida.setEstado(rs.getString("Estado"));
                    salida.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                    salida.setIdUsuarioAprobacion(rs.getObject("ID_Usuario_Aprobacion") != null ? rs.getLong("ID_Usuario_Aprobacion") : null);
                    salida.setFechaAprobacion(rs.getTimestamp("Fecha_Aprobacion"));
                    salida.setObservaciones(rs.getString("Observaciones"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    @PostMapping("/crear")
    public Salida crearSalida(@RequestBody Salida salida) {
        String sql = "INSERT INTO Salidas (Numero_Documento, Tipo_Documento, Fecha_Salida, Motivo_Salida, Total_Venta, Estado, ID_Usuario_Registro, ID_Usuario_Aprobacion, Fecha_Aprobacion, Observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, salida.getNumeroDocumento());
            ps.setString(2, salida.getTipoDocumento());
            ps.setTimestamp(3, salida.getFechaSalida());
            ps.setString(4, salida.getMotivoSalida());
            ps.setBigDecimal(5, salida.getTotalVenta());
            ps.setString(6, salida.getEstado());
            ps.setLong(7, salida.getIdUsuarioRegistro());
            if (salida.getIdUsuarioAprobacion() != null) {
                ps.setLong(8, salida.getIdUsuarioAprobacion());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            ps.setTimestamp(9, salida.getFechaAprobacion());
            ps.setString(10, salida.getObservaciones());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    salida.setIdSalida(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    @PutMapping("/actualizar/{id}")
    public Salida actualizarSalida(@PathVariable Long id, @RequestBody Salida salida) {
        String sql = "UPDATE Salidas SET Numero_Documento=?, Tipo_Documento=?, Fecha_Salida=?, Motivo_Salida=?, Total_Venta=?, Estado=?, ID_Usuario_Registro=?, ID_Usuario_Aprobacion=?, Fecha_Aprobacion=?, Observaciones=? WHERE ID_Salida=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, salida.getNumeroDocumento());
            ps.setString(2, salida.getTipoDocumento());
            ps.setTimestamp(3, salida.getFechaSalida());
            ps.setString(4, salida.getMotivoSalida());
            ps.setBigDecimal(5, salida.getTotalVenta());
            ps.setString(6, salida.getEstado());
            ps.setLong(7, salida.getIdUsuarioRegistro());
            if (salida.getIdUsuarioAprobacion() != null) {
                ps.setLong(8, salida.getIdUsuarioAprobacion());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            ps.setTimestamp(9, salida.getFechaAprobacion());
            ps.setString(10, salida.getObservaciones());
            ps.setLong(11, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarSalida(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarSalida(@PathVariable Long id) {
        String sql = "DELETE FROM Salidas WHERE ID_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Salida eliminada correctamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se encontró la salida con el ID proporcionado.";
    }

    // --- Métodos para DetalleSalida ---

    @GetMapping("/detalles/listar")
    public List<DetalleSalida> listarDetalles() {
        List<DetalleSalida> detalles = new ArrayList<>();
        String sql = "SELECT * FROM Detalle_Salida";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DetalleSalida detalle = new DetalleSalida();
                detalle.setIdDetalleSalida(rs.getLong("ID_Detalle_Salida"));
                detalle.setIdSalida(rs.getLong("ID_Salida"));
                detalle.setIdProducto(rs.getLong("ID_Producto"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setPrecioUnitario(rs.getBigDecimal("Precio_Unitario"));
                detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                detalle.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                detalle.setFechaRegistro(rs.getTimestamp("Fecha_Registro"));
                detalle.setEstado(rs.getString("Estado"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @GetMapping("/detalles/buscar/{id}")
    public DetalleSalida buscarDetalle(@PathVariable Long id) {
        DetalleSalida detalle = null;
        String sql = "SELECT * FROM Detalle_Salida WHERE ID_Detalle_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detalle = new DetalleSalida();
                    detalle.setIdDetalleSalida(rs.getLong("ID_Detalle_Salida"));
                    detalle.setIdSalida(rs.getLong("ID_Salida"));
                    detalle.setIdProducto(rs.getLong("ID_Producto"));
                    detalle.setCantidad(rs.getInt("Cantidad"));
                    detalle.setPrecioUnitario(rs.getBigDecimal("Precio_Unitario"));
                    detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                    detalle.setIdUsuarioRegistro(rs.getLong("ID_Usuario_Registro"));
                    detalle.setFechaRegistro(rs.getTimestamp("Fecha_Registro"));
                    detalle.setEstado(rs.getString("Estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    @PostMapping("/detalles/crear")
    public DetalleSalida crearDetalle(@RequestBody DetalleSalida detalle) {
        String sql = "INSERT INTO Detalle_Salida (ID_Salida, ID_Producto, Cantidad, Precio_Unitario, Subtotal, ID_Usuario_Registro, Fecha_Registro, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, detalle.getIdSalida());
            ps.setLong(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setBigDecimal(4, detalle.getPrecioUnitario());
            ps.setBigDecimal(5, detalle.getSubtotal());
            ps.setLong(6, detalle.getIdUsuarioRegistro());
            ps.setTimestamp(7, detalle.getFechaRegistro());
            ps.setString(8, detalle.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    detalle.setIdDetalleSalida(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    @PutMapping("/detalles/actualizar/{id}")
    public DetalleSalida actualizarDetalle(@PathVariable Long id, @RequestBody DetalleSalida detalle) {
        String sql = "UPDATE Detalle_Salida SET ID_Salida=?, ID_Producto=?, Cantidad=?, Precio_Unitario=?, Subtotal=?, ID_Usuario_Registro=?, Fecha_Registro=?, Estado=? WHERE ID_Detalle_Salida=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, detalle.getIdSalida());
            ps.setLong(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setBigDecimal(4, detalle.getPrecioUnitario());
            ps.setBigDecimal(5, detalle.getSubtotal());
            ps.setLong(6, detalle.getIdUsuarioRegistro());
            ps.setTimestamp(7, detalle.getFechaRegistro());
            ps.setString(8, detalle.getEstado());
            ps.setLong(9, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarDetalle(id);
    }

    @DeleteMapping("/detalles/eliminar/{id}")
    public String eliminarDetalle(@PathVariable Long id) {
        String sql = "DELETE FROM Detalle_Salida WHERE ID_Detalle_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Detalle de salida eliminado correctamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se encontró el detalle de salida con el ID proporcionado.";
    }
}