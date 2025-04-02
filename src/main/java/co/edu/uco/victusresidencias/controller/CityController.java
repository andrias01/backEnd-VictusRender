package co.edu.uco.victusresidencias.controller;

import java.util.ArrayList;



import java.util.List;
import java.util.UUID;

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
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.domain.CityDomain;
import co.edu.uco.victusresidencias.dto.CityDTO;
import co.edu.uco.victusresidencias.entity.CityEntity;

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
        	var registerNewCityFacade = new RegisterNewCityFacadeImpl();
            registerNewCityFacade.execute(city);

            CityDomain cityDomain = CityDTOAdapter.getCityDTOAdapter().adaptSource(city);
            CityEntity cityEntity = CityEntityAdapter.getCityEntityAdapter().adaptSource(cityDomain);
            daoFactory.getCityDAO().create(cityEntity);

            messages.add("La ciudad se registró de forma satisfactoria");
            return GenerateResponse.generateSuccessResponse(messages);

        } catch (final VictusResidenciasException exception) {
            messages.add(exception.getUserMessage());
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);

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

    @GetMapping
    public ResponseEntity<CityResponse> retrieveAll() {
        var responseWithData = new CityResponse();
        var messages = new ArrayList<String>();
        
        try {
        	// Obtención de todas las ciudades desde la base de datos en formato CityEntity
            List<CityEntity> cityEntities = daoFactory.getCityDAO().findAll();

            // Conversión de CityEntity a CityDomain utilizando el adaptador
            List<CityDomain> cityDomains = CityEntityAdapter.getCityEntityAdapter().adaptTarget(cityEntities);

            // Conversión de CityDomain a CityDTO para la respuesta final
            List<CityDTO> cityDTOs = CityDTOAdapter.getCityDTOAdapter().adaptTarget(cityDomains);

            // Preparación de respuesta exitosa
            responseWithData.setData(cityDTOs);
            messages.add("Las ciudades fueron consultadas satisfactoriamente.");
            responseWithData.setMessages(messages);

            return new GenerateResponse<CityResponse>().generateSuccessResponseWithData(responseWithData);
//            // Llamada a findAll para obtener todas las ciudades en SQL Server
//            List<CityEntity> cities = daoFactory.getCityDAO().findAll();
//            List<CityDTO> cityDTOs = cities.stream().map(CityDTO::fromEntity).toList();
//            
//            responseWithData.setData(cityDTOs);
//            messages.add("Las ciudades fueron consultadas satisfactoriamente");
//            responseWithData.setMessages(messages);
//            return new GenerateResponse<CityResponse>().generateSuccessResponseWithData(responseWithData);
        }catch (final Exception exception) {
            messages.add("Error al consultar la ciudad. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> retrieveById(@PathVariable UUID id) {
        var responseWithData = new CityResponse();
        var messages = new ArrayList<String>();
        
        try {
            CityEntity cityEntity = daoFactory.getCityDAO().fingByID(id);

            if (cityEntity == null) {
                messages.add("No se encontró una ciudad con el ID especificado.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }

            responseWithData.setData(List.of(CityDTO.create()));
            messages.add("La ciudad fue consultada satisfactoriamente.");
            responseWithData.setMessages(messages);
            
            return new GenerateResponse<CityResponse>().generateSuccessResponseWithData(responseWithData);

        } catch (final Exception exception) {
            messages.add("Error al consultar la ciudad. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
