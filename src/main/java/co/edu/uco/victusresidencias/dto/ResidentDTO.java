package co.edu.uco.victusresidencias.dto;

import java.time.LocalDate;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ResidentDTO extends DomainDTO {

    private String name;
    private String lastName;
    private String idType;
    private int idNumber;
    private LocalDate date;
    private int contactNumber;
    private String password;
    private PropertyDTO property;

    // Private constructor
    private ResidentDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setLastName(TextHelper.EMPTY);
        setIdType(TextHelper.EMPTY);
        setIdNumber(NumericHelper.CERO);
        setDate(DateHelper.DEFAULT_DATE);
        setContactNumber(NumericHelper.CERO);
        setPassword(TextHelper.EMPTY);
        setProperty(PropertyDTO.create());
    }

    // Static method to create an instance
    public static ResidentDTO create() {
        return new ResidentDTO();
    }

    // Getters and Setters with return this for fluency

    public String getName() {
        return name;
    }

    public ResidentDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ResidentDTO setLastName(String lastName) {
        this.lastName = TextHelper.applyTrim(lastName);
        return this;
    }

    public String getIdType() {
        return idType;
    }

    public ResidentDTO setIdType(String idType) {
        this.idType = TextHelper.applyTrim(idType);
        return this;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public ResidentDTO setIdNumber(int idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public ResidentDTO setDate(LocalDate date) {
        this.date = ObjectHelper.getDefault(date, DateHelper.DEFAULT_DATE);
        return this;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public ResidentDTO setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ResidentDTO setPassword(String password) {
        this.password = TextHelper.applyTrim(password);
        return this;
    }

    public PropertyDTO getProperty() {
        return property;
    }

    public ResidentDTO setProperty(PropertyDTO property) {
        this.property = ObjectHelper.getDefault(property, PropertyDTO.create());
        return this;
    }

    // Methods for handling the ID inherited from DomainDTO
    public ResidentDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
