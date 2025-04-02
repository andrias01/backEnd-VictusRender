package co.edu.uco.victusresidencias.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ScheduledDomain extends Domain {
    
    private String name;
    private Boolean availability; // Cambiado de disponibilidad a availability
    private LocalDateTime startDateTime; // Cambiado de fechaHoraInicio a startDateTime
    private LocalDateTime endDateTime; // Cambiado de fechaHoraFin a endDateTime
    private CommonZoneDomain commonZone; // Cambiado de zonaComun a commonZone

    // Private constructor
    private ScheduledDomain(final UUID id, final String name, final Boolean availability, final LocalDateTime startDateTime,
                            final LocalDateTime endDateTime, final CommonZoneDomain commonZone) {
        super(id);
        setName(name);
        setAvailability(availability);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        setCommonZone(commonZone);
    }

    // Static method to create an instance with parameters
    public static ScheduledDomain create(final UUID id, final String name, final Boolean availability, final LocalDateTime startDateTime, 
                                         final LocalDateTime endDateTime, final CommonZoneDomain commonZone) {
        return new ScheduledDomain(id, name, availability, startDateTime, endDateTime, commonZone);
    }

    // Static method to create an empty default instance
    public static ScheduledDomain create() {
        return new ScheduledDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, true, DateHelper.DEFAULT_DATE_TIME, 
                                   DateHelper.DEFAULT_DATE_TIME, CommonZoneDomain.create());
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public Boolean getAvailability() { // Cambiado de getDisponibilidad a getAvailability
        return availability;
    }

    private void setAvailability(final Boolean availability) { // Cambiado de setDisponibilidad a setAvailability
        this.availability = (availability != null) ? availability : Boolean.TRUE;
    }

    public LocalDateTime getStartDateTime() { // Cambiado de getFechaHoraInicio a getStartDateTime
        return startDateTime;
    }

    private void setStartDateTime(final LocalDateTime startDateTime) { // Cambiado de setFechaHoraInicio a setStartDateTime
        this.startDateTime = (startDateTime != null) ? startDateTime : DateHelper.DEFAULT_DATE_TIME;
    }

    public LocalDateTime getEndDateTime() { // Cambiado de getFechaHoraFin a getEndDateTime
        return endDateTime;
    }

    private void setEndDateTime(final LocalDateTime endDateTime) { // Cambiado de setFechaHoraFin a setEndDateTime
        this.endDateTime = (endDateTime != null) ? endDateTime : DateHelper.DEFAULT_DATE_TIME;
    }

    public CommonZoneDomain getCommonZone() { // Cambiado de getZonaComun a getCommonZone
        return commonZone;
    }

    private void setCommonZone(final CommonZoneDomain commonZone) { // Cambiado de setZonaComun a setCommonZone
        this.commonZone = (commonZone != null) ? commonZone : CommonZoneDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
