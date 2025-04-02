package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;


import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.entity.CountryEntity;


public class CountryEntityAdapter implements Adapter<CountryEntity,CountryDomain>{
	
	private static final Adapter<CountryEntity,CountryDomain> instance = new CountryEntityAdapter();
	
	private CountryEntityAdapter() {
		
	}
	public static Adapter<CountryEntity,CountryDomain> getCountryEntityAdapter(){
		return instance;
	}

	@Override
	public CountryEntity adaptSource(CountryDomain data) {
		var domainToAdapt = ObjectHelper.getDefault(data, CountryDomain.create(UUIDHelper.getDefault(), TextHelper.EMPTY));
		
		var entityToAdapt = new CountryEntity();
		entityToAdapt.setId(domainToAdapt.getId());
		entityToAdapt.setName(domainToAdapt.getName());
		
		return entityToAdapt;
	}

	@Override
	public CountryDomain adaptTarget(CountryEntity data) {
		var entityToAdapt = ObjectHelper.getDefault(data, new CountryEntity());
		return CountryDomain.create(
				entityToAdapt.getId(), 
				entityToAdapt.getName());
	}
	@Override
	public List<CountryDomain> adaptTarget(final List<CountryEntity> data) {
		
		var results = new ArrayList<CountryDomain>();
		
		for (CountryEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}
	

}
