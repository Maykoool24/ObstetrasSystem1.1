package modelo;

import java.sql.Timestamp;

public class Cita {

    public static final int ESTADO_PENDIENTE = 1;
    public static final int ESTADO_ATENDIDO = 2;
    public static final int ESTADO_CANCELADO = 0;

    private int idCita;
    private int idObstetra;
    private int idPaciente;
    private Timestamp fechaCita;
    private int idPrograma;
    private int estadoCita;
    private String observaciones;
    private Timestamp fechaRegistro;

    public Cita() {
    }

    public Cita(int idObstetra, int idPaciente, Timestamp fechaCita, int idPrograma, int estadoCita, String observaciones) {
        this.idObstetra = idObstetra;
        this.idPaciente = idPaciente;
        this.fechaCita = fechaCita;
        this.idPrograma = idPrograma;
        this.estadoCita = estadoCita;
        this.observaciones = observaciones;
    }

// Constructor completo (para leer de la DB)
    public Cita(int idCita, int idObstetra, int idPaciente, Timestamp fechaCita, int idPrograma, int estadoCita, String observaciones, Timestamp fechaRegistro) {
        this.idCita = idCita;
        this.idObstetra = idObstetra;
        this.idPaciente = idPaciente;
        this.fechaCita = fechaCita;
        this.idPrograma = idPrograma;
        this.estadoCita = estadoCita;
        this.observaciones = observaciones;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdObstetra() {
        return idObstetra;
    }

    public void setIdObstetra(int idObstetra) {
        this.idObstetra = idObstetra;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Timestamp getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Timestamp fechaCita) {
        this.fechaCita = fechaCita;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public int getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(int estadoCita) {
        this.estadoCita = estadoCita;
    }

    // Método auxiliar para obtener el nombre del estado (para la UI)
    public String getEstadoCitaString() {
        switch (estadoCita) {
            case ESTADO_PENDIENTE:
                return "PENDIENTE";
            case ESTADO_ATENDIDO:
                return "ATENDIDO";
            case ESTADO_CANCELADO:
                return "CANCELADO";
            default:
                return "DESCONOCIDO";
        }
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
