package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class CommonZoneDTO extends DomainDTO {

    private String name;
    private String description;
    private int capacityPeople;
    private int usageTime;
    private String usageTimeUnit;
    private String rules;
    private ResidentialComplexDTO residentialComplex;

    private CommonZoneDTO() {
        super(UUIDHelper.getDefaultAsString());
        setName(TextHelper.EMPTY);
        setDescription(TextHelper.EMPTY);
        setCapacityPeople(NumericHelper.CERO);
        setUsageTime(NumericHelper.CERO);
        setUsageTimeUnit(TextHelper.EMPTY);
        setRules(TextHelper.EMPTY);
        setResidentialComplex(ResidentialComplexDTO.create());
    }

    public static CommonZoneDTO create() {
        return new CommonZoneDTO();
    }

    public String getName() {
        return name;
    }

    public CommonZoneDTO setName(String name) {
        this.name = TextHelper.applyTrim(name);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommonZoneDTO setDescription(String description) {
        this.description = TextHelper.applyTrim(description);
        return this;
    }

    public int getCapacityPeople() {
        return capacityPeople;
    }

    public CommonZoneDTO setCapacityPeople(int capacityPeople) {
        this.capacityPeople = capacityPeople;
        return this;
    }

    public int getUsageTime() {
        return usageTime;
    }

    public CommonZoneDTO setUsageTime(int usageTime) {
        this.usageTime = usageTime;
        return this;
    }

    public String getUsageTimeUnit() {
        return usageTimeUnit;
    }

    public CommonZoneDTO setUsageTimeUnit(String usageTimeUnit) {
        this.usageTimeUnit = TextHelper.applyTrim(usageTimeUnit);
        return this;
    }

    public String getRules() {
        return rules;
    }

    public CommonZoneDTO setRules(String rules) {
        this.rules = TextHelper.applyTrim(rules);
        return this;
    }

    public ResidentialComplexDTO getResidentialComplex() {
        return residentialComplex;
    }

    public CommonZoneDTO setResidentialComplex(ResidentialComplexDTO residentialComplex) {
        this.residentialComplex = ObjectHelper.getDefault(residentialComplex, ResidentialComplexDTO.create());
        return this;
    }

    public CommonZoneDTO setId(final String id) {
        super.setIdentifier(id);
        return this;
    }

    @Override
    public String getId() {
        return super.getId();
    }
}
