package co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl;

import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.AdministratorDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.administrator.FindAdministratorFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl.FindAdministratorImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.AdministratorDTO;

public final class FindAdministratorFacadeImpl implements FindAdministratorFacade{
	@Override
	public List<AdministratorDTO> execute(final AdministratorDTO data){
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			var findAdminUseCase = new FindAdministratorImpl(factory);//new FindCityImpl(factory);
			var adminDomain = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptSource(data);
			
			return AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptTarget(findAdminUseCase.execute(adminDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de los admins...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de los admins. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
