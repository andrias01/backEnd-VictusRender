package co.edu.uco.victusresidencias.entity;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class DomainEntity {
	private UUID id;
	
	protected DomainEntity(final UUID id) {
		setId(id);
	}

	protected UUID getId() {
		return id;
	}
	
	protected void setId(final UUID id) {
		this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
	}

}