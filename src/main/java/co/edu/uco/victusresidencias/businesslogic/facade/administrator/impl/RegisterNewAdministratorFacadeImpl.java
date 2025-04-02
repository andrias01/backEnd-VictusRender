package co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.AdministratorDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.administrator.RegisterNewAdministratorFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl.RegisterNewAdministratorImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.AdministratorDTO;

public final class RegisterNewAdministratorFacadeImpl implements RegisterNewAdministratorFacade{
	@Override
	public void execute(final AdministratorDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		
		
		try {
			factory.initTransaction(); 
			var registerNewAdminUSeCase = new RegisterNewAdministratorImpl(factory);
			var adminDomain = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptSource(data);
			
			registerNewAdminUSeCase.execute(adminDomain);
			
			factory.commitTransaction();
		} catch (final VictusResidenciasException exception) {
			factory.rollbackTransaction();
			throw exception;
		} catch (final Exception exception) {
			
			factory.rollbackTransaction();
			
			var userMEssage ="Se ha presentado un problema tratando de registerar la informacion de la nuevo admin...";
			var technicalMEssage="Se ha presentado un problema inseperado registrando la informacion del nuevo admin. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception) ;  
		}finally {
			factory.closeConnection();
		}
		
	}
}
