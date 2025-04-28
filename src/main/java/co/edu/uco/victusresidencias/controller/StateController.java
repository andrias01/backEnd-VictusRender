/*package co.edu.uco.victusresidencias.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.StateDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.StateEntityAdapter;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.StateResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.domain.StateDomain;
import co.edu.uco.victusresidencias.dto.StateDTO;
import co.edu.uco.victusresidencias.entity.StateEntity;

@RestController
@RequestMapping("/api/v1/states")
public final class StateController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public StateController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> create(@RequestBody StateDTO state) {
        var messages = new ArrayList<String>();

        try {
            var registerNewStateFacade = new RegisterNewStateFacadeImpl();
            registerNewStateFacade.execute(state);

            messages.add("El estado se registró de forma satisfactoria.");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (final VictusResidenciasException | UcoApplicationException exception) {
            messages.add(exception.getUserMessage());
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);

        } catch (final Exception exception) {
            messages.add("Se ha presentado un problema inesperado al registrar el estado.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateResponse> update(@PathVariable UUID id, @RequestBody StateDTO state) {
        var response = new StateResponse();
        var messages = new ArrayList<String>();

        try {
            StateEntity existingEntity = daoFactory.getStateDAO().findById(id);

            if (existingEntity == null) {
                messages.add("No se encontró un estado con el ID especificado.");
                response.setMessages(messages);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            state.setId(id.toString());
            StateDomain domain = StateDTOAdapter.getStateDTOAdapter().adaptSource(state);
            StateEntity updatedEntity = StateEntityAdapter.getStateEntityAdapter().adaptSource(domain);

            daoFactory.getStateDAO().update(updatedEntity);

            messages.add("El estado se actualizó de manera satisfactoria.");
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (final Exception exception) {
            messages.add("Error al actualizar el estado. Por favor intente nuevamente.");
            exception.printStackTrace();
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        var messages = new ArrayList<String>();

        try {
            daoFactory.getStateDAO().delete(id);
            messages.add("El estado se eliminó de manera satisfactoria.");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (final Exception exception) {
            messages.add("Error al eliminar el estado. Por favor intente nuevamente.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<StateResponse> retrieveAll() {
        var response = new StateResponse();
        var messages = new ArrayList<String>();

        try {
            List<StateEntity> entities = daoFactory.getStateDAO().findAll();
            List<StateDomain> domains = StateEntityAdapter.getStateEntityAdapter().adaptTarget(entities);
            List<StateDTO> dtos = StateDTOAdapter.getStateDTOAdapter().adaptTarget(domains);

            response.setData(dtos);
            messages.add("Los estados fueron consultados satisfactoriamente.");
            response.setMessages(messages);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (final Exception exception) {
            messages.add("Error al consultar los estados. Por favor intente nuevamente.");
            exception.printStackTrace();
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> retrieveById(@PathVariable UUID id) {
        var response = new StateResponse();
        var messages = new ArrayList<String>();

        try {
            StateEntity entity = daoFactory.getStateDAO().findById(id);

            if (entity == null) {
                messages.add("No se encontró un estado con el ID especificado.");
                response.setMessages(messages);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            List<StateEntity> entityList = List.of(entity);
            List<StateDomain> domainList = StateEntityAdapter.getStateEntityAdapter().adaptTarget(entityList);
            List<StateDTO> dtoList = StateDTOAdapter.getStateDTOAdapter().adaptTarget(domainList);

            response.setData(dtoList);
            messages.add("El estado fue consultado satisfactoriamente.");
            response.setMessages(messages);

            return new GenerateResponse<StateResponse>().generateSuccessResponseWithData(response);

        } catch (final Exception exception) {
            messages.add("Error al consultar el estado. Por favor intente nuevamente.");
            exception.printStackTrace();
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
        */