package co.edu.uco.victusresidencias.businesslogic.usecase.country.impl;

import java.util.UUID;

import co.edu.uco.victusresidencias.businesslogic.usecase.country.DeleteCountry;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;

public final class DeleteCountryImpl implements DeleteCountry {

    private DAOFactory daoFactory;

    public DeleteCountryImpl(final DAOFactory factory) {
    	setDaoFactory(factory);
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema al intentar eliminar la información del país. Por favor, intente de nuevo.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase DeleteCountryImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

	@Override
	public void execute(UUID id) {
		if (id == null) {
            var userMessage = "El ID del país es requerido para poder eliminar la información.";
            var technicalMessage = "El ID del país en la clase DeleteCountryImpl llegó nulo o vacío.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}

        daoFactory.getCountryDAO().delete(id);
		
	}
}
