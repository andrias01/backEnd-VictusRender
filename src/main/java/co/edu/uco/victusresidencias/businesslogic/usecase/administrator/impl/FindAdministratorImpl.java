package co.edu.uco.victusresidencias.businesslogic.usecase.administrator.impl;

import java.util.List;
import java.util.stream.Collectors;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.AdministratorEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.FindAdministrator;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.FindCountry;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.enums.Layer;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;
import co.edu.uco.victusresidencias.entity.CountryEntity;

public final class FindAdministratorImpl implements FindAdministrator {

    private DAOFactory daoFactory;

    public FindAdministratorImpl(final DAOFactory daoFactory) {
        setDaoFactory(daoFactory);
    }

    private void setDaoFactory(final DAOFactory daoFactory) {
        if (ObjectHelper.isNull(daoFactory)) {
            var userMessage = "Se ha presentado un problema inesperado al intentar buscar información del admin.";
            var technicalMessage = "El DAOFactory llegó nulo en la clase FindAdministratorImpl.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
        }
        this.daoFactory = daoFactory;
    }

    @Override
    public List<AdministratorDomain> execute(final AdministratorDomain data) {
        try {
            // Adaptar los datos de entrada al filtro de búsqueda
            var adminEntityFilter = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptSource(data);

            // Realizar la búsqueda en la base de datos usando el filtro
            List<AdministratorEntity> adminEntities = daoFactory.getAdministratorDAO().findByFilter(adminEntityFilter);

            // Convertir los resultados a CountryDomain para su retorno
            return adminEntities.stream()
                    .map(AdministratorEntityAdapter.getAdministratorEntityAdapter()::adaptTarget)
                    .collect(Collectors.toList());

        } catch (Exception exception) {
            var userMessage = "Se ha presentado un problema al intentar consultar la información de los admins.";
            var technicalMessage = "Ocurrió un error inesperado al ejecutar la búsqueda de admins.";
            throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage, exception, Layer.BUSINESSLOGIC);
        }
    }
}
