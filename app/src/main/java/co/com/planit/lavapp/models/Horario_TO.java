package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */

@SuppressWarnings("serial")
public class Horario_TO implements Serializable {

    /**
     *
     * Columna idHorario
     */
    private int idHorario;

    /**
     *
     * Columna horaInicio
     */
    private String horaInicio;

    /**
     *
     * Columna horaFinal
     */
    private String horaFinal;

    /**
     *
     * Columna idJornada
     */
    private Jornada_TO jornada;

    private String horario;

    //Constructores

    public Horario_TO() {
    }

    //Constructor General
    public Horario_TO(int idHorario) {
        this.idHorario = idHorario;
    }

    public Horario_TO(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Horario_TO(String horaFinal, int quemado) {
        this.horaFinal = horaFinal;
    }

    public Horario_TO(int idHorario, String horaInicio, String horaFinal) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public Horario_TO(int idHorario, String horaInicio, String horaFinal, Jornada_TO jornada, String horario) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.jornada = jornada;
        this.horario = horario;
    }

    public Horario_TO(String horaInicio, String horaFinal, Jornada_TO jornada, String horario) {
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.jornada = jornada;
        this.horario = horario;
    }

    //Getter and Setter
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Jornada_TO getJornada() {
        return jornada;
    }

    public void setJornada(Jornada_TO jornada) {
        this.jornada = jornada;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Horario_TO{" + "idHorario=" + idHorario + ", horaInicio=" + horaInicio + ", horaFinal=" + horaFinal + ", jornada=" + jornada + ", horario =" + horario + '}';
    }

}
