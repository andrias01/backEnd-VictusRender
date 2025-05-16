package co.edu.uco.victusresidencias.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.edu.uco.victusresidencias.businesslogic.adapter.dto.StateDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.StateEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.state.impl.DeleteDepartamentoFacadeImpl;
import co.edu.uco.victusresidencias.businesslogic.facade.state.impl.RegisterNewDepartamentoFacadeImpl;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.DepartamentoResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;

import co.edu.uco.victusresidencias.dto.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://tangerine-profiterole-824fd8.netlify.app")
@RestController
@RequestMapping("/api/v1/departamentos")
public final class DepartamentoController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public DepartamentoController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    // ------------------ Métodos CRUD ------------------

    @PostMapping("/crear")
    public ResponseEntity<GenericResponse> crear(@RequestBody StateDTO departamento) {
        var mensajes = new ArrayList<String>();

        try {
            new RegisterNewDepartamentoFacadeImpl().execute(departamento);
            mensajes.add("El departamento se registró de forma satisfactoria.");
            return GenerateResponse.generateSuccessResponse(mensajes);

        } catch (UcoApplicationException excepcion) {
            mensajes.add(excepcion.getUserMessage());
            excepcion.printStackTrace();
            return GenerateResponse.generateFailedResponse(mensajes);

        } catch (Exception excepcion) {
            mensajes.add("Se ha presentado un problema inesperado al registrar el departamento.");
            excepcion.printStackTrace();
            return GenerateResponse.generateFailedResponse(mensajes);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<DepartamentoResponse> consultarTodos() {
        var respuesta = new DepartamentoResponse();
        var mensajes = new ArrayList<String>();

        try {
            var entidades = daoFactory.getStateDAO().findAll();
            var dominios = StateEntityAdapter.getStateEntityAdapter().adaptTarget(entidades);
            var dtos = StateDTOAdapter.getStateDTOAdapter().adaptTarget(dominios);

            respuesta.setData(dtos);
            mensajes.add("Los departamentos fueron consultados satisfactoriamente.");
            respuesta.setMessages(mensajes);

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            mensajes.add("Error al consultar los departamentos. Por favor intente nuevamente.");
            respuesta.setMessages(mensajes);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> consultarPorId(@PathVariable UUID id) {
        var respuesta = new DepartamentoResponse();
        var mensajes = new ArrayList<String>();

        try {
            var entidad = daoFactory.getStateDAO().fingByID(id);

            if (entidad == null) {
                mensajes.add("No se encontró un departamento con el ID especificado.");
                respuesta.setMessages(mensajes);
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }

            var dominios = StateEntityAdapter.getStateEntityAdapter().adaptTarget(List.of(entidad));
            var dtos = StateDTOAdapter.getStateDTOAdapter().adaptTarget(dominios);

            respuesta.setData(dtos);
            mensajes.add("El departamento fue consultado satisfactoriamente.");
            respuesta.setMessages(mensajes);

            return new GenerateResponse<DepartamentoResponse>().generateSuccessResponseWithData(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            mensajes.add("Error al consultar el departamento. Por favor intente nuevamente.");
            respuesta.setMessages(mensajes);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> actualizar(@PathVariable UUID id, @RequestBody StateDTO departamento) {
        var respuesta = new DepartamentoResponse();
        var mensajes = new ArrayList<String>();

        try {
            var existente = daoFactory.getStateDAO().fingByID(id);

            if (existente == null) {
                mensajes.add("No se encontró un departamento con el ID especificado.");
                respuesta.setMessages(mensajes);
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }

            mensajes.add("El departamento " + existente.getName() + " se actualizó.");

            departamento.setId(id.toString());
            var dominio = StateDTOAdapter.getStateDTOAdapter().adaptSource(departamento);
            var entidadActualizada = StateEntityAdapter.getStateEntityAdapter().adaptSource(dominio);

            daoFactory.getStateDAO().update(entidadActualizada);

            var dominios = StateEntityAdapter.getStateEntityAdapter().adaptTarget(List.of(entidadActualizada));
            var dtos = StateDTOAdapter.getStateDTOAdapter().adaptTarget(dominios);

            mensajes.add(entidadActualizada.getName() + " actualizado satisfactoriamente.");
            respuesta.setData(dtos);
            respuesta.setMessages(mensajes);

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            mensajes.add("Error al actualizar el departamento. Por favor intente nuevamente.");
            respuesta.setMessages(mensajes);
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> eliminar(@PathVariable UUID id) {
        var mensajes = new ArrayList<String>();

        try {
            new DeleteDepartamentoFacadeImpl().execute(id);
            mensajes.add("El departamento se eliminó de manera satisfactoria.");
            return GenerateResponse.generateSuccessResponse(mensajes);

        } catch (Exception e) {
            e.printStackTrace();
            mensajes.add("Error al eliminar el departamento. Por favor intente nuevamente.");
            return GenerateResponse.generateFailedResponse(mensajes);
        }
    }

    // ------------------ Métodos Adicionales ------------------

    @GetMapping("/dummy")
    public StateDTO getDummy() {
        return StateDTO.create();
    }

    // Si tienes Jackson en tu proyecto


    public static void main(String[] args) {
        try {
            PostgreSqlDAOFactory factoria = new PostgreSqlDAOFactory();
            var entidades = factoria.getStateDAO().findAll();
            var dominios = StateEntityAdapter.getStateEntityAdapter().adaptTarget(entidades);
            var dtos = StateDTOAdapter.getStateDTOAdapter().adaptTarget(dominios);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entidades);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}