package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.entity.CityEntity;

public final class CityEntityAdapter implements Adapter<CityEntity,CityDomain>{
	
	private static final Adapter<CityEntity,CityDomain> instance = new CityEntityAdapter();
	
	private CityEntityAdapter() {
		
	}
	public static Adapter<CityEntity,CityDomain> getCityEntityAdapter(){
		return instance;
	}
	@Override
	public CityEntity adaptSource(CityDomain data) {
		// Ensure data is not null, use a default value if it is
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.CITY);

        // Convert CityDomain to CityEntity
        var entityToAdapt = new CityEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setState(StateEntityAdapter.getStateEntityAdapter().adaptSource(domainToAdapt.getState()));

        return entityToAdapt;
	}
	@Override
	public CityDomain adaptTarget(CityEntity data) {
		// Ensure data is not null, use a default value if it is
        var entityToAdapt = ObjectHelper.getDefault(data, new CityEntity());

        // Convert CityEntity to CityDomain
        return CityDomain.create(
            entityToAdapt.getId(),
            entityToAdapt.getName(),
            StateEntityAdapter.getStateEntityAdapter().adaptTarget(entityToAdapt.getState()) // Adapt StateEntity to StateDomain
        );
	}
	@Override
	public List<CityDomain> adaptTarget(final List<CityEntity> data) {
		
		var results = new ArrayList<CityDomain>();
		
		for (CityEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}

}
