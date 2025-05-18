package co.edu.uco.victusresidencias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CityDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CityEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.city.impl.RegisterNewCityFacadeImpl;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.CityResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.dto.CityDTO;
import co.edu.uco.victusresidencias.entity.CityEntity;
import java.util.*;

@CrossOrigin(origins = "https://tangerine-profiterole-824fd8.netlify.app")
@RestController
@RequestMapping("/api/v1/cities")
public final class CityController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public CityController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    @GetMapping("/dummy")
    public CityDTO getDummy() {
        return CityDTO.create();
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> create(@RequestBody CityDTO city) {
        var messages = new ArrayList<String>();
        
        try {
            new RegisterNewCityFacadeImpl().execute(city);
            messages.add("La ciudad se registró de forma satisfactoria");
            return GenerateResponse.generateSuccessResponse(messages);

        }catch (final UcoApplicationException exception) {
            messages.add(exception.getUserMessage());
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);

        } catch (final Exception exception) {
            messages.add("Se ha presentado un problema inesperado al registrar la ciudad.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable UUID id, @RequestBody CityDTO city) {
        var messages = new ArrayList<String>();

        try {
            // Verificar si la ciudad con el ID especificado existe en la base de datos
            var cityDAO = daoFactory.getCityDAO();
            CityEntity existingCity = cityDAO.fingByID(id);

            if (existingCity == null) {
                messages.add("No se encontró una ciudad con el ID especificado.");
                return GenerateResponse.generateFailedResponse(messages);
            }

            // Asigna el ID al DTO y adapta el DTO a Entity
            city.setId(id.toString());
            CityDomain cityDomain = CityDTOAdapter.getCityDTOAdapter().adaptSource(city);
            CityEntity cityEntity = CityEntityAdapter.getCityEntityAdapter().adaptSource(cityDomain);

            // Actualiza la ciudad en la base de datos
            cityDAO.update(cityEntity);

            messages.add("La ciudad se actualizó de manera satisfactoria.");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (final Exception exception) {
            messages.add("Error al actualizar la ciudad. Por favor intente nuevamente.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        var messages = new ArrayList<String>();
        
        try {
            daoFactory.getCityDAO().delete(id);
            messages.add("La ciudad se eliminó de manera satisfactoria");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (final Exception exception) {
            messages.add("Error al eliminar la ciudad. Por favor intente nuevamente.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @GetMapping("/todas")
    public ResponseEntity<CityResponse> retrieveAll() {
        var response = new CityResponse();
        var messages = new ArrayList<String>();
        
        try {
        	var entidades = daoFactory.getCityDAO().findAll();
            var dominios = CityEntityAdapter.getCityEntityAdapter().adaptTarget(entidades);
            var dtos = CityDTOAdapter.getCityDTOAdapter().adaptTarget(dominios);

            // Preparación de respuesta exitosa
            response.setData(dtos);
            messages.add("Las ciudades fueron consultadas satisfactoriamente.");
            response.setMessages(messages);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (final Exception e) {
            e.printStackTrace();
            messages.add("Error al consultar las ciudades. Por favor intente nuevamente.");
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> retrieveById(@PathVariable UUID id) {
        var response = new CityResponse();
        var messages = new ArrayList<String>();
        
        try {
            CityEntity entidades = daoFactory.getCityDAO().fingByID(id);

            if (entidades == null) {
                messages.add("No se encontró una ciudad con el ID especificado.");
                response.setMessages(messages);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            var dominios = CityEntityAdapter.getCityEntityAdapter().adaptTarget(List.of(entidades));
            var dtos = CityDTOAdapter.getCityDTOAdapter().adaptTarget(dominios);

//            response.setData(List.of(CityDTO.create()));
            response.setData(dtos);
            messages.add("La ciudad fue consultada satisfactoriamente.");
            response.setMessages(messages);
            
            return new GenerateResponse<CityResponse>().generateSuccessResponseWithData(response);

        } catch (final Exception exception) {
            messages.add("Error al consultar la ciudad. Por favor intente nuevamente.");
            exception.printStackTrace();
            response.setMessages(messages);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
