package co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.AdministratorEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.UpdateAdministrator;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.domain.CountryDomain;

public final class UpdateAdministratorImpl implements UpdateAdministrator{
	
private DAOFactory daoFactory;
	
	public UpdateAdministratorImpl(DAOFactory daoFactory){
		setDaoFactory(daoFactory);
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo la modificación de la información del admin deseado, Por favor intente de nuevo y si el problema persiste cominuquese con soporte...";
			var technicalMessage = "El dao factory requerido para crear la clase que actualiza el admin llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		
		this.daoFactory = daoFactory;
	}

	@Override
	public void execute(AdministratorDomain data) {
		var adminEntity = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptSource(data);
		daoFactory.getAdministratorDAO().update(adminEntity);
		
	}
}
