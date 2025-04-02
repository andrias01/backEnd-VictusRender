package co.edu.uco.victusresidencias.businesslogic.facade.commonzone.impl;

import java.util.List;


import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CommonZoneDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.commonzone.FindCommonZoneFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.commonzone.impl.FindCommonZoneImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CommonZoneDTO;

public final class FindCommonZoneFacadeImpl implements FindCommonZoneFacade{
	@Override
	public List<CommonZoneDTO> execute(final CommonZoneDTO data){
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			var findCommonZoneUseCase = new FindCommonZoneImpl(factory);//new FindCityImpl(factory);
			var commonZoneDomain = CommonZoneDTOAdapter.getCommonZoneDTOAdapter().adaptSource(data);
			
			return CommonZoneDTOAdapter.getCommonZoneDTOAdapter().adaptTarget(findCommonZoneUseCase.execute(commonZoneDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de la zona común...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de las zonas comunes. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
