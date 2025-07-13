package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import conexion.Conexion;
import modelo.Cita;
import modelo.Paciente;
import modelo.Usuario;


public class ControladorCita {

    // --- Métodos CRUD para Citas ---
    // 1. Registrar nueva cita
    public boolean registrarCita(Cita cita) {
        // Validar si ya tiene cita del mismo programa en el mismo año
        if (yaTieneCitaEnMismoProgramaEsteAnio(cita.getIdPaciente(), cita.getIdPrograma(), cita.getFechaCita())) {
            System.err.println("El paciente ya tiene una cita para este programa en el mismo año.");
            return false;
        }

        // Asegúrate de que el SQL use id_programa y estado_cita como INT
        String sql = "INSERT INTO citas (id_obstetra, id_paciente, fecha_cita, id_programa, estado_cita, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Para obtener el ID generado si lo necesitas
            ps.setInt(1, cita.getIdObstetra());
            ps.setInt(2, cita.getIdPaciente());
            ps.setTimestamp(3, cita.getFechaCita());
            ps.setInt(4, cita.getIdPrograma()); // Usar ID del programa
            ps.setInt(5, cita.getEstadoCita()); // Usar estado INT
            ps.setString(6, cita.getObservaciones());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                // Si necesitas el ID de la cita recién creada
                // ResultSet rs = ps.getGeneratedKeys();
                // if (rs.next()) {
                //     cita.setIdCita(rs.getInt(1));
                // }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            return false;
        }
    }

    // 2. Modificar estado de cita
    public boolean modificarEstadoCita(int idCita, int nuevoEstado) { // nuevoEstado ahora es int
        String sql = "UPDATE citas SET estado_cita = ? WHERE id_cita = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado); // Usar estado INT
            ps.setInt(2, idCita);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar estado de cita: " + e.getMessage());
            return false;
        }
    }

    // 3. Listar todas las citas (para administradores, si aplica)
    public List<Cita> listarCitas() {
        List<Cita> listaCitas = new ArrayList<>();
        // Seleccionar id_programa en lugar de programa_preventivo
        String sql = "SELECT id_cita, id_obstetra, id_paciente, fecha_cita, id_programa, estado_cita, observaciones, fecha_registro FROM citas";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                        rs.getInt("id_cita"),
                        rs.getInt("id_obstetra"),
                        rs.getInt("id_paciente"),
                        rs.getTimestamp("fecha_cita"),
                        rs.getInt("id_programa"), // Leer ID del programa
                        rs.getInt("estado_cita"), // Leer estado INT
                        rs.getString("observaciones"),
                        rs.getTimestamp("fecha_registro")
                );
                listaCitas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }
        return listaCitas;
    }

    // 4. Listar citas pendientes (ahora con JOIN para nombres de programas, pacientes y obstetras)
    public List<Map<String, Object>> listarCitasConDetalles(int estadoFiltro) {
        List<Map<String, Object>> listaCitasConDetalles = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha_cita, c.estado_cita, c.observaciones, c.fecha_registro, "
                + "p.dni AS dni_paciente, p.nombre_completo AS nombre_paciente, "
                + "u.nombre_completo AS nombre_obstetra, "
                + "pp.nombre_programa AS nombre_programa_preventivo " // Asegúrate que esta columna exista en tu tabla programas_preventivos
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id "
                + "JOIN usuarios u ON c.id_obstetra = u.id "
                + "JOIN programas_preventivos pp ON c.id_programa = pp.id_programa ";

        // Si estadoFiltro no es -1 (indicando "todos los estados"), añadir la cláusula WHERE
        if (estadoFiltro != -1) {
            sql += "WHERE c.estado_cita = ? ";
        }
        sql += "ORDER BY c.fecha_cita ASC"; // Siempre ordenar

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Si hay un filtro de estado, establecer el parámetro
            if (estadoFiltro != -1) {
                ps.setInt(1, estadoFiltro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> citaDetalle = new HashMap<>();
                    citaDetalle.put("id_cita", rs.getInt("id_cita"));
                    citaDetalle.put("fecha_cita", rs.getTimestamp("fecha_cita"));
                    citaDetalle.put("dni_paciente", rs.getString("dni_paciente"));
                    citaDetalle.put("nombre_paciente", rs.getString("nombre_paciente"));
                    citaDetalle.put("nombre_programa_preventivo", rs.getString("nombre_programa_preventivo"));
                    citaDetalle.put("estado_cita", rs.getInt("estado_cita"));
                    citaDetalle.put("observaciones", rs.getString("observaciones"));
                    citaDetalle.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                    citaDetalle.put("nombre_obstetra", rs.getString("nombre_obstetra"));

                    listaCitasConDetalles.add(citaDetalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas con detalles: " + e.getMessage());
        }
        return listaCitasConDetalles;
    }

    // NUEVO MÉTODO: Obtener el estado actual de una cita por su ID
    public int obtenerEstadoCita(int idCita) {
        String sql = "SELECT estado_cita FROM citas WHERE id_cita = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("estado_cita");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estado de cita: " + e.getMessage());
        }
        return -1; // Retorna -1 si no se encuentra o hay un error
    }

    // 5. Listar citas por obstetra (para el manejo de sesiones) con detalles
    public List<Map<String, Object>> listarCitasPorObstetraConDetalles(int idObstetra) {
        List<Map<String, Object>> listaCitasConDetalles = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha_cita, c.estado_cita, c.observaciones, c.fecha_registro, "
                + "p.dni AS dni_paciente, p.nombre_completo AS nombre_paciente, "
                + "pp.nombre_programa AS nombre_programa_preventivo "
                + "FROM citas c "
                + "JOIN pacientes p ON c.id_paciente = p.id_paciente "
                + "JOIN programas_preventivos pp ON c.id_programa = pp.id_programa "
                + "WHERE c.id_obstetra = ? ORDER BY c.fecha_cita DESC";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idObstetra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> citaDetalle = new HashMap<>();
                citaDetalle.put("id_cita", rs.getInt("id_cita"));
                citaDetalle.put("fecha_cita", rs.getTimestamp("fecha_cita"));
                citaDetalle.put("dni_paciente", rs.getString("dni_paciente"));
                citaDetalle.put("nombre_paciente", rs.getString("nombre_paciente"));
                citaDetalle.put("nombre_programa_preventivo", rs.getString("nombre_programa_preventivo"));
                citaDetalle.put("estado_cita", rs.getInt("estado_cita"));
                citaDetalle.put("observaciones", rs.getString("observaciones"));
                citaDetalle.put("fecha_registro", rs.getTimestamp("fecha_registro"));

                listaCitasConDetalles.add(citaDetalle);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar citas por obstetra con detalles: " + e.getMessage());
        }
        return listaCitasConDetalles;
    }

    // --- Métodos Auxiliares para la Vista (para autocompletado y mostrar nombres) ---
    // Obtener ID de paciente por DNI
    public int obtenerIdPacientePorDNI(String dni) {
        int id = -1;
        String sql = "SELECT id FROM pacientes WHERE dni = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de paciente por DNI: " + e.getMessage());
        }
        return id;
    }

    // Obtener Nombre del Paciente por ID
    public String obtenerNombrePaciente(int idPaciente) {
        String nombre = null;
        String sql = "SELECT nombre_completo FROM pacientes WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre_completo");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre de paciente: " + e.getMessage());
        }
        return nombre;
    }

    // Obtener DNI del Paciente por ID
    public String obtenerDNIpaciente(int idPaciente) {
        String dni = null;
        String sql = "SELECT dni FROM pacientes WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dni = rs.getString("dni");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener DNI de paciente: " + e.getMessage());
        }
        return dni;
    }

    // Obtener Nombre del Obstetra por ID (desde la tabla usuarios)
    public String obtenerNombreObstetra(int idObstetra) {
        String nombre = null;
        String sql = "SELECT nombre_completo FROM usuarios WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idObstetra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre_completo");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre de obstetra: " + e.getMessage());
        }
        return nombre;
    }

    // Verificar si el DNI del paciente existe
    public boolean existePacientePorDNI(String dni) {
        return obtenerIdPacientePorDNI(dni) != -1;
    }

    // --- Métodos para Programas Preventivos ---
    // Obtener todos los programas preventivos (ID y Nombre)
    public Map<Integer, String> obtenerProgramasPreventivos() {
        Map<Integer, String> programas = new HashMap<>();
        String sql = "SELECT id_programa, nombre_programa FROM programas_preventivos ORDER BY nombre_programa ASC";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                programas.put(rs.getInt("id_programa"), rs.getString("nombre_programa"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener programas preventivos: " + e.getMessage());
        }
        return programas;
    }

    // Obtener el nombre de un programa por su ID
    public String obtenerNombrePrograma(int idPrograma) {
        String nombre = null;
        String sql = "SELECT nombre_programa FROM programas_preventivos WHERE id_programa = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrograma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nombre = rs.getString("nombre_programa");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener nombre de programa por ID: " + e.getMessage());
        }
        return nombre;
    }

    // Validación: verificar si el paciente ya tiene una cita del mismo programa en el mismo año
    public boolean yaTieneCitaEnMismoProgramaEsteAnio(int idPaciente, int idPrograma, Timestamp fechaCita) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM citas WHERE id_paciente = ? AND id_programa = ? AND YEAR(fecha_cita) = YEAR(?)";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ps.setInt(2, idPrograma);
            ps.setTimestamp(3, fechaCita);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al validar citas duplicadas por año: " + e.getMessage());
        }

        return existe;
    }

    // Obtener el ID de un programa por su nombre (útil para la vista si se selecciona por nombre)
    public int obtenerIdProgramaPorNombre(String nombrePrograma) {
        int id = -1;
        String sql = "SELECT id_programa FROM programas_preventivos WHERE nombre_programa = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombrePrograma);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_programa");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID de programa por nombre: " + e.getMessage());
        }
        return id;
    }

    //todo lo de estadisticas ------------------------------------------------------------------------------->>>>>>
    public List<Map<String, Object>> obtenerEstadisticasPorPrograma() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
        SELECT 
            pp.nombre_programa,
            COUNT(c.id_cita) AS total,
            SUM(CASE WHEN c.estado_cita = 2 THEN 1 ELSE 0 END) AS atendidas
        FROM programas_preventivos pp
        LEFT JOIN citas c ON pp.id_programa = c.id_programa
        GROUP BY pp.nombre_programa
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                int total = rs.getInt("total");
                int atendidas = rs.getInt("atendidas");
                double porcentaje = total == 0 ? 0 : (atendidas * 100.0 / total);

                map.put("programa", rs.getString("nombre_programa"));
                map.put("total", total);
                map.put("atendidas", atendidas);
                map.put("porcentaje", porcentaje);

                lista.add(map);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas por programa: " + e.getMessage());
        }
        return lista;
    }

    //
    public List<Map<String, Object>> obtenerEstadisticasPorObstetra() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = """
        SELECT 
            u.nombre_completo,
            COUNT(c.id_cita) AS total,
            SUM(CASE WHEN c.estado_cita = 2 THEN 1 ELSE 0 END) AS atendidas
        FROM usuarios u
        LEFT JOIN citas c ON u.id = c.id_obstetra
        WHERE u.rol = 'OBSTETRA'
        GROUP BY u.nombre_completo
    """;

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                int total = rs.getInt("total");
                int atendidas = rs.getInt("atendidas");
                double porcentaje = total == 0 ? 0 : (atendidas * 100.0 / total);

                map.put("obstetra", rs.getString("nombre_completo"));
                map.put("total", total);
                map.put("atendidas", atendidas);
                map.put("porcentaje", porcentaje);

                lista.add(map);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas por obstetra: " + e.getMessage());
        }
        return lista;
        
    }

  public List<Map<String, Object>> obtenerEstadisticasPorProgramaFiltrado(String dniObstetra, List<String> programasSeleccionados, java.util.Date fechaSeleccionada) {
    List<Map<String, Object>> lista = new ArrayList<>();
    StringBuilder sql = new StringBuilder("""
        SELECT 
            pp.nombre_programa,
            COUNT(c.id_cita) AS total,
            SUM(CASE WHEN c.estado_cita = 2 THEN 1 ELSE 0 END) AS atendidas
        FROM programas_preventivos pp
        LEFT JOIN citas c 
            ON pp.id_programa = c.id_programa
        LEFT JOIN usuarios u 
            ON c.id_obstetra = u.id
        WHERE 1=1
    """);

    // Filtro por DNI si se especifica
    if (dniObstetra != null && !dniObstetra.isEmpty()) {
        sql.append(" AND u.dni = ? ");
    }

    // Filtro por programas seleccionados
    if (programasSeleccionados != null && !programasSeleccionados.isEmpty()) {
        String placeholders = String.join(", ", programasSeleccionados.stream().map(p -> "?").toList());
        sql.append(" AND pp.nombre_programa IN (").append(placeholders).append(") ");
    }

    // Filtro por MES y AÑO (no por día)
    if (fechaSeleccionada != null) {
        sql.append(" AND MONTH(c.fecha_cita) = ? AND YEAR(c.fecha_cita) = ? ");
    }

    sql.append(" GROUP BY pp.nombre_programa ");

    try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
        int index = 1;

        if (dniObstetra != null && !dniObstetra.isEmpty()) {
            ps.setString(index++, dniObstetra);
        }

        if (programasSeleccionados != null && !programasSeleccionados.isEmpty()) {
            for (String programa : programasSeleccionados) {
                ps.setString(index++, programa);
            }
        }

        if (fechaSeleccionada != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(fechaSeleccionada);
            int mes = cal.get(java.util.Calendar.MONTH) + 1; // Calendar es base 0
            int anio = cal.get(java.util.Calendar.YEAR);

            ps.setInt(index++, mes);
            ps.setInt(index++, anio);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                int total = rs.getInt("total");
                int atendidas = rs.getInt("atendidas");
                double porcentaje = total == 0 ? 0 : (atendidas * 100.0 / total);

                map.put("programa", rs.getString("nombre_programa"));
                map.put("total", total);
                map.put("atendidas", atendidas);
                map.put("porcentaje", porcentaje);
                lista.add(map);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener estadísticas por programa con filtro: " + e.getMessage());
    }

    return lista;
}

}