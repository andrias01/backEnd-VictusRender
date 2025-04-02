package co.edu.uco.victusresidencias.data.dao.impl.postgresql;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.CountryDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.CountryEntity;

final class CountryPostgreSQLDAO extends SqlDAO implements CountryDAO {
	
	public CountryPostgreSQLDAO(Connection connection) {
		super(connection);
	}	
	
	@Override
	public CountryEntity fingByID(UUID id) {
		var countryEntityFilter = new CountryEntity();
	    countryEntityFilter.setId(id);
	    
	    var result = findByFilter(countryEntityFilter);
	    return (result.isEmpty()) ? null : result.get(0); // Retorna null si no encuentra
	}
	

	@Override
	public List<CountryEntity> findAll() {
		CountryEntity pruebaPais = new CountryEntity();
		System.out.println("el pais nuevo tiene el id " + pruebaPais.getId());
		return findByFilter(new CountryEntity());  //default 0000 y name =""
	}
	

	@Override
	public List<CountryEntity> findByFilter(CountryEntity filter) { //filter datos 
		System.out.println("La carga paso por aqui del filter");
		final var statement = new StringBuilder(); //sentencia SQL
	    final var parameters = new ArrayList<>();  // ?
	    final var resultSelect = new ArrayList<CountryEntity>(); //select para una lista entity
	    var statementWasPrepared = false;	//sentencia fue preparada?	 
	    
	    // Select
	    createSelect(statement);
//		SELECT id, name FROM country ORDER BY name ASC
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
	        System.out.println("Sentencia preparada " + statement);
	        
	        statementWasPrepared = true;
	        
	        final var result = preparedStatement.executeQuery();
	        while (result.next()) {
	            var countryEntityTmp = new CountryEntity();
	            //var stateEntityTmp = new StateEntity();
	            countryEntityTmp.setId(UUID.fromString(result.getString("id")));
	            System.out.println("id que inserta a la lista " + UUID.fromString(result.getString("id")));
	            
	            countryEntityTmp.setName(result.getString("name"));
	            
	            //stateEntityTmp.setId(UUID.fromString(result.getString("state")));
	            
	            //countryEntityTmp.setState(stateEntityTmp);
	            
	            resultSelect.add(countryEntityTmp);		
	        }
	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de llevar a cabo la consulta de los paises.";
	        var technicalMessage = statementWasPrepared ? 
	            "Problema ejecutando la consulta de paises en la base de datos." : 
	            "Problema preparando la consulta de paises en la base de datos.";
	        
	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
	    
	    return resultSelect;
	}
	
	private void createSelect(final StringBuilder statement) {
		statement.append("SELECT id, name ");
	}
	
	private void createFrom(final StringBuilder statement) {
		statement.append("FROM country ");
	}

	private void createWhere(final StringBuilder statement, 
            final CountryEntity filter, 
            final List<Object> parameters) {//filter.getId = 0000000
			if (!UUIDHelper.isDefault(filter.getId())) { // Se asegura de que el ID no sea el valor predeterminado
				System.out.println("Sentencia preparada con where " + filter.getId());
				statement.append("WHERE id = ? ");
				parameters.add(filter.getId());
			} else if (!TextHelper.isEmpty(filter.getName())) { // Condición para filtro de nombre
				statement.append("WHERE name = ? ");
				parameters.add(filter.getName());
			}//este if es para filtar por id o por nombre
	}
	
	private void createOrderBy(final StringBuilder statement) {
		statement.append("ORDER BY name ASC");
	}

	@Override
	public void create(CountryEntity data) {
		
		// Verificar si ya existe un país con el mismo nombre
	    CountryEntity filter = new CountryEntity();
	    filter.setName(data.getName());
	    if (!findByFilter(filter).isEmpty()) {
	        throw DataVictusResidenciasException.crear(
	            "El país ya existe", "No se puede crear un país con el nombre duplicado: " + data.getName());
	    }
	    
	    final StringBuilder statement = new StringBuilder();
	    statement.append("INSERT INTO country(id, name) VALUES (?, ?)");

	    // Verificar si el ID es el UUID predeterminado, y si es así, generar uno nuevo.
	    if (UUIDHelper.isDefault(data.getId())) {
	        data.setId(UUIDHelper.generate()); // Genera un UUID único si es el valor predeterminado.
	    }

	    try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
	        preparedStatement.setObject(1, data.getId());
	        preparedStatement.setString(2, data.getName());

	        preparedStatement.executeUpdate();
	        System.out.println("Se creó el país con el nombre " + data.getName() + " exitosamente");

	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de llevar a cabo el registro de la información del nuevo país. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
	        var technicalMessage = "Se ha presentado un problema al tratar de registrar la información del nuevo país en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";

	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
	}



	@Override
	public void delete(UUID data) {
		final StringBuilder statement = new StringBuilder();
	    statement.append("DELETE FROM country WHERE id = ?");

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
	public void update(CountryEntity data) {
		final StringBuilder statement = new StringBuilder();
	    statement.append("UPDATE country SET name = ? WHERE id = ?");

	    try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {

	        preparedStatement.setString(1, data.getName());
	        preparedStatement.setObject(2, data.getId());

	        preparedStatement.executeUpdate();

	    } catch (final SQLException exception) {
	        var userMessage = "Se ha presentado un problema tratando de actualizar la información de la ciudad. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
	        var technicalMessage = "Se ha presentado un problema al tratar de actualizar la información de la ciudad en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";

	        throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
	    }
		
	}

	
}
