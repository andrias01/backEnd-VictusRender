package co.edu.uco.victusresidencias.businesslogic.facade.state.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.dto.StateDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.RegisterNewCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.facade.state.RegisterNewDepartamentoFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.RegisterNewCountryImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.impl.RegisterNewDepartamentoImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.dto.StateDTO;

public final class RegisterNewDepartamentoFacadeImpl implements RegisterNewDepartamentoFacade{
	@Override
	public void execute(final StateDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

		try {
			factory.initTransaction(); 
			
			var registerNewDepartamentoUSeCase = new RegisterNewDepartamentoImpl(factory);
			var departamentoDomain = StateDTOAdapter.getStateDTOAdapter().adaptSource(data);

			registerNewDepartamentoUSeCase.execute(departamentoDomain);
			
			factory.commitTransaction();
		} catch (final VictusResidenciasException exception) {
			factory.rollbackTransaction();
			throw exception;
		} catch (final Exception exception) {
			
			factory.rollbackTransaction();
			
			var userMEssage ="Se ha presentado un problema tratando de registerar la informacion de la nuevo departamento...";
			var technicalMEssage="Se ha presentado un problema inseperado registrando la informacion del nuevo departamento. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception) ;  
		}finally {
			factory.closeConnection();
		}
		
	}
}
