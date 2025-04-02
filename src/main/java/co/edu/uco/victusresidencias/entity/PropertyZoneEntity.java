package co.edu.uco.victusresidencias.entity;

import java.util.UUID;


import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyZoneEntity extends DomainEntity {
	
	private String propertyZoneType;
	private int propertyZoneNumber;
	private ResidentialComplexEntity residentialComplex;


	public PropertyZoneEntity() {
		super(UUIDHelper.getDefault());
		setPropertyZoneType(TextHelper.EMPTY);
		setPropertyZoneNumber(NumericHelper.CERO);
		setResidentialComplex(new ResidentialComplexEntity());
	}
	
	public String getPropertyZoneType() {
		return propertyZoneType;
	}

	public void setPropertyZoneType(String tipoZonaInmueble) {
		this.propertyZoneType = TextHelper.applyTrim(tipoZonaInmueble);
	}
	
	public int getPropertyZoneNumber() {
		return propertyZoneNumber;
	}

	public void setPropertyZoneNumber(int numeroZonaInmueble) {
		this.propertyZoneNumber = numeroZonaInmueble;
	}

	@Override
	public void setId(final UUID id) {
		super.setId(id);
	}
	@Override
	public UUID getId() {
		return super.getId();
	}

	public ResidentialComplexEntity getResidentialComplex() {
		return residentialComplex;
	}

	public void setResidentialComplex(final ResidentialComplexEntity residentialComplex) {
		this.residentialComplex = ObjectHelper.getDefault(residentialComplex, new ResidentialComplexEntity());
	}
	
}