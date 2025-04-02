package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.UsageTimeUnitDomain;
import co.edu.uco.victusresidencias.entity.UsageTimeUnitEntity;

public class UsageTimeUnitEntityAdapter implements Adapter<UsageTimeUnitEntity, UsageTimeUnitDomain> {
    
    private static final Adapter<UsageTimeUnitEntity, UsageTimeUnitDomain> instance = new UsageTimeUnitEntityAdapter();
    
    private UsageTimeUnitEntityAdapter() {
        // Constructor privado para Singleton
    }

    public static Adapter<UsageTimeUnitEntity, UsageTimeUnitDomain> getUsageTimeUnitEntityAdapter() {
        return instance;
    }

    @Override
    public UsageTimeUnitEntity adaptSource(UsageTimeUnitDomain data) {
        // Asegurarse de que data no sea nulo, usar un valor predeterminado si lo es
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.USAGE_TIME_UNIT);

        // Convertir UsageTimeUnitDomain a UsageTimeUnitEntity
        var entityToAdapt = new UsageTimeUnitEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setAbbreviation(domainToAdapt.getAbbreviation());
        entityToAdapt.setInBased(domainToAdapt.getInBased());
        entityToAdapt.setDescription(domainToAdapt.getDescription());

        return entityToAdapt;
    }

    @Override
    public UsageTimeUnitDomain adaptTarget(UsageTimeUnitEntity data) {
        // Asegurarse de que data no sea nulo, usar un valor predeterminado si lo es
        var entityToAdapt = ObjectHelper.getDefault(data, new UsageTimeUnitEntity());

        // Convertir UsageTimeUnitEntity a UsageTimeUnitDomain
        return UsageTimeUnitDomain.create(
            entityToAdapt.getId(),
            entityToAdapt.getName(),
            entityToAdapt.getAbbreviation(),
            entityToAdapt.getInBased(),
            entityToAdapt.getDescription());
    }

    @Override
    public List<UsageTimeUnitDomain> adaptTarget(final List<UsageTimeUnitEntity> data) {
        var results = new ArrayList<UsageTimeUnitDomain>();
        
        for (UsageTimeUnitEntity entity : data) {
            results.add(adaptTarget(entity));
        }
        
        return results;
    }
}