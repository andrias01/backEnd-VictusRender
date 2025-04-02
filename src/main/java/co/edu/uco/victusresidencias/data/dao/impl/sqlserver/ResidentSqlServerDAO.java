package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.ResidentDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.ResidentEntity;


public final class ResidentSqlServerDAO extends SqlDAO implements ResidentDAO {

    public ResidentSqlServerDAO(final Connection connection) {
        super(connection);
    }


	@Override
	public ResidentEntity fingByID(UUID id) {
		var residentEntityFilter = new ResidentEntity();
        residentEntityFilter.setId(id);

        var result = findByFilter(residentEntityFilter);
        return (result.isEmpty()) ? new ResidentEntity() : result.get(0);
	}


    @Override
    public List<ResidentEntity> findAll() {
        return findByFilter(new ResidentEntity());
    }

    @Override
    public List<ResidentEntity> findByFilter(ResidentEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<ResidentEntity>();
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
                var residentEntityTmp = new ResidentEntity();
                var phone = residentEntityTmp.getContactNumber();

                residentEntityTmp.setId(UUID.fromString(result.getString("id")));
                residentEntityTmp.setName(result.getString("name"));
                residentEntityTmp.setLastName(result.getString("apellido"));
                residentEntityTmp.setContactNumber(phone);

                resultSelect.add(residentEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al realizar la consulta de residentes.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de residentes en la base de datos." :
                    "Problema preparando la consulta de residentes en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(ResidentEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO Resident(id, name, email, phone) VALUES (?, ?, ?, ?)");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getLastName());
            preparedStatement.setString(4, data.getContactNumber());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al registrar el nuevo residente.";
            var technicalMessage = "Error al intentar registrar un nuevo residente en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM Resident WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al eliminar el residente.";
            var technicalMessage = "Error al intentar eliminar el residente en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(ResidentEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE Resident SET name = ?, email = ?, phone = ? WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getLastName());
            preparedStatement.setString(3, data.getContactNumber());
            preparedStatement.setObject(4, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al actualizar el residente.";
            var technicalMessage = "Error al intentar actualizar el residente en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, name, email, phone ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM Resident ");
    }

    private void createWhere(final StringBuilder statement, final ResidentEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

        if (!TextHelper.isEmpty(filter.getName())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("name = ? ");
            parameters.add(filter.getName());
        }

        if (!TextHelper.isEmpty(filter.getLastName())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("email = ? ");
            parameters.add(filter.getLastName());
        }

        if (!ObjectHelper.isNull(filter.getContactNumber())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("phone = ? ");
            parameters.add(filter.getContactNumber());
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY name ASC");
    }

}
