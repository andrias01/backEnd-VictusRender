package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.CommonZoneDomain;
import co.edu.uco.victusresidencias.entity.CommonZoneEntity;

public class CommonZoneEntityAdapter implements Adapter<CommonZoneEntity,CommonZoneDomain>{
	private static final Adapter<CommonZoneEntity,CommonZoneDomain> instance = new CommonZoneEntityAdapter();
	
	private CommonZoneEntityAdapter() {
		
	}
	
	public static Adapter<CommonZoneEntity,CommonZoneDomain> getCommonZoneEntityAdapter(){
		return instance;
	}

	@Override
	public CommonZoneEntity adaptSource(CommonZoneDomain data) {
		// Ensure data is not null, use a default value if it is
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.COMMON_ZONE);

        // Convert StateDomain to StateEntity
        var entityToAdapt = new CommonZoneEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setDescription(domainToAdapt.getDescription());
        entityToAdapt.setPeopleCapacity(domainToAdapt.getPeopleLimit());
        entityToAdapt.setUsingTime(domainToAdapt.getUseTime());
        entityToAdapt.setUsingTimeUnit(domainToAdapt.getUseTimeUnit());
        entityToAdapt.setRule(domainToAdapt.getRules());
        entityToAdapt.setResidentialComplex(ResidentialComplexEntityAdapter.getResidentialComplexEntityAdapter().adaptSource(domainToAdapt.getResidentialComplex()));
        return entityToAdapt;
	}

	@Override
	public CommonZoneDomain adaptTarget(CommonZoneEntity data) {
		// Ensure data is not null, use a default value if it is
        var entityToAdapt = ObjectHelper.getDefault(data, new CommonZoneEntity());

        // Convert StateEntity to StateDomain
        return CommonZoneDomain.create(
        		entityToAdapt.getId(),
        		entityToAdapt.getName(),
        		entityToAdapt.getDescription(),
        		entityToAdapt.getPeopleCapacity(),
        		entityToAdapt.getUsingTime(),
        		entityToAdapt.getUsingTimeUnit(),
        		entityToAdapt.getRule(),
        		ResidentialComplexEntityAdapter.getResidentialComplexEntityAdapter().adaptTarget(entityToAdapt.getResidentialComplex())
        		
        		// Adapt CountryEntity to CountryDomain (id, name, description, peopleLimit, useTime, useTimeUnit, rules, residentialComplex)
        );
	}

	@Override
	public List<CommonZoneDomain> adaptTarget(final List<CommonZoneEntity> data) {
		
		var results = new ArrayList<CommonZoneDomain>();
		
		for (CommonZoneEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}
}
