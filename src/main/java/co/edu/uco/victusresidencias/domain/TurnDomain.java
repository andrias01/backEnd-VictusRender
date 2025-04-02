package co.edu.uco.victusresidencias.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class TurnDomain extends Domain {
    
    private String name;
    private String shiftNumber; // Cambiado de numeroTurno a shiftNumber
    private LocalDateTime startTime; // Cambiado de horaInicio a startTime
    private LocalDateTime endTime; // Cambiado de horaFin a endTime
    private Boolean status; // Cambiado de estado a status
    private ScheduledDomain schedule; // Cambiado de agenda a schedule

    // Private constructor
    private TurnDomain(final UUID id, final String name, final String shiftNumber, final LocalDateTime startTime,
                        final LocalDateTime endTime, final Boolean status, final ScheduledDomain schedule) {
        super(id);
        setName(name);
        setShiftNumber(shiftNumber);
        setStartTime(startTime);
        setEndTime(endTime);
        setStatus(status);
        setSchedule(schedule);
    }

    // Static method to create an instance with parameters
    public static TurnDomain create(final UUID id, final String name, final String shiftNumber, final LocalDateTime startTime, 
                                     final LocalDateTime endTime, final Boolean status, final ScheduledDomain schedule) {
        return new TurnDomain(id, name, shiftNumber, startTime, endTime, status, schedule);
    }

    // Static method to create an empty default instance
    public static TurnDomain create() {
        return new TurnDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, DateHelper.DEFAULT_DATE_TIME,
                               DateHelper.DEFAULT_DATE_TIME, true, ScheduledDomain.create());
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getShiftNumber() { // Cambiado de getNumeroTurno a getShiftNumber
        return shiftNumber;
    }

    private void setShiftNumber(final String shiftNumber) { // Cambiado de setNumeroTurno a setShiftNumber
        this.shiftNumber = TextHelper.applyTrim(shiftNumber);
    }

    public LocalDateTime getStartTime() { // Cambiado de getHoraInicio a getStartTime
        return startTime;
    }

    private void setStartTime(final LocalDateTime startTime) { // Cambiado de setHoraInicio a setStartTime
        this.startTime = (startTime != null) ? startTime : DateHelper.DEFAULT_DATE_TIME;
    }

    public LocalDateTime getEndTime() { // Cambiado de getHoraFin a getEndTime
        return endTime;
    }

    private void setEndTime(final LocalDateTime endTime) { // Cambiado de setHoraFin a setEndTime
        this.endTime = (endTime != null) ? endTime : DateHelper.DEFAULT_DATE_TIME;
    }

    public Boolean getStatus() { // Cambiado de getEstado a getStatus
        return status;
    }

    private void setStatus(final Boolean status) { // Cambiado de setEstado a setStatus
        this.status = (status != null) ? status : Boolean.TRUE;
    }

    public ScheduledDomain getSchedule() { // Cambiado de getAgenda a getSchedule
        return schedule;
    }

    private void setSchedule(final ScheduledDomain schedule) { // Cambiado de setAgenda a setSchedule
        this.schedule = (schedule != null) ? schedule : ScheduledDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
