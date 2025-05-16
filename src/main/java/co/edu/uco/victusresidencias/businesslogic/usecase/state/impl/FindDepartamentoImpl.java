package co.edu.uco.victusresidencias.businesslogic.usecase.state.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.StateEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.FindCountry;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.FindDepartamento;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.enums.Layer;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.domain.StateDomain;
import co.edu.uco.victusresidencias.entity.CountryEntity;
import co.edu.uco.victusresidencias.entity.StateEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class FindDepartamentoImpl implements FindDepartamento {

    private DAOFactory daoFactory;

    public FindDepartamentoImpl(final DAOFactory daoFactory) {
        setDaoFactory(daoFactory);
    }

    private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema inesperado al intentar buscar información del departamento.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase FindDepartamentoImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

    @Override
    public List<StateDomain> execute(final StateDomain data) {
        try {
            // Adaptar los datos de entrada al filtro de búsqueda
            var departamentoEntityFilter = StateEntityAdapter.getStateEntityAdapter().adaptSource(data);

            // Realizar la búsqueda en la base de datos usando el filtro
            List<StateEntity> departamentoEntities = daoFactory.getStateDAO().findByFilter(departamentoEntityFilter);

            // Convertir los resultados a CountryDomain para su retorno
            return departamentoEntities.stream()
                    .map(StateEntityAdapter.getStateEntityAdapter()::adaptTarget)
                    .collect(Collectors.toList());

        } catch (Exception exception) {
            var userMessage = "Se ha presentado un problema al intentar consultar la información de los departamentos.";
            var technicalMessage = "Ocurrió un error inesperado al ejecutar la búsqueda de departamentos.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage, exception, Layer.BUSINESSLOGIC);
        }
    }
}
