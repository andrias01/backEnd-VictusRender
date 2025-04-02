package co.edu.uco.victusresidencias.businesslogic.facade.country.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.RegisterNewCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.RegisterNewCountryImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CountryDTO;

public final class RegisterNewCountryFacadeImpl implements RegisterNewCountryFacade{
	@Override
	public void execute(final CountryDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		
		
		try {
			factory.initTransaction(); 
			
			var registerNewCountryUSeCase = new RegisterNewCountryImpl(factory);
			var countryDomain = CountryDTOAdapter.getCountryDTOAdapter().adaptSource(data);
			
			registerNewCountryUSeCase.execute(countryDomain);
			
			factory.commitTransaction();
		} catch (final VictusResidenciasException exception) {
			factory.rollbackTransaction();
			throw exception;
		} catch (final Exception exception) {
			
			factory.rollbackTransaction();
			
			var userMEssage ="Se ha presentado un problema tratando de registerar la informacion de la nuevo pais...";
			var technicalMEssage="Se ha presentado un problema inseperado registrando la informacion del nuevo pais. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception) ;  
		}finally {
			factory.closeConnection();
		}
		
	}
}
