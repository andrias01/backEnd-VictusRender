package co.edu.uco.victusresidencias.domain;

import java.util.UUID;


import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class AdministratorDomain extends Domain {

    private String name;
    private String lastName;
    private String idType;
    private String idNumber;
    private String contactNumber;
    private String email;
    private String password;

    //construtor privado solo el Domain
    private AdministratorDomain(final UUID id, final String name, final String lastName, final String idType, 
                                final String idNumber, final String contactNumber, final String email, final String password) {
        super(id);
        setName(name);
        setLastName(lastName);
        setIdType(idType);
        setIdNumber(idNumber);
        setContactNumber(contactNumber);
        setEmail(email);
        setPassword(password);
    }

    // Método estático para crear una instancia
    public static final AdministratorDomain create(
    		final UUID id, 
    		final String name, 
    		final String lastName,
            final String idType, 
            final String idNumber, 
            final String contactNumber, 
            final String email, 
            final String password) {
        return new AdministratorDomain(
        		id, 
        		name, 
        		lastName, 
        		idType, 
        		idNumber, 
        		contactNumber, 
        		email, 
        		password);
    }

    // Método estático para crear una instancia vacía por defecto
    public static final AdministratorDomain create() {
        return new AdministratorDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY, TextHelper.EMPTY);
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getLastName() {
        return this.lastName;
    }

    private void setLastName(final String lastName) {
        this.lastName = TextHelper.applyTrim(lastName);
    }

    public String getIdType() {
        return this.idType;
    }

    private void setIdType(final String idType) {
        this.idType = TextHelper.applyTrim(idType);
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    private void setIdNumber(final String idNumber) {
        this.idNumber = idNumber;
    }

    public String getContactNumber() {
        return this.contactNumber;
    }

    private void setContactNumber(final String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(final String email) {
        this.email = TextHelper.applyTrim(email);
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(final String password) {
        this.password = TextHelper.applyTrim(password);
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
