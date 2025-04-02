package co.edu.uco.victusresidencias.businesslogic.adapter.dto;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.List;
import java.util.ArrayList;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.dto.CityDTO;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.domain.StateDomain;



public class CityDTOAdapter implements Adapter<CityDomain, CityDTO>{
	
	private static final Adapter<CityDomain, CityDTO> instance = new CityDTOAdapter();
	
	private CityDTOAdapter() {
		
	}
	
	public static Adapter<CityDomain, CityDTO> getCityDTOAdapter(){
		return instance;
	}

	@Override
	public CityDomain adaptSource(CityDTO data) {
		// Si el DTO es nulo, usamos un DTO por defecto
        var dtoToAdapt = ObjectHelper.getDefault(data, CityDTO.create());
        
        // Convertimos el DTO a Domain
        return CityDomain.create(
            UUIDHelper.convertToUUID(dtoToAdapt.getId()), 
            dtoToAdapt.getName(), 
            StateDTOAdapter.getStateDTOAdapter().adaptSource(dtoToAdapt.getState()) // Adaptamos el estado
        );
	}

	@Override
	public CityDTO adaptTarget(CityDomain data) {
		// Si el Domain es nulo, usamos un Domain por defecto
        var domainToAdapt = ObjectHelper.getDefault(data, CityDomain.create(UUIDHelper.getDefault(), TextHelper.EMPTY, StateDomain.create(UUIDHelper.getDefault(),TextHelper.EMPTY, CountryDomain.create(UUIDHelper.getDefault(),TextHelper.EMPTY))));

        // Convertimos el Domain a DTO
        return CityDTO.create()
            .setId(UUIDHelper.getDefaultAsString()) // Convertimos el UUID a String
            .setName(domainToAdapt.getName())
            .setState(StateDTOAdapter.getStateDTOAdapter().adaptTarget(domainToAdapt.getState())); // Adaptamos el estado
	}
	@Override
	public List<CityDTO> adaptTarget(final List<CityDomain> data) {
		
		var results = new ArrayList<CityDTO>();
		
		for (CityDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}




}
