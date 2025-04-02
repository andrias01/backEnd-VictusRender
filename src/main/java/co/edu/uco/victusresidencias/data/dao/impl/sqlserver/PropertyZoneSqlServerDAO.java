package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.PropertyZoneDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.PropertyZoneEntity;
import co.edu.uco.victusresidencias.entity.ResidentialComplexEntity;

public final class PropertyZoneSqlServerDAO extends SqlDAO implements PropertyZoneDAO {

    public PropertyZoneSqlServerDAO(final Connection connection) {
        super(connection);
    }

    @Override
	public PropertyZoneEntity fingByID(UUID id) {
    	var propertyZoneEntityFilter = new PropertyZoneEntity();
        propertyZoneEntityFilter.setId(id);

        var result = findByFilter(propertyZoneEntityFilter);
        return (result.isEmpty()) ? new PropertyZoneEntity() : result.get(0);
	}

    @Override
    public List<PropertyZoneEntity> findAll() {
        return findByFilter(new PropertyZoneEntity());
    }

    @Override
    public List<PropertyZoneEntity> findByFilter(PropertyZoneEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<PropertyZoneEntity>();
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
                var propertyZoneEntityTmp = new PropertyZoneEntity();
                var residentialComplexEntityTmp = new ResidentialComplexEntity();

                propertyZoneEntityTmp.setId(UUID.fromString(result.getString("id")));
                propertyZoneEntityTmp.setPropertyZoneType(result.getString("name"));

                residentialComplexEntityTmp.setId(UUID.fromString(result.getString("residentialComplex")));
                propertyZoneEntityTmp.setResidentialComplex(residentialComplexEntityTmp);

                resultSelect.add(propertyZoneEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema tratando de realizar la consulta de zonas de propiedad.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de zonas de propiedad en la base de datos." :
                    "Problema preparando la consulta de zonas de propiedad en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(PropertyZoneEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO PropertyZone(id, name, residentialComplex) VALUES (?, ?, ?)");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            preparedStatement.setString(2, data.getPropertyZoneType());
            preparedStatement.setObject(3, data.getResidentialComplex().getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al registrar la nueva zona de propiedad.";
            var technicalMessage = "Error al intentar registrar una nueva zona de propiedad en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM PropertyZone WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al eliminar la zona de propiedad.";
            var technicalMessage = "Error al intentar eliminar la zona de propiedad en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(PropertyZoneEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE PropertyZone SET name = ?, residentialComplex = ? WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, data.getPropertyZoneType());
            preparedStatement.setObject(2, data.getResidentialComplex().getId());
            preparedStatement.setObject(3, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al actualizar la zona de propiedad.";
            var technicalMessage = "Error al intentar actualizar la zona de propiedad en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, name, residentialComplex ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM PropertyZone ");
    }

    private void createWhere(final StringBuilder statement, final PropertyZoneEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

        if (!TextHelper.isEmpty(filter.getPropertyZoneType())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("name = ? ");
            parameters.add(filter.getPropertyZoneType());
        }

        if (!UUIDHelper.isDefault(filter.getResidentialComplex().getId())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("residentialComplex = ? ");
            parameters.add(filter.getResidentialComplex().getId());
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY name ASC");
    }

	
}
