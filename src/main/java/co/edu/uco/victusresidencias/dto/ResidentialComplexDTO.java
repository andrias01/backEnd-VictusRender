package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ResidentialComplexDTO extends DomainDTO {

    private String name;
    private String address;
    private String phoneNumber;
    private AdministratorDTO administrator;
    private CityDTO city;
    private String description;

    // Private constructor
    private ResidentialComplexDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setAddress(TextHelper.EMPTY);
        setDescription(TextHelper.EMPTY);
        setPhoneNumber(TextHelper.EMPTY);
        setAdministrator(AdministratorDTO.create());
    }

    // Static method to create an instance
    public static ResidentialComplexDTO create() {
        return new ResidentialComplexDTO();
    }

    // Getters and Setters with return this for fluency

    public String getName() {
        return name;
    }

    public ResidentialComplexDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ResidentialComplexDTO setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public String getDescription() {
        return description;
    }

    public ResidentialComplexDTO setAddress(String address) {
        this.address = TextHelper.applyTrim(address);
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ResidentialComplexDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = TextHelper.applyTrim(phoneNumber);
        return this;
    }

    public AdministratorDTO getAdministrator() {
        return administrator;
    }

    public ResidentialComplexDTO setAdministrator(AdministratorDTO administrator) {
        this.administrator = administrator != null ? administrator : AdministratorDTO.create();
        return this;
    }
    
    public CityDTO getCity() {
        return city;
    }

    public ResidentialComplexDTO setCity(CityDTO city) {
        this.city = city != null ? city : CityDTO.create();
        return this;
    }

    // Methods for handling the ID inherited from DomainDTO
    public ResidentialComplexDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
