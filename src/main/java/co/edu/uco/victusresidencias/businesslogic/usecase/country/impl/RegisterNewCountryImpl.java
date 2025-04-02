package co.edu.uco.victusresidencias.businesslogic.usecase.country.impl;

import java.util.UUID;


import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.RegisterNewCountry;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.rules.CountryNameConsistencyIsValid;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.rules.impl.CountryNameConsistencyIsValidImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.entity.CountryEntity;

public final class RegisterNewCountryImpl implements RegisterNewCountry{

	private DAOFactory daoFactory;
	private CountryNameConsistencyIsValid countryNameConsistencyIsValid = new CountryNameConsistencyIsValidImpl();
			
	public RegisterNewCountryImpl(final DAOFactory daoFactory) {
		setDaoFactory(daoFactory);
	}
	
	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo el registro de la información del pais deseada. Por favor intente de nuevo y si el problema persiste, llame a Luz Mery Rios Alzate...";
			var technicalMessage = "El DAO factory requerido para crear la clase registra la ciudad llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void execute(final CountryDomain data) {
	    countryNameConsistencyIsValid.execute(data.getName(),"Nombre");

	 // Crear un filtro de entidad para buscar si existe un país con el mismo nombre
	    var countryEntityFilter = new CountryEntity();
	    countryEntityFilter.setName(data.getName());

	    // Buscar en la base de datos utilizando el filtro
	    boolean countryExists = !daoFactory.getCountryDAO().findByFilter(countryEntityFilter).isEmpty();

	    // Lanzar excepción si se encuentra un país con el mismo nombre
	    if (countryExists) {
	        String userMessage = "El país ya existe";
	        String technicalMessage = "El país con el nombre '" + data.getName() + "' ya existe en la base de datos.";
	        
	        throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
	    }


	    // Si no existe, procede a crear el país
	    var countryDomainToMap = CountryDomain.create(generateId(), data.getName());
	    var countryEntity = CountryEntityAdapter.getCountryEntityAdapter().adaptSource(countryDomainToMap);
	    daoFactory.getCountryDAO().create(countryEntity);
	}
	
	private UUID generateId() {
	    UUID id;
	    do {
	        id = UUIDHelper.generate();
	    } while (daoFactory.getCountryDAO().fingByID(id) != null);
	    
	    return id;
	}

}
