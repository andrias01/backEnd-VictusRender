package co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.impl;

import java.util.UUID;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.ResidentialComplexEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.RegisterNewResidentialComplex;
import co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.rules.ResidentialComplexNameConsistencyIsValid;
import co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.rules.impl.ResidentialcomplexNameConsistencyIsValidImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.ResidentialComplexDomain;

public final class RegisterNewResidentialComplexImpl implements RegisterNewResidentialComplex{

	private DAOFactory daoFactory;
	private ResidentialComplexNameConsistencyIsValid residentialComplexNameConsistencyIsValid = new ResidentialcomplexNameConsistencyIsValidImpl();
			
	public RegisterNewResidentialComplexImpl(final DAOFactory daoFactory) {
		setDaoFactory(daoFactory);
	}
	
	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo el registro de la información de la ciudad deseada. Por favor intente de nuevo y si el problema persiste, llame a Luz Mery Rios Alzate...";
			var technicalMessage = "El DAO factory requerido para crear la clase registra la ciudad llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void execute(final ResidentialComplexDomain data) {
		residentialComplexNameConsistencyIsValid.execute(data.getName(),"Nombre");
		
		var residentialComplexDomainToMap = ResidentialComplexDomain.create(); //organizar despues
		var residentialComplexEntity = ResidentialComplexEntityAdapter.getResidentialComplexEntityAdapter().adaptSource(residentialComplexDomainToMap);
		daoFactory.getResidentialComplexDAO().create(residentialComplexEntity);		
	}
	
	private UUID generateId() {
		var id = UUIDHelper.generate();
		var cityEntity = daoFactory.getCityDAO().fingByID(id);    
		
		if (UUIDHelper.isEqual(cityEntity.getId(), id)) {
			id = generateId();
		}
		
		return id;
		
	}

}
