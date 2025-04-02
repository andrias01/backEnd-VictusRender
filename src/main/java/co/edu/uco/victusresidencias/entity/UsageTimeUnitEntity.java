package co.edu.uco.victusresidencias.entity;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class UsageTimeUnitEntity extends DomainEntity {
    
    private String name;
    private String abbreviation;
    private Boolean inBased; 
    private String description;

    public UsageTimeUnitEntity() {
        super(UUIDHelper.getDefault());
        setName(TextHelper.EMPTY);
        setAbbreviation(TextHelper.EMPTY);
        setInBased(false);
        setDescription(TextHelper.EMPTY);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = TextHelper.applyTrim(abbreviation);
    }

    public Boolean getInBased() {
        return inBased;
    }

    public void setInBased(Boolean inBased) {
        this.inBased = inBased;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = TextHelper.applyTrim(description);
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(final UUID id) {
        super.setId(id);
    }
}