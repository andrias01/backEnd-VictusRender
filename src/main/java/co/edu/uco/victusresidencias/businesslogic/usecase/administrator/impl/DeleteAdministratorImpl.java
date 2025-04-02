package co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl;

import java.util.UUID;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.DeleteAdministrator;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;

public final class DeleteAdministratorImpl implements DeleteAdministrator{

	private DAOFactory daoFactory;

    public DeleteAdministratorImpl(final DAOFactory factory) {
    	setDaoFactory(factory);
	}

	private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema al intentar eliminar la información del admin. Por favor, intente de nuevo.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase DeleteAdministratorImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

	@Override
	public void execute(UUID id) {
		
		if (id == null) {
            var userMessage = "El ID del admin es requerido para poder eliminar la información.";
            var technicalMessage = "El ID del admin en la clase DeleteAdminstratorImpl llegó nulo o vacío.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}

        daoFactory.getAdministratorDAO().delete(id);
		
	}

}
