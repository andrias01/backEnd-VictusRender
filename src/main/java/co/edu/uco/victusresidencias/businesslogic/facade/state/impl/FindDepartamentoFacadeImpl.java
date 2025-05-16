package co.edu.uco.victusresidencias.businesslogic.facade.state.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.dto.StateDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.FindCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.facade.state.FindDepartamentoFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.FindCountryImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.impl.FindDepartamentoImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.dto.StateDTO;

import java.util.List;

public final class FindDepartamentoFacadeImpl implements FindDepartamentoFacade {
	@Override
	public List<StateDTO> execute(final StateDTO data){
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			var findDepartamentoUseCase = new FindDepartamentoImpl(factory);
			var departamentoDomain = StateDTOAdapter.getStateDTOAdapter().adaptSource(data);
			
			return StateDTOAdapter.getStateDTOAdapter().adaptTarget(findDepartamentoUseCase.execute(departamentoDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de los departamentos...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de los departamentos. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
