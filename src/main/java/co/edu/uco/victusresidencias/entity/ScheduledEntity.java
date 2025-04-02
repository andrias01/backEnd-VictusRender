package co.edu.uco.victusresidencias.entity;

import java.time.LocalDateTime;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ScheduledEntity extends DomainEntity {
	
	private String name;
	private Boolean availability; //availobility pronunciación =)
	private LocalDateTime dateTimeStart;
	private LocalDateTime dateTimeEnd;
	private CommonZoneEntity commonZone;
	
	
	public ScheduledEntity() {
		super(UUIDHelper.getDefault());
		setName(TextHelper.EMPTY);
		setAvailability(true);
		setDateTimeStart(DateHelper.DEFAULT_DATE_TIME);
		setDateTimeEnd(DateHelper.DEFAULT_DATE_TIME);
		setCommonZone(new CommonZoneEntity());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = TextHelper.applyTrim(name);
	}
	
	
	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public LocalDateTime getDateTimeStart() {
		return dateTimeStart;
	}

	public void setDateTimeStart(LocalDateTime dateTimeStart) {
		this.dateTimeStart = dateTimeStart;
	}

	public LocalDateTime getDateTimeEnd() {
		return dateTimeEnd;
	}

	public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
		this.dateTimeEnd = dateTimeEnd;
	}

	@Override
	public void setId(final UUID id) {
		super.setId(id);
	}
	@Override
	public UUID getId() {
		return super.getId();
	}

	public CommonZoneEntity getCommonZone() {
		return commonZone;
	}

	public void setCommonZone(final CommonZoneEntity commonZone) {
		this.commonZone = ObjectHelper.getDefault(commonZone, new CommonZoneEntity());
	}
	
}