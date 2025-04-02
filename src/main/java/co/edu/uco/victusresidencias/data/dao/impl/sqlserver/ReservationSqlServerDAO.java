package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.ReservationDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.ReservationEntity;

public final class ReservationSqlServerDAO extends SqlDAO implements ReservationDAO {

    public ReservationSqlServerDAO(final Connection connection) {
        super(connection);
    }

    @Override
    public ReservationEntity fingByID(UUID id) {
        var reservationEntityFilter = new ReservationEntity();
        reservationEntityFilter.setId(id);

        var result = findByFilter(reservationEntityFilter);
        return (result.isEmpty()) ? new ReservationEntity() : result.get(0);
    }

    @Override
    public List<ReservationEntity> findAll() {
        return findByFilter(new ReservationEntity());
    }

    @Override
    public List<ReservationEntity> findByFilter(ReservationEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<ReservationEntity>();
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
                var reservationEntityTmp = new ReservationEntity();

                reservationEntityTmp.setId(UUID.fromString(result.getString("id")));
//                reservationEntityTmp.setDescription(result.getString("description"));
//                reservationEntityTmp.setStartTime(result.getTimestamp("startTime").toLocalDateTime());
//                reservationEntityTmp.setEndTime(result.getTimestamp("endTime").toLocalDateTime());
//                reservationEntityTmp.setResidentId(UUID.fromString(result.getString("residentId")));
//                reservationEntityTmp.setZoneId(UUID.fromString(result.getString("zoneId")));

                resultSelect.add(reservationEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al realizar la consulta de reservas.";
            var technicalMessage = statementWasPrepared ?
                    "Problema ejecutando la consulta de reservas en la base de datos." :
                    "Problema preparando la consulta de reservas en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }

        return resultSelect;
    }

    @Override
    public void create(ReservationEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("INSERT INTO Reservation(id, description, startTime, endTime, residentId, zoneId) VALUES (?, ?, ?, ?, ?, ?)");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
//            preparedStatement.setString(2, data.getDescription());
//            preparedStatement.setTimestamp(3, Timestamp.valueOf(data.getStartTime()));
//            preparedStatement.setTimestamp(4, Timestamp.valueOf(data.getEndTime()));
//            preparedStatement.setObject(5, data.getResidentId());
//            preparedStatement.setObject(6, data.getZoneId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al registrar la nueva reserva.";
            var technicalMessage = "Error al intentar registrar una nueva reserva en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final StringBuilder statement = new StringBuilder();
        statement.append("DELETE FROM Reservation WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);
            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al eliminar la reserva.";
            var technicalMessage = "Error al intentar eliminar la reserva en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(ReservationEntity data) {
        final StringBuilder statement = new StringBuilder();
        statement.append("UPDATE Reservation SET description = ?, startTime = ?, endTime = ?, residentId = ?, zoneId = ? WHERE id = ?");

        try (final var preparedStatement = getConnection().prepareStatement(statement.toString())) {
//            preparedStatement.setString(1, data.getDescription());
//            preparedStatement.setTimestamp(2, Timestamp.valueOf(data.getStartTime()));
//            preparedStatement.setTimestamp(3, Timestamp.valueOf(data.getEndTime()));
//            preparedStatement.setObject(4, data.getResidentId());
//            preparedStatement.setObject(5, data.getZoneId());
//            preparedStatement.setObject(6, data.getId());

            preparedStatement.executeUpdate();

        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al actualizar la reserva.";
            var technicalMessage = "Error al intentar actualizar la reserva en la base de datos SQL Server.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, description, startTime, endTime, residentId, zoneId ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM Reservation ");
    }

    private void createWhere(final StringBuilder statement, final ReservationEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

//        if (!TextHelper.isEmpty(filter.getDescription())) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("description = ? ");
//            parameters.add(filter.getDescription());
//        }
//
//        if (filter.getStartTime() != null) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("startTime = ? ");
//            parameters.add(Timestamp.valueOf(filter.getStartTime()));
//        }
//
//        if (filter.getEndTime() != null) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("endTime = ? ");
//            parameters.add(Timestamp.valueOf(filter.getEndTime()));
//        }
//
//        if (!UUIDHelper.isDefault(filter.getResidentId())) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("residentId = ? ");
//            parameters.add(filter.getResidentId());
//        }
//
//        if (!UUIDHelper.isDefault(filter.getZoneId())) {
//            statement.append((parameters.isEmpty()) ? "WHERE " : "AND ");
//            statement.append("zoneId = ? ");
//            parameters.add(filter.getZoneId());
//        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY startTime ASC");
    }

}
