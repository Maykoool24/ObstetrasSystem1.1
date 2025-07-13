package controlador;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import modelo.Paciente;

public class ControladorPaciente {

    // Registrar paciente
    public boolean registrar(Paciente paciente) {
        if (paciente.getFechaNac().isAfter(LocalDate.now())) {
            System.out.println("La fecha de nacimiento no puede ser futura.");
            return false;
        }

        String sql = "INSERT INTO pacientes (nombre_completo, dni, fecha_nacimiento, telefono, direccion, estado) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombreCompleto());
            ps.setString(2, paciente.getDni());
            ps.setDate(3, Date.valueOf(paciente.getFechaNac())); // convierte LocalDate a java.sql.Date
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getDireccion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar paciente: " + e.toString());
            return false;
        }
    }

    // Listar pacientes activos
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE estado = 1";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("dni"),
                    rs.getDate("fecha_nacimiento").toLocalDate(), // convertir a LocalDate
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getInt("estado")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pacientes: " + e.toString());
        }

        return lista;
    }

    // Modificar paciente
    public boolean modificar(Paciente paciente) {
        if (paciente.getFechaNac().isAfter(LocalDate.now())) {
            System.out.println("La fecha de nacimiento no puede ser futura.");
            return false;
        }

        String sql = "UPDATE pacientes SET nombre_completo = ?, dni = ?, fecha_nacimiento = ?, telefono = ?, direccion = ? WHERE id = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombreCompleto());
            ps.setString(2, paciente.getDni());
            ps.setDate(3, Date.valueOf(paciente.getFechaNac()));
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getDireccion());
            ps.setInt(6, paciente.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar paciente: " + e.toString());
            return false;
        }
    }

    // Eliminación lógica
    public boolean eliminar(int id) {
        String sql = "UPDATE pacientes SET estado = 0 WHERE id = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar paciente: " + e.toString());
            return false;
        }
    }

    // Calcular edad actual desde la fecha de nacimiento
    public int calcularEdad(Paciente paciente) {
        if (paciente.getFechaNac() == null) return -1;
        return Period.between(paciente.getFechaNac(), LocalDate.now()).getYears();
    }
}
