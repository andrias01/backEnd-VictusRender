package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.CityDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.CityEntity;
import co.edu.uco.victusresidencias.entity.StateEntity;


final class CitySqlServerDAO extends SqlDAO implements CityDAO {
	
	protected CitySqlServerDAO(final Connection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}
	@Override
	public CityEntity fingByID(UUID id) {
		var cityEntityFilter = new CityEntity();
	    cityEntityFilter.setId(id);
	    
	    var result = findByFilter(cityEntityFilter);
	    return (result.isEmpty()) ? new CityEntity() : result.get(0);
	}

	@Override
	public List<CityEntity> findAll() {
		return findByFilter(new CityEntity());
	}

	@Override
	public List<CityEntity> findByFilter(CityEntity filter) {
		final var statement = new StringBuilder();
	    final var parameters = new ArrayList<>();
	    final var resultSelect = new ArrayList<CityEntity>();
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
	            var cityEntityTmp = new CityEntity();
	            var stateEntityTmp = new StateEntity();
	            cityEntityTmp.setId(UUID.fromString(result.getString("id")));
	            cityEntityTmp.setName(result.getString("name"));
	            
	            stateEntityTmp.setId(UUID.fromString(result.getString("state")));
	            
	            cityEntityTmp.setState(stateEntityTmp);
	            
	           
	            resultSelect.add(cityEntityTmp);		
	        }
	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de llevar a cabo la consulta de las ciudades.";
	        var technicalMessage = statementWasPrepared ? 
	            "Problema ejecutando la consulta de ciudades en la base de datos." : 
	            "Problema preparando la consulta de ciudades en la base de datos.";
	        
	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
	    
	    return resultSelect;
	}

	@Override
	public void create(CityEntity data) {
		final StringBuilder statement = new StringBuilder();
		statement.append("INSERT INTO City(id, name, state) VALUES (?, ?, ?)");
 
		try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
 
			preparedStatement.setObject(1, data.getId());
			preparedStatement.setString(2, data.getName());
			preparedStatement.setObject(3, data.getState().getId());
 
			preparedStatement.executeUpdate();
 
		} catch (final SQLException exception) {
			var userMessage = "Se ha presentado un problema tratando de llevar a cabo el registro de la información del nuevo país. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
			var technicalMessage = "Se ha presentado un problema al tratar de registrar la informaciòn del nuevo país en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";
 
			throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
		}
	}
	
	@Override
	public void delete(UUID data) {
		final StringBuilder statement = new StringBuilder();
	    statement.append("DELETE FROM City WHERE id = ?");

	    try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {

	        preparedStatement.setObject(1, data);

	        preparedStatement.executeUpdate();

	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de eliminar la ciudad seleccionada. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
	        var technicalMessage = "Se ha presentado un problema al tratar de eliminar la ciudad en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";

	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
		
	}
	@Override
	public void update(CityEntity data) {
		final StringBuilder statement = new StringBuilder();
	    statement.append("UPDATE City SET name = ?, state = ? WHERE id = ?");

	    try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {

	        preparedStatement.setString(1, data.getName());
	        preparedStatement.setObject(2, data.getState().getId());
	        preparedStatement.setObject(3, data.getId());

	        preparedStatement.executeUpdate();

	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de actualizar la información de la ciudad. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
	        var technicalMessage = "Se ha presentado un problema al tratar de actualizar la información de la ciudad en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";

	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
		
	}
	
	private void createSelect(final StringBuilder statement) {
	    statement.append("SELECT id, name, state ");
	}

	private void createFrom(final StringBuilder statement) {
	    statement.append("FROM city ");
	}

	private void createWhere(final StringBuilder statement, final CityEntity filter, final List<Object> parameters) {
	    if (!UUIDHelper.isDefault(filter.getId())) {
	        statement.append("WHERE id = ? ");
	        parameters.add(filter.getId());
	    }
	    
	    if (!TextHelper.isEmpty(filter.getName())) {
	        statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
	        statement.append("name = ? ");
	        parameters.add(filter.getName());
	    }
	    
	    if (!UUIDHelper.isDefault(filter.getState().getId())) {
	        statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
	        statement.append("state = ? ");
	        parameters.add(filter.getState().getId());
	    }
	}

	private void createOrderBy(final StringBuilder statement) {
	    statement.append("ORDER BY name ASC");
	}

	
}
