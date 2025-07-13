package controlador;

import java.sql.*;
import java.util.ArrayList; // Importar ArrayList
import java.util.List;     // Importar List
import modelo.Usuario;
import conexion.Conexion;
import utilidades.HashUtil; // Asegúrate de que esta clase existe y funciona correctamente

public class ControladorUsuario {

    private static final int MAX_INTENTOS = 5;
    private static final long TIEMPO_BLOQUEO_MS = 300000L; // 5 minutos
    public static final int ESTADO_BLOQUEADO = 0;
    private static final int ESTADO_ACTIVO = 1;

    // --- Métodos de Autenticación y Bloqueo ---
    public Usuario login(String usuario, String contrasena) {
        Usuario user = obtenerUsuario(usuario);

        if (user == null) {
            // Usuario no encontrado
            return null;
        }

        // Si el usuario está bloqueado, verificar si ha pasado el tiempo de bloqueo
        if (user.getEstado() == ESTADO_BLOQUEADO) {
            Timestamp fechaBloqueo = user.getFechaBloqueo();
            if (fechaBloqueo != null) {
                long tiempoBloqueo = fechaBloqueo.getTime();
                long tiempoActual = System.currentTimeMillis();

                if (tiempoActual - tiempoBloqueo > TIEMPO_BLOQUEO_MS) {
                    // Ha pasado el tiempo de bloqueo, desbloquear y permitir el intento
                    desbloquearUsuario(usuario);
                    user.setEstado(ESTADO_ACTIVO);
                    user.setIntentosRestantes(MAX_INTENTOS);
                    user.setFechaBloqueo(null); // Limpiar fecha de bloqueo en el objeto
                } else {
                    // Usuario bloqueado y tiempo no ha expirado
                    System.out.println("Login fallido: Usuario " + usuario + " bloqueado temporalmente.");
                    return null;
                }
            } else {
                // Estado bloqueado pero sin fecha de bloqueo (posible inconsistencia, tratar como bloqueado)
                System.out.println("Login fallido: Usuario " + usuario + " bloqueado (sin fecha de bloqueo).");
                return null;
            }
        }

        // Verificar contraseña
        String claveIngresadaHash = HashUtil.sha256(contrasena);
        if (!user.getContrasena().equals(claveIngresadaHash)) {
            int intentosRestantes = user.getIntentosRestantes() - 1;
            actualizarIntentos(usuario, intentosRestantes); // Actualiza en la DB
            System.out.println("Login fallido: Contraseña incorrecta para " + usuario + ". Intentos restantes: " + intentosRestantes);
            if (intentosRestantes <= 0) {
                bloquearUsuario(usuario); // Bloquea en la DB
                System.out.println("Usuario " + usuario + " bloqueado por exceso de intentos fallidos.");
            }
            return null;
        }

        // Contraseña correcta, reiniciar intentos y devolver usuario
        reiniciarIntentos(usuario); // Actualiza en la DB
        user.setIntentosRestantes(MAX_INTENTOS); // Actualiza en el objeto
        user.setEstado(ESTADO_ACTIVO); // Asegurar que el estado en el objeto esté activo
        user.setFechaBloqueo(null); // Limpiar fecha de bloqueo en el objeto
        return user;
    }

    // Obtener un usuario por su nombre de usuario
    public Usuario obtenerUsuario(String usuario) {
        Usuario u = null;
        // Se agregó la columna 'dni' a la selección
        String sql = "SELECT id, nombre_completo, usuario, contrasena, dni, pregunta_seguridad, respuesta_seguridad, intentos_restantes, estado, rol, fecha_bloqueo FROM usuarios WHERE usuario = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("dni"), // Leer DNI directamente
                        rs.getString("pregunta_seguridad"),
                        rs.getString("respuesta_seguridad"),
                        rs.getInt("intentos_restantes"),
                        rs.getInt("estado"),
                        rs.getString("rol"),
                        rs.getTimestamp("fecha_bloqueo")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage()); // Usar System.err para errores
        }
        return u;
    }

    // Reiniciar intentos de login y desbloquear si estaba bloqueado
    public void reiniciarIntentos(String usuario) {
        try (Connection con = Conexion.conectar()) {
            String sql = "UPDATE usuarios SET intentos_restantes = ?, fecha_bloqueo = NULL, estado = ? WHERE usuario = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, MAX_INTENTOS);
            pst.setInt(2, ESTADO_ACTIVO);
            pst.setString(3, usuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al reiniciar los intentos: " + e.getMessage());
        }
    }

    // Desbloquear usuario (equivalente a reiniciar intentos)
    public void desbloquearUsuario(String usuario) {
        reiniciarIntentos(usuario);
        System.out.println("Usuario " + usuario + " ha sido desbloqueado y sus intentos reiniciados.");
    }

    // Actualizar el número de intentos restantes
    public void actualizarIntentos(String usuario, int intentosRestantes) {
        try (Connection con = Conexion.conectar()) {
            String sql = "UPDATE usuarios SET intentos_restantes = ? WHERE usuario = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, intentosRestantes);
            pst.setString(2, usuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar los intentos: " + e.getMessage());
        }
    }

    // Bloquear un usuario por exceso de intentos
    public void bloquearUsuario(String usuario) {
        try (Connection con = Conexion.conectar()) {
            String sql = "UPDATE usuarios SET estado = ?, fecha_bloqueo = ?, intentos_restantes = 0 WHERE usuario = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ESTADO_BLOQUEADO);
            pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pst.setString(3, usuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al bloquear usuario: " + e.getMessage());
        }
    }

    // --- Métodos de Gestión de Usuario (CRUD y auxiliares) ---
    // Obtener pregunta y respuesta de seguridad (para recuperación)
    public String[] obtenerPreguntaYRespuestaSeguridad(String usuario) {
        String[] datosSeguridad = null;
        // Se agregó la columna 'estado' a la selección para una posible validación futura
        String sql = "SELECT pregunta_seguridad, respuesta_seguridad, estado FROM usuarios WHERE usuario = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                datosSeguridad = new String[2];
                datosSeguridad[0] = rs.getString("pregunta_seguridad");
                // La respuesta de seguridad debe ser verificada con hash
                datosSeguridad[1] = rs.getString("respuesta_seguridad");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pregunta y respuesta de seguridad: " + e.getMessage());
        }
        return datosSeguridad;
    }

    // Actualizar contraseña y restaurar estado a activo/intentos a MAX_INTENTOS
    public boolean actualizarContrasenaYRestauraEstado(String usuario, String nuevaContrasena) {
        try (Connection con = Conexion.conectar()) {
            String hashNuevaClave = HashUtil.sha256(nuevaContrasena);

            String sql = "UPDATE usuarios SET contrasena = ?, intentos_restantes = ?, estado = ?, fecha_bloqueo = NULL WHERE usuario = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hashNuevaClave);
            pst.setInt(2, MAX_INTENTOS);       // Reiniciar intentos a 5
            pst.setInt(3, ESTADO_ACTIVO);     // Poner estado en activo (1)
            pst.setString(4, usuario);

            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar contraseña y restaurar estado: " + e.getMessage());
            return false;
        }
    }

    // Registrar nuevo usuario
    public boolean registrar(Usuario usuario) {

        String sql = "INSERT INTO usuarios (usuario, contrasena, nombre_completo, dni, pregunta_seguridad, respuesta_seguridad, intentos_restantes, estado, rol, fecha_bloqueo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)"; // Usamos '?' para intentos y estado iniciales

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuario());
            // Hashear la contraseña antes de guardar
            String contrasenaHasheada = HashUtil.sha256(usuario.getContrasena());
            ps.setString(2, contrasenaHasheada);
            ps.setString(3, usuario.getNombreCompleto());
            ps.setString(4, usuario.getDni()); // Nuevo campo DNI
            ps.setString(5, usuario.getPreguntaSeguridad());
            ps.setString(6, usuario.getRespuestaSeguridad()); 

            ps.setInt(7, MAX_INTENTOS); // Intentos iniciales
            ps.setInt(8, ESTADO_ACTIVO); // Estado inicial activo
            ps.setString(9, usuario.getRol());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.toString()); // Usar err y to.String() para detalles
            return false;
        }
    }

    // Listar todos los usuarios activos
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        // Asegúrate de seleccionar el DNI en la consulta SQL
        String sql = "SELECT id, nombre_completo, usuario, contrasena, dni, pregunta_seguridad, respuesta_seguridad, intentos_restantes, estado, rol, fecha_bloqueo FROM usuarios WHERE estado = 1";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre_completo"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("dni"), // Leer DNI
                        rs.getString("pregunta_seguridad"),
                        rs.getString("respuesta_seguridad"),
                        rs.getInt("intentos_restantes"),
                        rs.getInt("estado"),
                        rs.getString("rol"),
                        rs.getTimestamp("fecha_bloqueo")
                );
                // El bloque try-catch para DNI en listar() ya no es estrictamente necesario
                // si la columna 'dni' siempre va a existir, pero no hace daño.
                // Lo he integrado directamente en el constructor para mayor limpieza.
                lista.add(u);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.toString());
        }

        return lista;
    }

    // Modificar usuario
    public boolean modificar(Usuario usuario) {
        String sql;
        String contrasena = usuario.getContrasena();
        boolean updatePassword = (contrasena != null && !contrasena.isEmpty());

        if (updatePassword) {
            // Actualiza también contrasena y DNI
            sql = "UPDATE usuarios SET usuario = ?, contrasena = ?, nombre_completo = ?, dni = ?, pregunta_seguridad = ?, respuesta_seguridad = ?, rol = ? WHERE id = ?";
        } else {
            // No actualiza contrasena, pero sí DNI
            sql = "UPDATE usuarios SET usuario = ?, nombre_completo = ?, dni = ?, pregunta_seguridad = ?, respuesta_seguridad = ?, rol = ? WHERE id = ?";
        }

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            ps.setString(i++, usuario.getUsuario());
            if (updatePassword) {
                // Hashea la nueva contraseña si se va a actualizar
                ps.setString(i++, HashUtil.sha256(contrasena));
            }
            ps.setString(i++, usuario.getNombreCompleto());
            ps.setString(i++, usuario.getDni()); // Campo DNI
            ps.setString(i++, usuario.getPreguntaSeguridad());
            // Considera hashear la respuesta de seguridad aquí también
            ps.setString(i++, usuario.getRespuestaSeguridad());
            ps.setString(i++, usuario.getRol());
            ps.setInt(i++, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al modificar usuario: " + e.toString());
            return false;
        }
    }

    // Eliminación lógica de usuario (cambiar estado a 0)
    public boolean eliminar(int id) {
        String sql = "UPDATE usuarios SET estado = 0 WHERE id = ?";

        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.toString());
            return false;
        }
    }

    // Método para verificar si un usuario ya existe por su nombre de usuario
    public boolean existeUsuario(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de usuario: " + e.toString());
        }
        return false;
    }

    // Método para verificar si un DNI ya existe
    public boolean existeDNI(String dni) {
        // Excluir DNI nulo si no quieres que los nulos afecten la unicidad
        // String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ? AND dni IS NOT NULL";
        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ?"; // Si la unicidad del DNI ya la manejas con el índice filtrado
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de DNI: " + e.toString());
        }
        return false;
    }

    // Método para verificar si un DNI ya existe, excluyendo el ID del usuario actual (para modificar)
    public boolean existeDNIExcluyendoId(String dni, int idUsuario) {
        // Similar al anterior, podrías añadir 'AND dni IS NOT NULL' si fuera el caso
        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ? AND id != ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setInt(2, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de DNI (excluyendo ID): " + e.toString());
        }
        return false;
    }

    // Método para verificar si un usuario ya existe por su nombre de usuario, excluyendo el ID del usuario actual (para modificar)
    public boolean existeUsuarioExcluyendoId(String usuario, int idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND id != ?";
        try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setInt(2, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de usuario (excluyendo ID): " + e.toString());
        }
        return false;
    }
    
    // Obtener nombre completo por DNI (para autocompletar en Estadísticas)
public String obtenerNombrePorDni(String dni) {
    String nombre = null;
    String sql = "SELECT nombre_completo FROM usuarios WHERE dni = ? AND estado = 1";

    try (Connection con = Conexion.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, dni);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                nombre = rs.getString("nombre_completo");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener nombre por DNI: " + e.toString());
    }

    return nombre;
}

}
