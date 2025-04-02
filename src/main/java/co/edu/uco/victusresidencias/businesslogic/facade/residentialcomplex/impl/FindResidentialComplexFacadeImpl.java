package co.edu.uco.victusresidencias.businesslogic.facade.residentialcomplex.impl;

import java.util.List;


import co.edu.uco.victusresidencias.businesslogic.adapter.dto.ResidentialComplexDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.residentialcomplex.FindResidentialComplexFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.impl.FindResidentialComplexImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.ResidentialComplexDTO;

public final class FindResidentialComplexFacadeImpl implements FindResidentialComplexFacade{
	@Override
	public List<ResidentialComplexDTO> execute(final ResidentialComplexDTO data){
		var factory = DAOFactory.getFactory(DAOSource.SQLSERVER);
		
		try {
			var findResidentialComplexUseCase = new FindResidentialComplexImpl(factory);//new FindCityImpl(factory);
			var residentialComplexDomain = ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter().adaptSource(data);
			
			return ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter().adaptTarget(findResidentialComplexUseCase.execute(residentialComplexDomain));
			
		} catch (final VictusResidenciasException exception) {
			throw exception;
		}catch (final Exception exception) {
			var userMEssage ="Se ha presentado un problema tratando de consultar la informacion de las ciudades...";
			var technicalMEssage="Se ha presentado un problema inseperado consultando la informacion de las ciudades. por favor revise el log de errores para tener mas detalles...";
			
			throw BusinessLogicVictusResidenciasException.create(userMEssage, technicalMEssage, exception);
		} finally {
			factory.closeConnection();
		}
	
	}
}
