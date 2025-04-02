package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.NumericHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class CommonZoneDomain extends Domain {
    
    private String name;
    private String description;
    private int peopleLimit;
    private int useTime;
    private String useTimeUnit;
    private String rules;
    private ResidentialComplexDomain residentialComplex;

    // Constructor privado
    private CommonZoneDomain(final UUID id, final String name, final String description, final int peopleLimit, 
                            final int useTime, final String useTimeUnit, final String rules, 
                            final ResidentialComplexDomain residentialComplex) {
        super(id);
        setName(name);
        setDescription(description);
        setPeopleLimit(peopleLimit);
        setUseTime(useTime);
        setUseTimeUnit(useTimeUnit);
        setRules(rules);
        setResidentialComplex(residentialComplex);
    }

    // Método estático para crear una instancia con parámetros
    public static CommonZoneDomain create(final UUID id, final String name, final String description, final int peopleLimit, 
                                         final int useTime, final String useTimeUnit, final String rules, 
                                         final ResidentialComplexDomain residentialComplex) {
        return new CommonZoneDomain(id, name, description, peopleLimit, useTime, useTimeUnit, rules, residentialComplex);
    }

    // Método estático para crear una instancia vacía por defecto
    public static CommonZoneDomain create() {
        return new CommonZoneDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, NumericHelper.CERO, 
                                   NumericHelper.CERO, TextHelper.EMPTY, TextHelper.EMPTY, ResidentialComplexDomain.create());
    }

    // Getters y Setters

    public String getName() {
        return name;
    }

    private void setName(final String name) {
        this.name = TextHelper.applyTrim(name);
    }

    public String getDescription() {
        return this.description;
    }

    private void setDescription(final String description) {
        this.description = TextHelper.applyTrim(description);
    }

    public int getPeopleLimit() {
        return this.peopleLimit;
    }

    private void setPeopleLimit(final int peopleLimit) {
        this.peopleLimit= (peopleLimit > 0) ? peopleLimit : NumericHelper.CERO;
    }

    public int getUseTime() {
        return this.useTime;
    }

    private void setUseTime(final int UseTime) {
        this.useTime = (UseTime > 0) ? useTime : NumericHelper.CERO;
    }

    public String getUseTimeUnit() {
        return this.useTimeUnit;
    }

    private void setUseTimeUnit(final String useTimeUnit) {
        this.useTimeUnit = TextHelper.applyTrim(useTimeUnit);
    }

    public String getRules() {
        return this.rules;
    }

    private void setRules(final String rules) {
        this.rules = TextHelper.applyTrim(rules);
    }

    public ResidentialComplexDomain getResidentialComplex() {
        return this.residentialComplex;
    }

    private void setResidentialComplex(final ResidentialComplexDomain residentialComplex) {
        this.residentialComplex = (residentialComplex != null) ? residentialComplex : ResidentialComplexDomain.create();
    }

    @Override
    public UUID getId() {
        return super.getId();
    }
}
