package co.edu.uco.victusresidencias.entity;

import java.time.LocalDateTime;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class TurnEntity extends DomainEntity {
	
	private String name;
	private String shiftNumber;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Boolean status;
	private ScheduledEntity scheduled;
	
	
	public TurnEntity() {
		super(UUIDHelper.getDefault());
		setName(TextHelper.EMPTY);
		setShiftNumber(TextHelper.EMPTY);
		setStartTime(DateHelper.DEFAULT_DATE_TIME);
		setEndTime(DateHelper.DEFAULT_DATE_TIME);
		setStatus(true);
		setScheduled(new ScheduledEntity());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = TextHelper.applyTrim(name);
	}
	
	public String getShiftNumber() {
		return shiftNumber;
	}

	public void setShiftNumber(String shiftNumber) {
		this.shiftNumber = shiftNumber;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public void setId(final UUID id) {
		super.setId(id);
	}
	@Override
	public UUID getId() {
		return super.getId();
	}

	public ScheduledEntity getScheduled() {
		return scheduled;
	}

	public void setScheduled(final ScheduledEntity scheduled) {
		this.scheduled = ObjectHelper.getDefault(scheduled, new ScheduledEntity());
	}
}