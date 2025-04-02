package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class PropertyZoneDTO extends DomainDTO {
    
    private String propertyZoneType;
    private int propertyZoneNumber;
    private ResidentialComplexDTO residentialComplex;

    private PropertyZoneDTO() {
        super(UUIDHelper.getDefaultAsString());
        setPropertyZoneType(TextHelper.EMPTY);
        setPropertyZoneNumber(NumericHelper.CERO);
        setResidentialComplex(ResidentialComplexDTO.create());
    }

    public static PropertyZoneDTO create() {
        return new PropertyZoneDTO();
    }

    public String getPropertyZoneType() {
        return propertyZoneType;
    }

    public PropertyZoneDTO setPropertyZoneType(String propertyZoneType) {
        this.propertyZoneType = TextHelper.applyTrim(propertyZoneType);
        return this;
    }

    public int getPropertyZoneNumber() {
        return propertyZoneNumber;
    }

    public PropertyZoneDTO setPropertyZoneNumber(int propertyZoneNumber) {
        this.propertyZoneNumber = propertyZoneNumber;
        return this;
    }

    public ResidentialComplexDTO getResidentialComplex() {
        return residentialComplex;
    }

    public PropertyZoneDTO setResidentialComplex(ResidentialComplexDTO residentialComplex) {
        this.residentialComplex = ObjectHelper.getDefault(residentialComplex, ResidentialComplexDTO.create());
        return this;
    }

    public PropertyZoneDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
