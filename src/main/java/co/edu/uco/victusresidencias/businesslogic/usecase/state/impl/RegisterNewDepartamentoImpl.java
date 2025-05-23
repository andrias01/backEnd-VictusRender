package co.edu.uco.victusresidencias.businesslogic.usecase.state.impl;

import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.StateEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.RegisterNewCountry;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.rules.CountryNameConsistencyIsValid;
import co.edu.uco.victusresidencias.businesslogic.usecase.country.rules.impl.CountryNameConsistencyIsValidImpl;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.RegisterNewDepartamento;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.rules.DepartamentoExisteConsistenciaValida;
import co.edu.uco.victusresidencias.businesslogic.usecase.state.rules.impl.DepartamentoExisteConsistenciaValidaImpl;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.DAOFactory;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.domain.StateDomain;
import co.edu.uco.victusresidencias.entity.CountryEntity;
import co.edu.uco.victusresidencias.entity.StateEntity;

import java.util.UUID;

public final class RegisterNewDepartamentoImpl implements RegisterNewDepartamento {

	private DAOFactory daoFactory;
	private DepartamentoExisteConsistenciaValida departamentoNameConsistencyIsValid = new DepartamentoExisteConsistenciaValidaImpl();

	public RegisterNewDepartamentoImpl(final DAOFactory daoFactory) {
		setDaoFactory(daoFactory);
	}
	
	private void setDaoFactory(final DAOFactory daoFactory) {
		if (ObjectHelper.isNull(daoFactory)) {
			var userMessage = "Se ha presentado un problema inesperado, tratando de llevar a cabo el registro de la información del departamento deseado. Por favor intente de nuevo y si el problema persiste, llame a Luz Mery Rios Alzate...";
			var technicalMessage = "El DAO factory requerido para crear la clase registro de un departamento llegó nulo...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
		}
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void execute(final StateDomain data) {
		departamentoNameConsistencyIsValid.execute(data.getName(),"Nombre");

	 // Crear un filtro de entidad para buscar si existe un departamento con el mismo nombre
	    var departamentoEntityFilter = new StateEntity();
		departamentoEntityFilter.setName(data.getName());

	    // Buscar en la base de datos utilizando el filtro
	    boolean departamentoExists = !daoFactory.getStateDAO().findByFilter(departamentoEntityFilter).isEmpty();

	    // Lanzar excepción si se encuentra un departamento con el mismo nombre
	    if (departamentoExists) {
	        String userMessage = "El departamento ya existe";
	        String technicalMessage = "El departamento con el nombre '" + data.getName() + "' ya existe en la base de datos.";
	        
	        throw BusinessLogicVictusResidenciasException.crear(userMessage, technicalMessage);
	    }


	    // Si no existe, procede a crear el departamento
	    var departamentoDomainToMap = StateDomain.create(generateId(), data.getName(),data.getCountry());
	    var departamentoEntity = StateEntityAdapter.getStateEntityAdapter().adaptSource(departamentoDomainToMap);
	    daoFactory.getStateDAO().create(departamentoEntity);
	}
	
	private UUID generateId() {
	    UUID id;
	    do {
	        id = UUIDHelper.generate();
	    } while (daoFactory.getStateDAO().fingByID(id) != null);
	    
	    return id;
	}

}
