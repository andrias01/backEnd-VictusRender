package co.edu.uco.victusresidencias.businesslogic.facade.country.impl;

import java.util.List;


import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CityDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.city.FindCityFacade;
import co.edu.uco.victusresidencias.businesslogic.facade.country.FindCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.impl.FindCityImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.FindCountryImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CityDTO;
import co.edu.uco.victusresidencias.dto.CountryDTO;

public final class FindCountryFacadeImpl implements FindCountryFacade{
	@Override
	public List<CountryDTO> execute(final CountryDTO data){
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			var findCountryUseCase = new FindCountryImpl(factory);//new FindCityImpl(factory);
			var countryDomain = CountryDTOAdapter.getCountryDTOAdapter().adaptSource(data);
			
			return CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(findCountryUseCase.execute(countryDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de las ciudades...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de las ciudades. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
