package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.TurnDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.TurnEntity;

public final class TurnSqlServerDAO extends SqlDAO implements TurnDAO {

    public TurnSqlServerDAO(final Connection connection) {
        super(connection);
    }

    @Override
    public TurnEntity fingByID(UUID id) {
        var turnEntityFilter = new TurnEntity();
        turnEntityFilter.setId(id);

        var result = findByFilter(turnEntityFilter);
        return (result.isEmpty()) ? new TurnEntity() : result.get(0);
    }

    @Override
    public List<TurnEntity> findAll() {
        return findByFilter(new TurnEntity());
    }

    @Override
    public List<TurnEntity> findByFilter(TurnEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<TurnEntity>();
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
                var turnEntityTmp = new TurnEntity();

                turnEntityTmp.setId(UUID.fromString(result.getString("id")));
                turnEntityTmp.setScheduled(null);//(result.getString("description"));
                turnEntityTmp.setStartTime(result.getTimestamp("startTime").toLocalDateTime());
                turnEntityTmp.setEndTime(result.getTimestamp("endTime").toLocalDateTime());

                resultSelect.add(turnEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al realizar la consulta de turnos.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de turnos en la base de datos." :
                    "Problema preparando la consulta de turnos en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(TurnEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO Turn(id, description, startTime, endTime) VALUES (?, ?, ?, ?)");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            //preparedStatement.setString(2, data.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(data.getStartTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(data.getEndTime()));

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al registrar el nuevo turno.";
            var technicalMessage = "Error al intentar registrar un nuevo turno en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM Turn WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al eliminar el turno.";
            var technicalMessage = "Error al intentar eliminar el turno en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(TurnEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE Turn SET description = ?, startTime = ?, endTime = ? WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, "pendiente");
            preparedStatement.setTimestamp(2, Timestamp.valueOf(data.getStartTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(data.getEndTime()));
            preparedStatement.setObject(4, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al actualizar el turno.";
            var technicalMessage = "Error al intentar actualizar el turno en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, description, startTime, endTime ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM Turn ");
    }

    private void createWhere(final StringBuilder statement, final TurnEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

//        if (!TextHelper.isEmpty(filter.getDescription())) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("description = ? ");
//            parameters.add(filter.getDescription());
//        }

        if (filter.getStartTime() != null) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("startTime = ? ");
            parameters.add(Timestamp.valueOf(filter.getStartTime()));
        }

        if (filter.getEndTime() != null) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("endTime = ? ");
            parameters.add(Timestamp.valueOf(filter.getEndTime()));
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY startTime ASC");
    }

}
