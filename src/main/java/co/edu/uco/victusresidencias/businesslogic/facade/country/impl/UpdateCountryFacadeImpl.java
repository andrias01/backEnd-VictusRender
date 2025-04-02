package co.edu.uco.victusresidencias.businesslogic.facade.country.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.UpdateCountryFacade;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.impl.UpdateCountryImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.data.dao.enums.DAOSource;
import co.edu.uco.victusresidencias.dto.CountryDTO;

public final class UpdateCountryFacadeImpl implements UpdateCountryFacade {

    @Override
    public void execute(final CountryDTO data) {
        var factory = DAOFactory.getFactory(DAOSource.POSTGRESQL);

        try {
            factory.initTransaction();

            var updateCountryUseCase = new UpdateCountryImpl(factory);
            var countryDomain = CountryDTOAdapter.getCountryDTOAdapter().adaptSource(data);

            updateCountryUseCase.execute(countryDomain);

            factory.commitTransaction();
        } catch (final VictusResidenciasException exception) {
            factory.rollbackTransaction();
            throw exception;
        } catch (final Exception exception) {
            factory.rollbackTransaction();

            var userMessage = "Se ha presentado un problema tratando de actualizar la información del país.";
            var technicalMessage = "Se presentó un problema inesperado al actualizar la información del país. Por favor, revise el log de errores para más detalles.";
            
            throw BusinessLogicVictusResidenciasException.create(userMessage, technicalMessage, exception);
        } finally {
            factory.closeConnection();
        }
    }
}
