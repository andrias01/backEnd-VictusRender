package co.edu.uco.victusresidencias.businesslogic.facade.state.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.dto.StateDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.UpdateCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.facade.state.UpdateDepartamentoFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.UpdateCountryImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.impl.UpdateDepartamentoImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.dto.StateDTO;

public final class UpdateDepartamentoFacadeImpl implements UpdateDepartamentoFacade {

    @Override
    public void execute(final StateDTO data) {
        var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

        try {
            factory.initTransaction();

            var updateDepartamentoUseCase = new UpdateDepartamentoImpl(factory);
            var departamentoDomain = StateDTOAdapter.getStateDTOAdapter().adaptSource(data);

            updateDepartamentoUseCase.execute(departamentoDomain);

            factory.commitTransaction();
        } catch (final VictusResidenciasException exception) {
            factory.rollbackTransaction();
            throw exception;
        } catch (final Exception exception) {
            factory.rollbackTransaction();

            var userMessage = "Se ha presentado un problema tratando de actualizar la información del departamento.";
            var technicalMessage = "Se presentó un problema inesperado al actualizar la información del departamento. Por favor, revise el log de errores para más detalles.";
            
            throw BusinessLogicVictusResidenciasException.create(userMessage, technicalMessage, exception);
        } finally {
            factory.closeConnection();
        }
    }
}
