package co.edu.uco.victusresidencias.businesslogic.adapter.dto;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;



import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.dto.CountryDTO;



public class CountryDTOAdapter implements Adapter<CountryDomain, CountryDTO> {

	private static final Adapter<CountryDomain, CountryDTO> instance = new CountryDTOAdapter();
	
	private CountryDTOAdapter() {
		
	}
	public static Adapter<CountryDomain, CountryDTO> getCountryDTOAdapter(){
		return instance;
	}

	
	@Override
	public CountryDomain adaptSource(final CountryDTO data) {
		var dtoToAdapt = ObjectHelper.getDefault(data, CountryDTO.create());
		return CountryDomain.create(
				UUIDHelper.convertToUUID(dtoToAdapt.getId()), 
				data.getName());
	}

	@Override
	public CountryDTO adaptTarget(final CountryDomain data) {
	    var domainToAdapt = ObjectHelper.getDefault(data, createDefault.COUNTRY);
	    return CountryDTO.create()
	            .setId(UUIDHelper.convertToString(domainToAdapt.getId())) /*pendiente*/ // Convertimos el UUID a String
	            .setName(domainToAdapt.getName());
	}
	@Override
	public List<CountryDTO> adaptTarget(final List<CountryDomain> data) {
		
		var results = new ArrayList<CountryDTO>();
		
		for (CountryDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
}
