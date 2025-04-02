package co.edu.uco.victusresidencias.businesslogic.facade.commonzone.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CommonZoneDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.commonzone.RegisterNewCommonZoneFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.commonzone.impl.RegisterNewCommonZoneImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CommonZoneDTO;

public final class RegisterNewCommonZoneFacadeImpl implements RegisterNewCommonZoneFacade{
	@Override
	public void execute(final CommonZoneDTO data) {
		
		var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);
		
		try {
			factory.initTransaction(); 
			
			var registerNewCommonZoneUSeCase = new RegisterNewCommonZoneImpl(factory);
			var commonZoneDomain = CommonZoneDTOAdapter.getCommonZoneDTOAdapter().adaptSource(data);
			
			registerNewCommonZoneUSeCase.execute(commonZoneDomain);
			
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
