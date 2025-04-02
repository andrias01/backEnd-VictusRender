package co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.impl;

import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.AdministratorDontExistWithTheSameEmail;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.AdministratorDontExistWithTheSameName;
import co.edu.uco.victusresidencias.businesslogic.usecase.administrator.rules.AdministratorNameConsistencyIsValid;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;

public final class AdministratorDontExistWithTheSameEmailImpl implements AdministratorDontExistWithTheSameEmail{
	private DAOFactory daoFactory;
	


	private boolean validateEmailExist(AdministratorDomain data,DAOFactory factory) {
		var adminEntityFilter = new AdministratorEntity();
	    adminEntityFilter.setEmail(data.getEmail());
	    System.out.println("VALIDANDO Si este dato como filtro existe: "+ data.getEmail());
	    // Buscar en la base de datos utilizando el filtro
	    return !factory.getAdministratorDAO().findByFilter(adminEntityFilter).isEmpty();
	}

	@Override
	public boolean execute(AdministratorDomain data, DAOFactory factory) {
		
		return validateEmailExist(data,factory);
	}

	

	



}
