package co.edu.uco.victusresidencias.dto;

import java.time.LocalDateTime;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ScheduledDTO extends DomainDTO {

    private String name;
    private Boolean availability;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private CommonZoneDTO commonArea;

    // Private constructor
    private ScheduledDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setAvailability(true);
        setStartDateTime(DateHelper.DEFAULT_DATE_TIME);
        setEndDateTime(DateHelper.DEFAULT_DATE_TIME);
        setCommonArea(CommonZoneDTO.create());
    }

    // Static method to create an instance
    public static ScheduledDTO create() {
        return new ScheduledDTO();
    }

    // Getters and Setters with return this for fluency

    public String getName() {
        return name;
    }

    public ScheduledDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public ScheduledDTO setAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public ScheduledDTO setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = ObjectHelper.getDefault(startDateTime, DateHelper.DEFAULT_DATE_TIME);
        return this;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public ScheduledDTO setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = ObjectHelper.getDefault(endDateTime, DateHelper.DEFAULT_DATE_TIME);
        return this;
    }

    public CommonZoneDTO getCommonArea() {
        return commonArea;
    }

    public ScheduledDTO setCommonArea(CommonZoneDTO commonArea) {
        this.commonArea = ObjectHelper.getDefault(commonArea, CommonZoneDTO.create());
        return this;
    }

    // Methods for handling the ID inherited from DomainDTO
    public ScheduledDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
