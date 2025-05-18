package co.edu.uco.victusresidencias.businesslogic.usecase.city.impl;

import java.util.UUID;


import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CityEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.RegisterNewCity;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.rules.CityNameConsistencyIsValid;
import co.edu.uco.victusresidencias.businesslogic.usecase.city.rules.impl.CityNameConsistencyIsValidImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.entity.CityEntity;

public final class RegisterNewCityImpl implements RegisterNewCity{

	private DAOFactory daoFactory;
	//private CityNameDoesNotExistsForState cityNameDoesNotExistsForState = (CityNameDoesNotExistsForState) new CityNameDoesNotExistsForStateImpl();
	//private StateExists stateExists = new StateExistsImpl();
	private CityNameConsistencyIsValid cityNameConsistencyIsValid = new CityNameConsistencyIsValidImpl();
			
	public RegisterNewCityImpl(final DAOFactory daoFactory) {
		setDaoFactory(daoFactory);
	}
	
	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo el registro de la información de la ciudad deseada. Por favor intente de nuevo y si el problema persiste, llame a Luz Mery Rios Alzate...";
			var technicalMessage = "El DAO factory requerido para crear la clase registra la ciudad llegó nula...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void execute(final CityDomain data) {
		cityNameConsistencyIsValid.execute(data.getName(),"Nombre");

		var ciudadEntidadFiltro = new CityEntity();
		ciudadEntidadFiltro.setName(data.getName());

		boolean ciudadExiste = !daoFactory.getCityDAO().findByFilter(ciudadEntidadFiltro).isEmpty();
		if (ciudadExiste){
			String mensajeUsuario = "La Ciudad ya existe";
			String mensajeTecnico = "La Ciudad con el nombre "+data.getName()+" ya existe en la base de datos.";
			throw BusinessLogicVictusResidenciasException.create(mensajeUsuario,mensajeTecnico);
		}

		//si no existe, procede a crear la ciudad
		var cityDomainToMap = CityDomain.create(generateId(), data.getName(), data.getState());
		var cityEntity = CityEntityAdapter.getCityEntityAdapter().adaptSource(cityDomainToMap);
		System.out.println("Id ciudada registerNewCityImpl " +cityEntity.getId());
		daoFactory.getCityDAO().create(cityEntity);		
	}
	
	private UUID generateId() {
		var id = UUIDHelper.generate();
		var cityEntity = daoFactory.getCityDAO().fingByID(id);

		if (UUIDHelper.isEqual(cityEntity.getId(), id)) {
			id = generateId();
		}
//		UUID id;
//		do {
//			id= UUIDHelper.generate();
//		}while (daoFactory.getCityDAO().fingByID(id) != null);
		return id;
		
	}

}
