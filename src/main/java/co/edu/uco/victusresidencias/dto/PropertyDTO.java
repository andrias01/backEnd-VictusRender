package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyDTO extends DomainDTO {
    
    private String propertyType;
    private int housingNumber;

    private PropertyDTO() {
        super(UUIDHelper.getDefaultAsString());
        setPropertyType(TextHelper.EMPTY);
        setHousingNumber(NumericHelper.CERO);
    }

    public static PropertyDTO create() {
        return new PropertyDTO();
    }

    public String getPropertyType() {
        return propertyType;
    }

    public PropertyDTO setPropertyType(String propertyType) {
        this.propertyType = TextHelper.applyTrim(propertyType);
        return this;
    }

    public int getHousingNumber() {
        return housingNumber;
    }

    public PropertyDTO setHousingNumber(int housingNumber) {
        this.housingNumber = housingNumber;
        return this;
    }

    public PropertyDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
