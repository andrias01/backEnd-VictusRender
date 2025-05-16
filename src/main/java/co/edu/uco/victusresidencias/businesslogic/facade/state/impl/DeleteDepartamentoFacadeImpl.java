package co.edu.uco.victusresidencias.businesslogic.facade.state.impl;

import co.edu.uco.victusresidencias.businesslogic.facade.country.DeleteCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.facade.state.DeleteDepartamentoFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.DeleteCountryImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.impl.DeleteDepartamentoImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;

import java.util.UUID;

public final class DeleteDepartamentoFacadeImpl implements DeleteDepartamentoFacade {

	@Override
    public void execute(final UUID id) {
        var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

        try {
            factory.initTransaction();

            var deleteDepartamentoUseCase = new DeleteDepartamentoImpl(factory);

            // Ejecuta el caso de uso directamente con el UUID recibido
            deleteDepartamentoUseCase.execute(id);

            factory.commitTransaction();
        } catch (final VictusResidenciasException exception) {
            factory.rollbackTransaction();
            throw exception;
        } catch (final Exception exception) {
            factory.rollbackTransaction();

            var userMessage = "Se ha presentado un problema tratando de eliminar la información del departamento.";
            var technicalMessage = "Se presentó un problema inesperado al eliminar la información del departamento. Por favor, revise el log de errores para más detalles.";
            
            throw BusinessLogicVictusResidenciasException.create(userMessage, technicalMessage, exception);
        } finally {
            factory.closeConnection();
        }
    }
}
