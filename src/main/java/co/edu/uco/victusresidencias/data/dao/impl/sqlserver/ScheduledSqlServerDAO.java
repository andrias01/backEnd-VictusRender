package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.ScheduledDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.ScheduledEntity;

public final class ScheduledSqlServerDAO extends SqlDAO implements ScheduledDAO {

    public ScheduledSqlServerDAO(final Connection connection) {
        super(connection);
    }

    @Override
    public ScheduledEntity fingByID(UUID id) {
        var scheduledEntityFilter = new ScheduledEntity();
        scheduledEntityFilter.setId(id);

        var result = findByFilter(scheduledEntityFilter);
        return (result.isEmpty()) ? new ScheduledEntity() : result.get(0);
    }

    @Override
    public List<ScheduledEntity> findAll() {
        return findByFilter(new ScheduledEntity());
    }

    @Override
    public List<ScheduledEntity> findByFilter(ScheduledEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<ScheduledEntity>();
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
                var scheduledEntityTmp = new ScheduledEntity();

                scheduledEntityTmp.setId(UUID.fromString(result.getString("id")));
                scheduledEntityTmp.setName(result.getString("name"));
                scheduledEntityTmp.setAvailability(true);//hay que organizarlo despues
                scheduledEntityTmp.setDateTimeStart(result.getTimestamp("startTime").toLocalDateTime());
                scheduledEntityTmp.setDateTimeEnd(result.getTimestamp("endTime").toLocalDateTime());

                resultSelect.add(scheduledEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al realizar la consulta de agendamientos.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de agendamientos en la base de datos." :
                    "Problema preparando la consulta de agendamientos en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(ScheduledEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO Scheduled(id, name, description, startTime, endTime) VALUES (?, ?, ?, ?, ?)");

        var dispo = "true";
        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, dispo);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(data.getDateTimeStart()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(data.getDateTimeEnd()));

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al registrar el nuevo agendamiento.";
            var technicalMessage = "Error al intentar registrar un nuevo agendamiento en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM Scheduled WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al eliminar el agendamiento.";
            var technicalMessage = "Error al intentar eliminar el agendamiento en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(ScheduledEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE Scheduled SET name = ?, description = ?, startTime = ?, endTime = ? WHERE id = ?");
        
        var dispo = "false";
        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, dispo);//organizar despues
            preparedStatement.setTimestamp(3, Timestamp.valueOf(data.getDateTimeStart()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(data.getDateTimeEnd()));
            preparedStatement.setObject(5, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al actualizar el agendamiento.";
            var technicalMessage = "Error al intentar actualizar el agendamiento en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, name, description, startTime, endTime ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM Scheduled ");
    }

    private void createWhere(final StringBuilder statement, final ScheduledEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

        if (!TextHelper.isEmpty(filter.getName())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("name = ? ");
            parameters.add(filter.getName());
        }

        if (!ObjectHelper.isNull(filter.getAvailability())) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("description = ? ");
            parameters.add(filter.getAvailability());
        }

        if (filter.getDateTimeStart() != null) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("startTime = ? ");
            parameters.add(Timestamp.valueOf(filter.getDateTimeStart()));
        }

        if (filter.getDateTimeEnd() != null) {
            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
            statement.append("endTime = ? ");
            parameters.add(Timestamp.valueOf(filter.getDateTimeEnd()));
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY startTime ASC");
    }

}
