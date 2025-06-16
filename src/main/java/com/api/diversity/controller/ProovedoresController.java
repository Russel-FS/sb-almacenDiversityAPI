package com.api.diversity.controller;

import com.api.diversity.model.Proveedores;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProovedoresController {

    private static final String URL = "jdbc:mysql://localhost:3306/diversity_inventory";
    private static final String USER = "root";
    private static final String PASS = "";

    @GetMapping("/listar")
    public List<Proveedores> listarProveedores() {
        List<Proveedores> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setIdProveedor(rs.getLong("ID_Proveedor"));
                proveedor.setRazonSocial(rs.getString("Razon_Social"));
                proveedor.setRuc(rs.getString("RUC"));
                proveedor.setDireccion(rs.getString("Direccion"));
                proveedor.setTelefono(rs.getString("Telefono"));
                proveedor.setEmail(rs.getString("Email"));
                proveedor.setEstado(rs.getString("Estado"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    @GetMapping("/buscar/{id}")
    public Proveedores buscarProveedor(@PathVariable Long id) {
        Proveedores proveedor = null;
        String sql = "SELECT * FROM Proveedores WHERE ID_Proveedor = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proveedor = new Proveedores();
                    proveedor.setIdProveedor(rs.getLong("ID_Proveedor"));
                    proveedor.setRazonSocial(rs.getString("Razon_Social"));
                    proveedor.setRuc(rs.getString("RUC"));
                    proveedor.setDireccion(rs.getString("Direccion"));
                    proveedor.setTelefono(rs.getString("Telefono"));
                    proveedor.setEmail(rs.getString("Email"));
                    proveedor.setEstado(rs.getString("Estado"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    @PostMapping("/crear")
    public Proveedores crearProveedor(@RequestBody Proveedores proveedor) {
        String sql = "INSERT INTO Proveedores (Razon_Social, RUC, Direccion, Telefono, Email, Estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, proveedor.getRazonSocial());
            ps.setString(2, proveedor.getRuc());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getEmail());
            ps.setString(6, proveedor.getEstado() != null ? proveedor.getEstado() : "Activo");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    proveedor.setIdProveedor(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }

    @PutMapping("/actualizar/{id}")
    public Proveedores actualizarProveedor(@PathVariable Long id, @RequestBody Proveedores proveedor) {
        String sql = "UPDATE Proveedores SET Razon_Social=?, RUC=?, Direccion=?, Telefono=?, Email=?, Estado=? WHERE ID_Proveedor=?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, proveedor.getRazonSocial());
            ps.setString(2, proveedor.getRuc());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getEmail());
            ps.setString(6, proveedor.getEstado());
            ps.setLong(7, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buscarProveedor(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        String sql = "DELETE FROM Proveedores WHERE ID_Proveedor = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return "Proveedor eliminado correctamente.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No se encontró el proveedor con el ID proporcionado.";
    }
}
