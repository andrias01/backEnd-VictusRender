package co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.impl;

import java.util.UUID;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.UsageTimeUnitEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.RegisterNewUsageTimeUnit;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.rules.UsageTimeUnitNameConsistencyIsValid;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.rules.impl.UsageTimeUnitNameConsistencyIsValidImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.UsageTimeUnitDomain;

public final class RegisterNewUsageTimeUnitImpl implements RegisterNewUsageTimeUnit{

	private DAOFactory daoFactory;
	private UsageTimeUnitNameConsistencyIsValid usageTimeUnitNameConsistencyIsValid = new UsageTimeUnitNameConsistencyIsValidImpl();
			
	public RegisterNewUsageTimeUnitImpl(final DAOFactory daoFactory) {
		setDaoFactory(daoFactory);
	}
	
	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo el registro de la información de untiempo de uso deseado. Por favor intente de nuevo y si el problema persiste, llame a Luz Mery Rios Alzate...";
			var technicalMessage = "El DAO factory requerido para crear la clase registra el tiempo de uso llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void execute(final UsageTimeUnitDomain data) {
		usageTimeUnitNameConsistencyIsValid.execute(data.getName(),"Nombre");
		
		var usageTimeUnitDomainToMap = UsageTimeUnitDomain.create(); //organizar despues
		var usageTimeUnitEntity = UsageTimeUnitEntityAdapter.getUsageTimeUnitEntityAdapter().adaptSource(usageTimeUnitDomainToMap);
		daoFactory.getUsageTimeUnitDAO().create(usageTimeUnitEntity);		
	}
	
	private UUID generateId() {
		var id = UUIDHelper.generate();
		var usageTimeUnitEntity = daoFactory.getUsageTimeUnitDAO().fingByID(id);    
		
		if (UUIDHelper.isEqual(usageTimeUnitEntity.getId(), id)) {
			id = generateId();
		}
		
		return id;
		
	}

}
