package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class AdministratorDTO extends DomainDTO {
    
    private String name;
    private String lastName;
    private String idType;
    private String idNumber;
    private String contactNumber;
    private String email;
    private String password;

    public AdministratorDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setLastName(TextHelper.EMPTY);
        setIdType(TextHelper.EMPTY);
        setIdNumber(TextHelper.EMPTY);
        setContactNumber(TextHelper.EMPTY);
        setEmail(TextHelper.EMPTY);
        setPassword(TextHelper.EMPTY);
    }

    public static final AdministratorDTO create() {
        return new AdministratorDTO();
    }

    public String getName() {
        return name;
    }

    public AdministratorDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AdministratorDTO setLastName(String lastName) {
        this.lastName = TextHelper.applyTrim(lastName);
        return this;
    }

    public String getIdType() {
        return idType;
    }

    public AdministratorDTO setIdType(String idType) {
        this.idType = TextHelper.applyTrim(idType);
        return this;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public AdministratorDTO setIdNumber(final String idNumber) {
        this.idNumber = TextHelper.applyTrim(idNumber);
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public AdministratorDTO setContactNumber(String contactNumber) {
        this.contactNumber = TextHelper.applyTrim(contactNumber);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdministratorDTO setEmail(String email) {
        this.email = TextHelper.applyTrim(email);
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AdministratorDTO setPassword(String password) {
        this.password = TextHelper.applyTrim(password);
        return this;
    }

    public AdministratorDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
