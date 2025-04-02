package co.edu.uco.victusresidencias.entity;

import java.util.UUID;


import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyEntity extends DomainEntity {
	
	private String propertyType;
	private int propertyNumber;
	private PropertyZoneEntity propertyZone;
	
	public PropertyEntity() {
		super(UUIDHelper.getDefault());
		setPropertyType(TextHelper.EMPTY);
		setPropertyNumber(NumericHelper.CERO);
		setPropertyZone(new PropertyZoneEntity());
	}
	
	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = TextHelper.applyTrim(propertyType);
	}
	
	
	public int getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(int propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	@Override
	public void setId(final UUID id) {
		super.setId(id);
	}
	@Override
	public UUID getId() {
		return super.getId();
	}

	public PropertyZoneEntity getPropertyZone() {
		return propertyZone;
	}

	public void setPropertyZone(final PropertyZoneEntity propertyZone) {
		this.propertyZone = ObjectHelper.getDefault(propertyZone, new PropertyZoneEntity());
	}
	
}