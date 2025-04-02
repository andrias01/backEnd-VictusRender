package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class ResidentialComplexDomain extends Domain {

    private String name;
    private String address; // Cambiado de direccion a address
    private CityDomain city;
    private String receptionContact; // Cambiado de contactoRecepcion a receptionContact
    private String description; // Cambiado de descripcion a description
    private AdministratorDomain administrator; // Cambiado de administrador a administrator

    // Private constructor
    private ResidentialComplexDomain(final UUID id, final String name, final String address, final CityDomain city, 
                                      final String receptionContact, final String description, final AdministratorDomain administrator) {
        super(id);
        setName(name);
        setAddress(address);
        setCity(city);
        setReceptionContact(receptionContact);
        setDescription(description);
        setAdministrator(administrator);
    }

    // Static method to create an instance with parameters
    public static ResidentialComplexDomain create(final UUID id, final String name, final String address, final CityDomain city, 
                                                   final String receptionContact, final String description, final AdministratorDomain administrator) {
        return new ResidentialComplexDomain(id, name, address, city, receptionContact, description, administrator);
    }

    // Static method to create an empty default instance
    public static ResidentialComplexDomain create() {
        return new ResidentialComplexDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, CityDomain.create(),
                                             TextHelper.EMPTY, TextHelper.EMPTY, AdministratorDomain.create());
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getAddress() { // Cambiado de getDireccion a getAddress
        return address;
    }

    private void setAddress(final String address) { // Cambiado de setDireccion a setAddress
        this.address = TextHelper.applyTrim(address);
    }

    public CityDomain getCity() {
        return city;
    }

    private void setCity(final CityDomain city) {
        this.city = (city != null) ? city : CityDomain.create(); // If city is null, set a default empty city
    }

    public String getReceptionContact() { // Cambiado de getContactoRecepcion a getReceptionContact
        return receptionContact;
    }

    private void setReceptionContact(final String receptionContact) { // Cambiado de setContactoRecepcion a setReceptionContact
        this.receptionContact = TextHelper.applyTrim(receptionContact);
    }

    public String getDescription() { // Cambiado de getDescripcion a getDescription
        return description;
    }

    private void setDescription(final String description) { // Cambiado de setDescripcion a setDescription
        this.description = TextHelper.applyTrim(description);
    }

    public AdministratorDomain getAdministrator() { // Cambiado de getAdministrador a getAdministrator
        return administrator;
    }

    private void setAdministrator(final AdministratorDomain administrator) { // Cambiado de setAdministrador a setAdministrator
        this.administrator = (administrator != null) ? administrator : AdministratorDomain.create(); // If the administrator is null, create a default one
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
