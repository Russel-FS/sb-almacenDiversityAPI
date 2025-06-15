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

    // Cambia estos valores según tu configuración
    private static final String URL = "jdbc:mysql://localhost:3306/JC_Diversity";
    private static final String USER = "root";
    private static final String PASS = ""; // tu contraseña

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
                salida.setIdSalida(rs.getInt("ID_Salida"));
                salida.setFechaSalida(rs.getTimestamp("Fecha_Salida"));
                salida.setMotivoSalida(rs.getString("Motivo_Salida"));
                salidas.add(salida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salidas;
    }

    @GetMapping("/buscar/{id}")
    public Salida buscarSalida(@PathVariable Integer id) {
        Salida salida = null;
        String sql = "SELECT * FROM Salidas WHERE ID_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    salida = new Salida();
                    salida.setIdSalida(rs.getInt("ID_Salida"));
                    salida.setFechaSalida(rs.getTimestamp("Fecha_Salida"));
                    salida.setMotivoSalida(rs.getString("Motivo_Salida"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    @PostMapping("/crear")
    public Salida crearSalida(@RequestBody Salida salida) {
        String sql = "INSERT INTO Salidas (Fecha_Salida, Motivo_Salida) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, new Timestamp(salida.getFechaSalida().getTime()));
            ps.setString(2, salida.getMotivoSalida());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    salida.setIdSalida(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salida;
    }

    @PutMapping("/actualizar/{id}")
    public Salida actualizarSalida(@PathVariable Integer id, @RequestBody Salida salida) {
        String sql = "UPDATE Salidas SET Fecha_Salida = ?, Motivo_Salida = ? WHERE ID_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new Timestamp(salida.getFechaSalida().getTime()));
            ps.setString(2, salida.getMotivoSalida());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarSalida(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarSalida(@PathVariable Integer id) {
        String sql = "DELETE FROM Salidas WHERE ID_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
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
                detalle.setIdDetalleSalida(rs.getInt("ID_Detalle_Salida"));
                detalle.setSalidaId(rs.getInt("ID_Salida"));
                detalle.setID_Producto(rs.getString("ID_Producto"));
                detalle.setCantidad(rs.getInt("Cantidad"));
                detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    @GetMapping("/detalles/buscar/{id}")
    public DetalleSalida buscarDetalle(@PathVariable Integer id) {
        DetalleSalida detalle = null;
        String sql = "SELECT * FROM Detalle_Salida WHERE ID_Detalle_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    detalle = new DetalleSalida();
                    detalle.setIdDetalleSalida(rs.getInt("ID_Detalle_Salida"));
                    detalle.setSalidaId(rs.getInt("ID_Salida"));
                    detalle.setID_Producto(rs.getString("ID_Producto"));
                    detalle.setCantidad(rs.getInt("Cantidad"));
                    detalle.setSubtotal(rs.getBigDecimal("Subtotal"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    @PostMapping("/detalles/crear")
    public DetalleSalida crearDetalle(@RequestBody DetalleSalida detalle) {
        String sql = "INSERT INTO Detalle_Salida (ID_Salida, ID_Producto, Cantidad, Subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalle.getSalidaId());
            ps.setString(2, detalle.getID_Producto());
            ps.setInt(3, detalle.getCantidad());
            ps.setBigDecimal(4, detalle.getSubtotal());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    detalle.setIdDetalleSalida(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    @PutMapping("/detalles/actualizar/{id}")
    public DetalleSalida actualizarDetalle(@PathVariable Integer id, @RequestBody DetalleSalida detalle) {
        String sql = "UPDATE Detalle_Salida SET ID_Salida = ?, ID_Producto = ?, Cantidad = ?, Subtotal = ? WHERE ID_Detalle_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, detalle.getSalidaId());
            ps.setString(2, detalle.getID_Producto());
            ps.setInt(3, detalle.getCantidad());
            ps.setBigDecimal(4, detalle.getSubtotal());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarDetalle(id);
    }

    @DeleteMapping("/detalles/eliminar/{id}")
    public String eliminarDetalle(@PathVariable Integer id) {
        String sql = "DELETE FROM Detalle_Salida WHERE ID_Detalle_Salida = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
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