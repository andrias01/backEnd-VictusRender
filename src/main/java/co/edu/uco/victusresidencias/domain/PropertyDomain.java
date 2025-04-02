package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyDomain extends Domain {

    private String propertyType;
    private int propertyNumber;
    private PropertyZoneDomain propertyZone;

    // Constructor privado
    private PropertyDomain(final UUID id, final String propertyType, final int propertyNumber, final PropertyZoneDomain propertyZone) {
        super(id);
        setPropertyType(propertyType);
        setPropertyNumber(propertyNumber);
        setPropertyZone(propertyZone);
    }

    // Método estático para crear una instancia con parámetros
    public static PropertyDomain create(final UUID id, final String propertyType, final int propertyNumber, final PropertyZoneDomain propertyZone) {
        return new PropertyDomain(id, propertyType, propertyNumber, propertyZone);
    }

    // Método estático para crear una instancia vacía por defecto
    public static PropertyDomain create() {
        return new PropertyDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, NumericHelper.CERO, PropertyZoneDomain.create());
    }

    // Getters y Setters

    public String getPropertyType() {
        return this.propertyType;
    }

    private void setPropertyType(final String propertyType) {
        this.propertyType = TextHelper.applyTrim(propertyType);
    }

    public int getPropertyNumber() {
        return this.propertyNumber;
    }

    private void setPropertyNumber(final int propertyNumber) {
        this.propertyNumber = (propertyNumber >= 0) ? propertyNumber : NumericHelper.CERO;
    }

    public PropertyZoneDomain getPropertyZone() {
        return this.propertyZone;
    }

    private void setPropertyZone(final PropertyZoneDomain propertyZone) {
        this.propertyZone = ObjectHelper.getDefault(propertyZone , PropertyZoneDomain.create());
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
