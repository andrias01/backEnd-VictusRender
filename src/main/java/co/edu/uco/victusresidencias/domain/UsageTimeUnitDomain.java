package co.edu.uco.victusresidencias.domain;

import java.util.UUID;


import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class UsageTimeUnitDomain extends Domain {

    private String name;
    private String abbreviation;
    private Boolean inBased;
    private String description;

    // Constructor privado para forzar el uso del método de creación estático
    private UsageTimeUnitDomain(final UUID id, final String name, final String abbreviation, 
                                final Boolean inBased, final String description) {
        super(id);
        setName(name);
        setAbbreviation(abbreviation);
        setInBased(inBased);
        setDescription(description);
    }

    // Método estático para crear una instancia
    public static final UsageTimeUnitDomain create(final UUID id, final String name, 
                                                   final String abbreviation, final Boolean inBased, 
                                                   final String description) {
        return new UsageTimeUnitDomain(id, name, abbreviation, inBased, description);
    }

    // Método estático para crear una instancia vacía por defecto
    public static final UsageTimeUnitDomain create() {
        return new UsageTimeUnitDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, false, TextHelper.EMPTY);
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private void setAbbreviation(final String abbreviation) {
        this.abbreviation = TextHelper.applyTrim(abbreviation);
    }

    public Boolean getInBased() {
        return inBased;
    }

    private void setInBased(final Boolean inBased) {
        this.inBased = inBased;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(final String description) {
        this.description = TextHelper.applyTrim(description);
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}