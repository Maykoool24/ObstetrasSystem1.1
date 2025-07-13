package modelo;

import java.sql.Timestamp;

public class Usuario {
    
    private int id;
    private String nombreCompleto;
    private String usuario;
    private String contrasena;
    private String dni; // ¡Añade esta línea!
    private String preguntaSeguridad;
    private String respuestaSeguridad;
    private int intentosRestantes;
    private int estado;
    private String rol;
    private Timestamp fechaBloqueo; 

    // Getters y Setters 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // Getter y Setter para DNI
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPreguntaSeguridad() {
        return preguntaSeguridad;
    }

    public void setPreguntaSeguridad(String preguntaSeguridad) {
        this.preguntaSeguridad = preguntaSeguridad;
    }

    public String getRespuestaSeguridad() {
        return respuestaSeguridad;
    }

    public void setRespuestaSeguridad(String respuestaSeguridad) {
        this.respuestaSeguridad = respuestaSeguridad;
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public void setIntentosRestantes(int intentosRestantes) {
        this.intentosRestantes = intentosRestantes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Timestamp getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Timestamp fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Usuario() {
    }
    
    // Constructor completo (actualizado para incluir DNI)
    public Usuario(int id, String nombreCompleto, String usuario, String contrasena, String dni, // ¡Añadido dni aquí!
                   String preguntaSeguridad, String respuestaSeguridad,
                   int intentosRestantes, int estado, String rol, Timestamp fechaBloqueo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.dni = dni; // Asignación del nuevo campo
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSeguridad = respuestaSeguridad;
        this.intentosRestantes = intentosRestantes;
        this.estado = estado;
        this.rol = rol;
        this.fechaBloqueo = fechaBloqueo;
    }
}