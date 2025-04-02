package co.edu.uco.victusresidencias.businesslogic.usecase.city.rules.impl;

import co.edu.uco.victusresidencias.businesslogic.usecase.city.rules.CityNameDoesNotExistsForState;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.entity.CityEntity;
import co.edu.uco.victusresidencias.entity.StateEntity;

public final class CityNameDoesNotExistsForStateImpl implements CityNameDoesNotExistsForState{
	@Override
	public void execute(final CityDomain data, final DAOFactory factory) {
		
		final var city = new CityEntity();
		city.setName(data.getName());
		
		final var state = new StateEntity();
		state.setId(data.getState().getId());
		
		city.setState(state);
		
		var results = factory.getCityDAO().findByFilter(city);
		
		if (!results.isEmpty()) {
				var userMessage = "Ya existe una ciudad llamada " + city.getName() + " para el departamento " + results.get(0).getState().getName();
				throw BusinessLogicVictusResidenciasException.crear(userMessage);
		}
		
	}
}
