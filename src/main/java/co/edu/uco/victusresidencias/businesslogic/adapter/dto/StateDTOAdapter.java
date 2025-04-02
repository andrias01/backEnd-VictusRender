package co.edu.uco.victusresidencias.businesslogic.adapter.dto;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;

import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.StateDomain;
import co.edu.uco.victusresidencias.dto.StateDTO;

public class StateDTOAdapter implements Adapter<StateDomain, StateDTO>{
	
	private static final Adapter<StateDomain, StateDTO> instance = new StateDTOAdapter();
	
	private StateDTOAdapter() {
		
	}
	
	public static Adapter<StateDomain, StateDTO> getStateDTOAdapter(){
		return instance;
	}

	@Override
	public StateDomain adaptSource(StateDTO data) {
		var dtoToAdapt = ObjectHelper.getDefault(data,StateDTO.create());
		return StateDomain.create(
				UUIDHelper.convertToUUID(dtoToAdapt.getId()), 
				dtoToAdapt.getName(),
				CountryDTOAdapter.getCountryDTOAdapter().adaptSource(dtoToAdapt.getCountry()));
	}

	@Override
	public StateDTO adaptTarget(StateDomain data) {
		// Usar un Domain por defecto si data es nulos
		var domainToAdapt = ObjectHelper.getDefault(data, createDefault.STATE);
		return StateDTO.create()
				.setId(UUIDHelper.getDefaultAsString())
				.setName(domainToAdapt.getName())
				.setCountry(CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(domainToAdapt.getCountry()));		
	}
	@Override
	public List<StateDTO> adaptTarget(final List<StateDomain> data) {
		
		var results = new ArrayList<StateDTO>();
		
		for (StateDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
	
}
