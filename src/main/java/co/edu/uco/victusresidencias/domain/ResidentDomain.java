package co.edu.uco.victusresidencias.domain;


import java.time.LocalDate;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.DateHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;


public class ResidentDomain extends Domain {

    private String name;
    private String lastName;
    private String idType;
    private int idNumber;
    private LocalDate birthDate;
    private int contactNumber;
    private String password;
    private PropertyDomain property;

    // Constructor privado
    private ResidentDomain(final UUID id, final String name, final String lastName, final String idType, final int idNumber, 
                           final LocalDate birthDate, final int contactNumber, final String password, final PropertyDomain property) {
        super(id);
        setName(name);
        setLastName(lastName);
        setIdType(idType);
        setIdNumber(idNumber);
        setBirthDat(birthDate);
        setContactNumber(contactNumber);
        setPassword(password);
        setProperty(property);
    }

    // Método estático para crear una instancia con parámetros
    public static ResidentDomain create(final UUID id, final String name, final String lastName, final String documentType, final int documentNumber, 
                                        final LocalDate birthDate, final int contactNumber, final String password, final PropertyDomain property) {
        return new ResidentDomain(id, name, lastName, documentType, documentNumber, birthDate, contactNumber, password, property);
    }

    // Método estático para crear una instancia vacía por defecto
    public static ResidentDomain create() {
        return new ResidentDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY, NumericHelper.CERO,
                                  DateHelper.DEFAULT_DATE, NumericHelper.CERO, TextHelper.EMPTY, PropertyDomain.create());
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(final String lastName) {
        this.lastName = TextHelper.applyTrim(lastName);
    }

    public String getIdType() {
        return idType;
    }

    private void setIdType(final String documentType) {
        this.idType = TextHelper.applyTrim(documentType);
    }

    public int getIdNumber() {
        return idNumber;
    }

    private void setIdNumber(final int documentNumber) {
        this.idNumber = (documentNumber > 0) ? documentNumber : NumericHelper.CERO;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    private void setBirthDat(final LocalDate birthDate) {
        this.birthDate = (birthDate != null) ? birthDate : DateHelper.DEFAULT_DATE;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    private void setContactNumber(final int contactNumber) {
        this.contactNumber = (contactNumber > 0) ? contactNumber : NumericHelper.CERO;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(final String password) {
        this.password = TextHelper.applyTrim(password);
    }

    public PropertyDomain getProperty() {
        return property;
    }

    private void setProperty(final PropertyDomain property) {
        this.property = (property != null) ? property : PropertyDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
