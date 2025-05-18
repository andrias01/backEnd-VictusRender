package co.edu.uco.victusresidencias.controller;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.impl.DeleteCountryFacadeImpl;
import co.edu.uco.victusresidencias.businesslogic.facade.country.impl.RegisterNewCountryFacadeImpl;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.CountryResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.dto.CountryDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://tangerine-profiterole-824fd8.netlify.app")
@RestController
@RequestMapping("/api/v1/countries")
public final class CountryController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public CountryController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    // ------------------ Métodos CRUD ------------------

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> create(@RequestBody CountryDTO country) {
        var messages = new ArrayList<String>();

        try {
            new RegisterNewCountryFacadeImpl().execute(country);
            messages.add("El país se registró de forma satisfactoria.");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (UcoApplicationException exception) {
            messages.add(exception.getUserMessage());
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);

        } catch (Exception exception) {
            messages.add("Se ha presentado un problema inesperado al registrar el país.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<CountryResponse> retrieveAll() {
        var response = new CountryResponse();
        var messages = new ArrayList<String>();

        try {
            var entities = daoFactory.getCountryDAO().findAll();
            var domains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(entities);
            var dtos = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(domains);

            response.setData(dtos);
            messages.add("Los países fueron consultados satisfactoriamente.");
            response.setMessages(messages);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            messages.add("Error al consultar los países. Por favor intente nuevamente.");
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> retrieveById(@PathVariable UUID id) {
        var response = new CountryResponse();
        var messages = new ArrayList<String>();

        try {
            var entity = daoFactory.getCountryDAO().fingByID(id);

            if (entity == null) {
                messages.add("No se encontró un país con el ID especificado.");
                response.setMessages(messages);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            var domains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(List.of(entity));
            var dtos = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(domains);

            response.setData(dtos);
            messages.add("El país fue consultado satisfactoriamente.");
            response.setMessages(messages);

            return new GenerateResponse<CountryResponse>().generateSuccessResponseWithData(response);

        } catch (Exception e) {
            e.printStackTrace();
            messages.add("Error al consultar el país. Por favor intente nuevamente.");
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable UUID id, @RequestBody CountryDTO country) {
        var response = new CountryResponse();
        var messages = new ArrayList<String>();

        try {
            var existing = daoFactory.getCountryDAO().fingByID(id);

            if (existing == null) {
                messages.add("No se encontró un país con el ID especificado.");
                response.setMessages(messages);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            messages.add("El país " + existing.getName() + " se actualizó.");

            country.setId(id.toString());
            var domain = CountryDTOAdapter.getCountryDTOAdapter().adaptSource(country);
            var updatedEntity = CountryEntityAdapter.getCountryEntityAdapter().adaptSource(domain);

            daoFactory.getCountryDAO().update(updatedEntity);

            var domains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(List.of(updatedEntity));
            var dtos = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(domains);

            messages.add(updatedEntity.getName() + " actualizado satisfactoriamente.");
            response.setData(dtos);
            response.setMessages(messages);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            messages.add("Error al actualizar el país. Por favor intente nuevamente.");
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        var messages = new ArrayList<String>();

        try {
            new DeleteCountryFacadeImpl().execute(id);
            messages.add("El país se eliminó de manera satisfactoria.");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (Exception e) {
            e.printStackTrace();
            messages.add("Error al eliminar el país. Por favor intente nuevamente.");
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    // ------------------ Métodos Adicionales ------------------

    @GetMapping("/dummy")
    public CountryDTO getDummy() {
        return CountryDTO.create();
    }

}
