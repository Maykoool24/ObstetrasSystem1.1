package modelo;

import java.time.LocalDate;

public class Paciente {
    
    
    private int id;
    private String nombreCompleto;
    private String dni;
    private LocalDate fechaNac;
    private String telefono;
    private String direccion;
    private int estado;
    
    
    // constructores
    public Paciente() {
    }

    public Paciente(int id, String nombreCompleto, String dni, LocalDate fechaNac, String telefono, String direccion, int estado) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.fechaNac = fechaNac;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
    }
    
    // g and s

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    
}

