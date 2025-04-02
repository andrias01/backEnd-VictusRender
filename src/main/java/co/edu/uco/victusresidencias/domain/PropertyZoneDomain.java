package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyZoneDomain extends Domain {

    private String propertyZoneType;
    private int propertyZoneNumber;
    private ResidentialComplexDomain residentialComplex;

    // Constructor privado
    private PropertyZoneDomain(final UUID id, final String propertyZoneType, final int propertyZoneNumber, final ResidentialComplexDomain residentialComplex) {
        super(id);
        setPropertyZoneType(propertyZoneType);
        setPropertyZoneNumber(propertyZoneNumber);
        setResidentialComplex(residentialComplex);
    }

    // Método estático para crear una instancia con parámetros
    public static PropertyZoneDomain create(final UUID id, final String propertyZoneType, final int propertyZoneNumber, 
    		final ResidentialComplexDomain residentialComplex) {
        return new PropertyZoneDomain(id, propertyZoneType, propertyZoneNumber, residentialComplex);
    }

    // Método estático para crear una instancia vacía por defecto
    public static PropertyZoneDomain create() {
        return new PropertyZoneDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, NumericHelper.CERO, ResidentialComplexDomain.create());
    }

    // Getters y Setters

    public String getPropertyZoneType() {
        return propertyZoneType;
    }

    private void setPropertyZoneType(final String propertyZoneType) {
        this.propertyZoneType = TextHelper.applyTrim(propertyZoneType);
    }

    public int getPropertyZoneNumber() {
        return propertyZoneNumber;
    }

    private void setPropertyZoneNumber(final int propertyZoneNumber) {
        this.propertyZoneNumber = (propertyZoneNumber >= 0) ? propertyZoneNumber : NumericHelper.CERO;
    }

    public ResidentialComplexDomain getResidentialComplex() {
        return residentialComplex;
    }

    private void setResidentialComplex(final ResidentialComplexDomain residentialComplex) {
        this.residentialComplex = (residentialComplex != null) ? residentialComplex: ResidentialComplexDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
