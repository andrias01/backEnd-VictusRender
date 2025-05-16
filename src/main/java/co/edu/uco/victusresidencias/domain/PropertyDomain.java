package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyDomain extends Domain {

    private String propertyType;
    private int propertyNumber;

    // Constructor privado
    private PropertyDomain(final UUID id, final String propertyType, final int propertyNumber) {
        super(id);
        setPropertyType(propertyType);
        setPropertyNumber(propertyNumber);
    }

    // Método estático para crear una instancia con parámetros
    public static PropertyDomain create(final UUID id, final String propertyType, final int propertyNumber) {
        return new PropertyDomain(id, propertyType, propertyNumber);
    }

    // Método estático para crear una instancia vacía por defecto
    public static PropertyDomain create() {
        return new PropertyDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, NumericHelper.CERO);
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


//    private void setPropertyZone(final PropertyZoneDomain propertyZone) {
//        this.propertyZone = ObjectHelper.getDefault(propertyZone , PropertyZoneDomain.create());
//    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
