package co.edu.uco.victusresidencias.businesslogic.facade.usagetimeunit.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.UsageTimeUnitDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.usagetimeunit.RegisterNewUsageTimeUnitFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.impl.RegisterNewUsageTimeUnitImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.UsageTimeUnitDTO;

public final class RegisterNewUsageTimeUnitFacadeImpl implements RegisterNewUsageTimeUnitFacade{
	@Override
	public void execute(final UsageTimeUnitDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			factory.initTransaction(); 
			
			var registerNewUsageTimeUnitUSeCase = new RegisterNewUsageTimeUnitImpl(factory);
			var usageTimeUnitDomain = UsageTimeUnitDTOAdapter.getUsageTimeUnitDTOAdapter().adaptSource(data);
			
			registerNewUsageTimeUnitUSeCase.execute(usageTimeUnitDomain);
			
			factory.commitTransaction();
		} catch (final VictusResidenciasException exception) {
			factory.rollbackTransaction();
			throw exception;
		} catch (final Exception exception) {
			
			factory.rollbackTransaction();
			
			var userMEssage ="Se ha presentado un problema tratando de registerar la informacion de una nueva unida de tiempo de uso...";
			var technicalMEssage="Se ha presentado un problema inseperado registrando la informacion de una nueva unida de tiempo de uso. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception) ;  
		}finally {
			factory.closeConnection();
		}
		
	}
}
