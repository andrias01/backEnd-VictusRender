package co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.impl;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.ResidentialComplexEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.residentialcomplex.UpdateResidentialComplex;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.ResidentialComplexDomain;

public final class UpdateResidentialComplexImpl implements UpdateResidentialComplex{
	
	private DAOFactory daoFactory;
	
	public UpdateResidentialComplexImpl(DAOFactory daoFactory){
		setDaoFactory(daoFactory);
	}
	
	@Override
	public void execute(final ResidentialComplexDomain data) {
		// Validate policies
		
		var residentialComplexEntity = ResidentialComplexEntityAdapter.getResidentialComplexEntityAdapter().adaptSource(data);
		daoFactory.getResidentialComplexDAO().update(residentialComplexEntity);
		
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo la modificación de la información del conjunto residencial deseado, Por favor intente de nuevo y si el problema persiste comuniquese con soporte...";
			var technicalMessage = "El dao factory requerido para crear la clase que actualiza el conjunto residencial llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		
		this.daoFactory = daoFactory;
	}

}
