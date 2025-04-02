package co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.impl;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.UsageTimeUnitEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.usagetimeunit.UpdateUsageTimeUnit;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.UsageTimeUnitDomain;

public final class UpdateUsageTimeUnitImpl implements UpdateUsageTimeUnit{
	
	private DAOFactory daoFactory;
	
	public UpdateUsageTimeUnitImpl(DAOFactory daoFactory){
		setDaoFactory(daoFactory);
	}
	
	@Override
	public void execute(final UsageTimeUnitDomain data) {
		// Validate policies
		
		var usageTimeUnitEntity = UsageTimeUnitEntityAdapter.getUsageTimeUnitEntityAdapter().adaptSource(data);
		daoFactory.getUsageTimeUnitDAO().update(usageTimeUnitEntity);
		
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo la modificación de la información del unidad de tiempo de uso deseada, Por favor intente de nuevo y si el problema persiste comuniquese con soporte...";
			var technicalMessage = "El dao factory requerido para crear la clase que actualiza la unidad de tiempo de uso llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		
		this.daoFactory = daoFactory;
	}

}
