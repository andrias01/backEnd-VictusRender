package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.domain.StateDomain;
import co.edu.uco.victusresidencias.entity.StateEntity;

import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;

public class StateEntityAdapter implements Adapter<StateEntity,StateDomain>{
	
	private static final Adapter<StateEntity,StateDomain> instance = new StateEntityAdapter();
	
	private StateEntityAdapter() {
		
	}
	public static Adapter<StateEntity,StateDomain> getStateEntityAdapter(){
		return instance;
	}

	@Override
	public StateEntity adaptSource(StateDomain data) {
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.STATE);

        // Convert StateDomain to StateEntity
        var entityToAdapt = new StateEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setCountry(CountryEntityAdapter.getCountryEntityAdapter().adaptSource(domainToAdapt.getCountry()));

        return entityToAdapt;
	}

	@Override
	public StateDomain adaptTarget(StateEntity data) {
        var entityToAdapt = ObjectHelper.getDefault(data, new StateEntity());
        return StateDomain.create(
            entityToAdapt.getId(),
            entityToAdapt.getName(),
            CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(entityToAdapt.getCountry()) // Adapt CountryEntity to CountryDomain
        );
	}
	@Override
	public List<StateDomain> adaptTarget(final List<StateEntity> data) {
		
		var results = new ArrayList<StateDomain>();
		
		for (StateEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}




}
