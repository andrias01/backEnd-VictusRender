package co.edu.uco.victusresidencias.data.dao.impl.sqlserver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.AdministratorDAO;
import co.edu.uco.victusresidencias.data.dao.impl.sql.SqlDAO;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;

final class AdministratorSqlServerDAO extends SqlDAO implements AdministratorDAO {
    
    protected AdministratorSqlServerDAO(final Connection connection) {
        super(connection);
    }

    @Override
    public AdministratorEntity fingByID(UUID id) {
        var adminFilter = new AdministratorEntity();
        adminFilter.setId(id);

        var result = findByFilter(adminFilter);
        return result.isEmpty() ? new AdministratorEntity() : result.get(0);
    }

    @Override
    public List<AdministratorEntity> findAll() {
        return findByFilter(new AdministratorEntity());
    }

    @Override
    public List<AdministratorEntity> findByFilter(AdministratorEntity filter) {
        final var statement = new StringBuilder();
        final var parameters = new ArrayList<>();
        final var resultSelect = new ArrayList<AdministratorEntity>();
        var statementWasPrepared = false;

        createSelect(statement);
        createFrom(statement);
        createWhere(statement, filter, parameters);
        createOrderBy(statement);

        try (var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            for (var i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            statementWasPrepared = true;
            final var result = preparedStatement.executeQuery();
            while (result.next()) {
                var adminEntityTmp = new AdministratorEntity();
                adminEntityTmp.setId(UUIDHelper.convertToUUID(result.getString("id")));
                adminEntityTmp.setName(result.getString("name"));
                adminEntityTmp.setEmail(result.getString("email"));
                
                resultSelect.add(adminEntityTmp);
            }
        } catch (final SQLException exception) {
            var userMessage = "Se ha presentado un problema al realizar la consulta de administradores.";
            var technicalMessage = statementWasPrepared ? 
                "Problema ejecutando la consulta en la base de datos." : 
                "Problema preparando la consulta en la base de datos.";

            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
        return resultSelect;
    }

    @Override
    public void create(AdministratorEntity data) {
        final var statement = new StringBuilder();
        statement.append("INSERT INTO Administrator(id, name, email) VALUES (?, ?, ?)");

        try (var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, data.getId());
            preparedStatement.setString(2, data.getName());
            preparedStatement.setString(3, data.getEmail());

            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            var userMessage = "Problema al registrar el nuevo administrador.";
            var technicalMessage = "Problema registrando el nuevo administrador en la base de datos SQL Server.";
            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void delete(UUID id) {
        final var statement = new StringBuilder();
        statement.append("DELETE FROM Administrator WHERE id = ?");

        try (var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setObject(1, id);

            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            var userMessage = "Problema al eliminar el administrador seleccionado.";
            var technicalMessage = "Problema al eliminar el administrador en la base de datos SQL Server.";
            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    @Override
    public void update(AdministratorEntity data) {
        final var statement = new StringBuilder();
        statement.append("UPDATE Administrator SET name = ?, email = ? WHERE id = ?");

        try (var preparedStatement = getConnection().prepareStatement(statement.toString())) {
            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getEmail());
            preparedStatement.setObject(3, data.getId());

            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            var userMessage = "Problema al actualizar la información del administrador.";
            var technicalMessage = "Problema al actualizar la información en la base de datos SQL Server.";
            throw DataVictusResidenciasException.crear(userMessage, technicalMessage, exception);
        }
    }

    private void createSelect(final StringBuilder statement) {
        statement.append("SELECT id, name, email ");
    }

    private void createFrom(final StringBuilder statement) {
        statement.append("FROM Administrator ");
    }

    private void createWhere(final StringBuilder statement, final AdministratorEntity filter, final List<Object> parameters) {
        if (!UUIDHelper.isDefault(filter.getId())) {
            statement.append("WHERE id = ? ");
            parameters.add(filter.getId());
        }

        if (!TextHelper.isEmpty(filter.getName())) {
            statement.append(parameters.isEmpty() ? "WHERE " : "AND ");
            statement.append("name = ? ");
            parameters.add(filter.getName());
        }

        if (!TextHelper.isEmpty(filter.getEmail())) {
            statement.append(parameters.isEmpty() ? "WHERE " : "AND ");
            statement.append("email = ? ");
            parameters.add(filter.getEmail());
        }
    }

    private void createOrderBy(final StringBuilder statement) {
        statement.append("ORDER BY name ASC");
    }
}
