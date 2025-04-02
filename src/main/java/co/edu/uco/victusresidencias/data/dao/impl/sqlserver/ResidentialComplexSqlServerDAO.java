package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.ResidentialComplexDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.CityEntity;
import co.edu.uco.victusresidencias.entity.ResidentialComplexEntity;

public final class ResidentialComplexSqlServerDAO extends SqlDAO implements ResidentialComplexDAO {

    public ResidentialComplexSqlServerDAO(final Connection connection) {
        super(connection);
    }
    
    @Override
	public ResidentialComplexEntity fingByID(UUID id) {
		var residentialComplexEntityFilter = new ResidentialComplexEntity();
        residentialComplexEntityFilter.setId(id);

        var result = findByFilter(residentialComplexEntityFilter);
        return (result.isEmpty()) ? new ResidentialComplexEntity() : result.get(0);
	}

    @Override
    public List<ResidentialComplexEntity> findAll() {
        return findByFilter(new ResidentialComplexEntity());
    }

    @Override
    public List<ResidentialComplexEntity> findByFilter(ResidentialComplexEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<ResidentialComplexEntity>();
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
                var residentialComplexEntityTmp = new ResidentialComplexEntity();
                var cityEntityTmp = new CityEntity();

                residentialComplexEntityTmp.setId(UUID.fromString(result.getString("id")));
                residentialComplexEntityTmp.setName(result.getString("name"));

                cityEntityTmp.setId(UUID.fromString(result.getString("city")));
                residentialComplexEntityTmp.setCity(cityEntityTmp);

                resultSelect.add(residentialComplexEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema tratando de llevar a cabo la consulta de los complejos residenciales.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de complejos residenciales en la base de datos." :
                    "Problema preparando la consulta de complejos residenciales en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(ResidentialComplexEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO ResidentialComplex(id, name, city) VALUES (?, ?, ?)");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setObject(3, data.getCity().getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema tratando de llevar a cabo el registro del nuevo complejo residencial.";
            var technicalMessage = "Error al intentar registrar un nuevo complejo residencial en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM ResidentialComplex WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema tratando de eliminar el complejo residencial.";
            var technicalMessage = "Error al intentar eliminar el complejo residencial en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(ResidentialComplexEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE ResidentialComplex SET name = ?, city = ? WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, data.getName());
            preparedStatement.setObject(2, data.getCity().getId());
            preparedStatement.setObject(3, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema tratando de actualizar el complejo residencial.";
            var technicalMessage = "Error al intentar actualizar el complejo residencial en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, name, city ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM ResidentialComplex ");
    }

    private void createWhere(final StringBuilder statement, final ResidentialComplexEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

        if (!TextHelper.isEmpty(filter.getName())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("name = ? ");
            parameters.add(filter.getName());
        }

        if (!UUIDHelper.isDefault(filter.getCity().getId())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("city = ? ");
            parameters.add(filter.getCity().getId());
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY name ASC");
    }

	
}
