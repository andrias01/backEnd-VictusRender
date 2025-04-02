package co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.AdministratorDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.administrator.UpdateAdministratorFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl.UpdateAdministratorImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.UpdateCountryImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.AdministratorDTO;

public final class UpdateAdministratorFacadeImpl implements UpdateAdministratorFacade {

    @Override
    public void execute(final AdministratorDTO data) {
        var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

        try {
            factory.initTransaction();

            var updateAdminUseCase = new UpdateAdministratorImpl(factory);
            var adminDomain = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptSource(data);

            updateAdminUseCase.execute(adminDomain);

            factory.commitTransaction();
        } catch (final VictusResidenciasException exception) {
            factory.rollbackTransaction();
            throw exception;
        } catch (final Exception exception) {
            factory.rollbackTransaction();

            var userMessage = "Se ha presentado un problema tratando de actualizar la información del admin.";
            var technicalMessage = "Se presentó un problema inesperado al actualizar la información del admin. Por favor, revise el log de errores para más detalles.";
            
            throw BusinessLogicVictusResidenciasException.create(userMessage, technicalMessage, exception);
        } finally {
            factory.closeConnection();
        }
    }

}
