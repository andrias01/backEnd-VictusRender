package co.edu.uco.victusresidencias.businesslogic.usecase.city.impl;

import java.util.List;
import java.util.stream.Collectors;


import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CityEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.FindCity;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.enums.Layer;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.entity.CityEntity;

public final class FindCityImpl implements FindCity{

	private DAOFactory daoFactory;
	public FindCityImpl(final DAOFactory daoFactory){ setDaoFactory(daoFactory);}

	private void setDaoFactory(final DAOFactory daoFactory){
		if (ObjectHelper.isNull(daoFactory)){
			var userMessage = "Se ha presentado un problema inesperado al intentar buscar información de las cuidades.";
			var technicalMessage = "El DAOFactory llegó nulo en la clase FindCityImpl.";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}

	@Override
	public List<CityDomain> execute(final CityDomain data) {
		try {
			var ciudadEntidadFiltro = CityEntityAdapter.getCityEntityAdapter().adaptSource(data);
			List<CityEntity> ciudadesEntidad = daoFactory.getCityDAO().findByFilter(ciudadEntidadFiltro);
			return ciudadesEntidad.stream()
					.map(CityEntityAdapter.getCityEntityAdapter()::adaptTarget)
					.collect(Collectors.toList());
		} catch (Exception exception){
			var userMessage = "Se ha presentado un problema al intentar consultar la información de las ciudades.";
			var technicalMessage = "Ocurrió un error inesperado al ejecutar la búsqueda de las cuidades.";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage, exception, Layer.BUSINESSLOGIC);
		}

	}
}
