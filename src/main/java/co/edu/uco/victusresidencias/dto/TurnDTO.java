package co.edu.uco.victusresidencias.dto;

import java.time.LocalDateTime;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class TurnDTO extends DomainDTO {

    private String name;
    private String shiftNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean status;
    private ScheduledDTO schedule;

    // Private constructor
    private TurnDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setShiftNumber(TextHelper.EMPTY);
        setStartTime(DateHelper.DEFAULT_DATE_TIME);
        setEndTime(DateHelper.DEFAULT_DATE_TIME);
        setStatus(true);
        setSchedule(ScheduledDTO.create());
    }

    // Static method to create an instance
    public static TurnDTO create() {
        return new TurnDTO();
    }

    // Getters and Setters with return this for fluency

    public String getName() {
        return name;
    }

    public TurnDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getShiftNumber() {
        return shiftNumber;
    }

    public TurnDTO setShiftNumber(String shiftNumber) {
        this.shiftNumber = TextHelper.applyTrim(shiftNumber);
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public TurnDTO setStartTime(LocalDateTime startTime) {
        this.startTime = ObjectHelper.getDefault(startTime, DateHelper.DEFAULT_DATE_TIME);
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public TurnDTO setEndTime(LocalDateTime endTime) {
        this.endTime = ObjectHelper.getDefault(endTime, DateHelper.DEFAULT_DATE_TIME);
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public TurnDTO setStatus(Boolean status) {
        this.status = ObjectHelper.getDefault(status, true);
        return this;
    }

    public ScheduledDTO getSchedule() {
        return schedule;
    }

    public TurnDTO setSchedule(ScheduledDTO schedule) {
        this.schedule = ObjectHelper.getDefault(schedule, ScheduledDTO.create());
        return this;
    }

    // Methods for handling the ID inherited from DomainDTO
    public TurnDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
