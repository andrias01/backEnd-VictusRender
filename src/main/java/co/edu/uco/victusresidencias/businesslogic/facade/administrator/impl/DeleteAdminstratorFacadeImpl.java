package co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl;

import java.util.UUID;

import co.edu.uco.victusresidencias.businesslogic.facade.administrator.DeleteAdministratorFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl.DeleteAdministratorImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;

public final class DeleteAdminstratorFacadeImpl implements DeleteAdministratorFacade {

	@Override
    public void execute(final UUID id) {
        var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

        try {
            factory.initTransaction();

            var deleteAdminUseCase = new DeleteAdministratorImpl(factory);

            // Ejecuta el caso de uso directamente con el UUID recibido
            deleteAdminUseCase.execute(id);

            factory.commitTransaction();
        } catch (final VictusResidenciasException exception) {
            factory.rollbackTransaction();
            throw exception;
        } catch (final Exception exception) {
            factory.rollbackTransaction();

            var userMessage = "Se ha presentado un problema tratando de eliminar la información del admin.";
            var technicalMessage = "Se presentó un problema inesperado al eliminar la información del admin. Por favor, revise el log de errores para más detalles.";
            
            throw BusinessLogicVictusResidenciasException.create(userMessage, technicalMessage, exception);
        } finally {
            factory.closeConnection();
        }
    }
}
