package co.edu.uco.victusresidencias.businesslogic.usecase.country.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.FindCountry;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.enums.Layer;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.entity.CountryEntity;

public final class FindCountryImpl implements FindCountry {

    private DAOFactory daoFactory;

    public FindCountryImpl(final DAOFactory daoFactory) {
        setDaoFactory(daoFactory);
    }

    private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema inesperado al intentar buscar información del país.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase FindCountryImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

    @Override
    public List<CountryDomain> execute(final CountryDomain data) {
        try {
            // Adaptar los datos de entrada al filtro de búsqueda
            var countryEntityFilter = CountryEntityAdapter.getCountryEntityAdapter().adaptSource(data);

            // Realizar la búsqueda en la base de datos usando el filtro
            List<CountryEntity> countryEntities = daoFactory.getCountryDAO().findByFilter(countryEntityFilter);

            // Convertir los resultados a CountryDomain para su retorno
            return countryEntities.stream()
                    .map(CountryEntityAdapter.getCountryEntityAdapter()::adaptTarget)
                    .collect(Collectors.toList());

        } catch (Exception exception) {
            var userMessage = "Se ha presentado un problema al intentar consultar la información de los países.";
            var technicalMessage = "Ocurrió un error inesperado al ejecutar la búsqueda de países.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage, exception, Layer.BUSINESSLOGIC);
        }
    }
}
