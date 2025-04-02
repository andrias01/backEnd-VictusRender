package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class UsageTimeUnitDTO extends DomainDTO {
    
    private String name;
    private String abbreviation;
    private Boolean inBased;
    private String description;

    public UsageTimeUnitDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setAbbreviation(TextHelper.EMPTY);
        setInBased(false);
        setDescription(TextHelper.EMPTY);
    }

    public static UsageTimeUnitDTO create() {
        return new UsageTimeUnitDTO();
    }

    public String getName() {
        return name;
    }

    public UsageTimeUnitDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public UsageTimeUnitDTO setAbbreviation(String abbreviation) {
        this.abbreviation = TextHelper.applyTrim(abbreviation);
        return this;
    }

    public Boolean getInBased() {
        return inBased;
    }

    public UsageTimeUnitDTO setInBased(Boolean inBased) {
        this.inBased = inBased;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UsageTimeUnitDTO setDescription(String description) {
        this.description = TextHelper.applyTrim(description);
        return this;
    }

    public UsageTimeUnitDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}