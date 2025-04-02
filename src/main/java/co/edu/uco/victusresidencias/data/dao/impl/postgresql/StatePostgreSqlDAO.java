package co.edu.uco.victusresidencias.data.dao.impl.postgresql;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.StateDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.CountryEntity;
import co.edu.uco.victusresidencias.entity.StateEntity;

final class StatePostgreSqlDAO extends SqlDAO implements StateDAO {
	
	protected StatePostgreSqlDAO(final Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public StateEntity fingByID(UUID id) {
		var stateEntityFilter = new StateEntity();
	    stateEntityFilter.setId(id);
	    
	    var result = findByFilter(stateEntityFilter);
	    return (result.isEmpty()) ? new StateEntity() : result.get(0);
	}

	@Override
	public List<StateEntity> findAll() {
		return findByFilter(new StateEntity());
	}

	@Override
	public List<StateEntity> findByFilter(StateEntity filter) {
		final var statement = new StringBuilder();
	    final var parameters = new ArrayList<>();
	    final var resultSelect = new ArrayList<StateEntity>();
	    var statementWasPrepared = false;		 
	    
	    // Select
	    createSelect(statement);
	    
	    // From
	    createFrom(statement);
	    
	    // Where
	    createWhere(statement, filter, parameters);
	    
	    // Order By
	    createOrderBy(statement);
	    
	    try (var preparedStatement = getConnection().prepareStatement(statement.toString())) {
	        for (var arrayIndex = 0; arrayIndex < parameters.size(); arrayIndex++) {
	            var statementIndex = arrayIndex + 1;
	            preparedStatement.setObject(statementIndex, parameters.get(arrayIndex));
	        }
	        
	        statementWasPrepared = true;
	        
	        final var result = preparedStatement.executeQuery();
	        while (result.next()) {
	            var stateEntityTmp = new StateEntity();
	            var countryEntityTmp = new CountryEntity();
	            stateEntityTmp.setId(UUID.fromString(result.getString("id")));
	            stateEntityTmp.setName(result.getString("name"));
	            
	            countryEntityTmp.setId(UUID.fromString(result.getString("country")));	          
	            stateEntityTmp.setCountry(countryEntityTmp);
	            
	            resultSelect.add(stateEntityTmp);		
	        }
	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de llevar a cabo la consulta de los estados.";
	        var technicalMessage = statementWasPrepared ? 
	            "Problema ejecutando la consulta de estados en la base de datos." : 
	            "Problema preparando la consulta de estados en la base de datos.";
	        
	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
	    
	    return resultSelect;
	}
	
	private void createSelect(final StringBuilder statement) {
	    statement.append("SELECT id, name, country ");
	}

	private void createFrom(final StringBuilder statement) {
	    statement.append("FROM state ");
	}

	private void createWhere(final StringBuilder statement, final StateEntity filter, final List<Object> parameters) {
	    if (!UUIDHelper.isDefault(filter.getId())) {
	        statement.append("WHERE id = ? ");
	        parameters.add(filter.getId());
	    }
	    
	    if (!TextHelper.isEmpty(filter.getName())) {
	        statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
	        statement.append("name = ? ");
	        parameters.add(filter.getName());
	    }
	}

	private void createOrderBy(final StringBuilder statement) {
	    statement.append("ORDER BY name ASC");
	}


}
