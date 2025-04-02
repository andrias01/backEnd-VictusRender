package co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.impl;

import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.AdministratorDontExistWithTheSameName;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.AdministratorNameConsistencyIsValid;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;

public final class AdministratorDontExistWithTheSameNameImpl implements AdministratorDontExistWithTheSameName{
	private DAOFactory daoFactory;
	


	private boolean validateNameExist(AdministratorDomain data,DAOFactory factory) {
		var adminEntityFilter = new AdministratorEntity();
	    adminEntityFilter.setName(data.getName());
	    System.out.println(adminEntityFilter);
	    // Buscar en la base de datos utilizando el filtro
	    return !factory.getAdministratorDAO().findByFilter(adminEntityFilter).isEmpty();
	}

	@Override
	public boolean execute(AdministratorDomain data, DAOFactory factory) {
		
		return validateNameExist(data,factory);
	}

	

	



}
