package co.edu.uco.victusresidencias.businesslogic.usecase.city.rules;

import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;

public interface CityNameDoesNotExistsForState {
	void execute(CityDomain data, DAOFactory factory);
}
