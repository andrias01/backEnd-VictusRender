package co.edu.uco.victusresidencias.businesslogic.usecase.state.impl;

import co.edu.uco.victusresidencias.businesslogic.usecase.state.DeleteDepartamento;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;

import java.util.UUID;

public final class DeleteDepartamentoImpl implements DeleteDepartamento {

    private DAOFactory daoFactory;

    public DeleteDepartamentoImpl(final DAOFactory factory) {
    	setDaoFactory(factory);
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema al intentar eliminar la información del departamento. Por favor, intente de nuevo.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase DeleteDepartamentoImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

	@Override
	public void execute(UUID id) {
		if (id == null) {
            var userMessage = "El ID del departamento es requerido para poder eliminar la información.";
            var technicalMessage = "El ID del departamento en la clase DeleteDepartamentoImpl llegó nulo o vacío.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}

        daoFactory.getStateDAO().delete(id);
		
	}
}
