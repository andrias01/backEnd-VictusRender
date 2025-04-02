package co.edu.uco.victusresidencias.businesslogic.facade.usagetimeunit.impl;

import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.UsageTimeUnitDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.usagetimeunit.FindUsageTimeUnitFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.impl.FindUsageTimeUnitImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.UsageTimeUnitDTO;

public final class FindUsageTimeUnitFacadeImpl implements FindUsageTimeUnitFacade{
	@Override
	public List<UsageTimeUnitDTO> execute(final UsageTimeUnitDTO data){
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			var findUsageTimeUnitUseCase = new FindUsageTimeUnitImpl(factory);//new FindCityImpl(factory);
			var usageTimeUnitDomain = UsageTimeUnitDTOAdapter.getUsageTimeUnitDTOAdapter().adaptSource(data);
			
			return UsageTimeUnitDTOAdapter.getUsageTimeUnitDTOAdapter().adaptTarget(findUsageTimeUnitUseCase.execute(usageTimeUnitDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de una unidad de tiempo...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de una unidad de tiempo. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
