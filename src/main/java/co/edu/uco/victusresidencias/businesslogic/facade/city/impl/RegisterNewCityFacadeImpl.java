package co.edu.uco.victusresidencias.businesslogic.facade.city.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CityDTOAdapter;

import co.edu.uco.victusresidencias.businesslogic.facade.city.RegisterNewCityFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.impl.RegisterNewCityImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CityDTO;

public final class RegisterNewCityFacadeImpl implements RegisterNewCityFacade{
	@Override
	public void execute(final CityDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			factory.initTransaction(); 
			
			var registerNewCityUSeCase = new RegisterNewCityImpl(factory);
			var cityDomain = CityDTOAdapter.getCityDTOAdapter().adaptSource(data);
			
			registerNewCityUSeCase.execute(cityDomain);
			
			factory.commitTransaction();
		} catch (final VictusResidenciasException exception) {
			factory.rollbackTransaction();
			throw exception;
		} catch (final Exception exception) {
			
			factory.rollbackTransaction();
			
			var userMEssage ="Se ha presentado un problema tratando de registerar la informacion de la nueva ciudad...";
			var technicalMEssage="Se ha presentado un problema inseperado registrando la informacion de la nueva ciudad. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception) ;  
		}finally {
			factory.closeConnection();
		}
		
	}
}
