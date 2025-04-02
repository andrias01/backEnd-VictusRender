package co.edu.uco.victusresidencias.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.CountryDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.CountryEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.country.impl.DeleteCountryFacadeImpl;
import co.edu.uco.victusresidencias.businesslogic.facade.country.impl.RegisterNewCountryFacadeImpl;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.CountryResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.domain.CountryDomain;
import co.edu.uco.victusresidencias.dto.CountryDTO;
import co.edu.uco.victusresidencias.entity.CountryEntity;

@RestController
@RequestMapping("/api/v1/countries")
public final class CountryController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public CountryController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    @GetMapping("/dummy")
    public CountryDTO getDummy() {
        return CountryDTO.create();
    }
   
    @PostMapping("/create")
    public ResponseEntity<GenericResponse> create(@RequestBody CountryDTO country) {
        var messages = new ArrayList<String>();
        
        try {
        	var registerNewCountryFacade = new RegisterNewCountryFacadeImpl();
            registerNewCountryFacade.execute(country);

            messages.add("El país se registró de forma satisfactoria");
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
            messages.add("Se ha presentado un problema inesperado al registrar el país.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable UUID id, @RequestBody CountryDTO country) {
        var responseWithData = new CountryResponse();
        var messages = new ArrayList<String>();

        try {
        	
        	
            // Paso 1: Obtener la entidad del país desde la base de datos
            CountryEntity existingCountryEntity = daoFactory.getCountryDAO().fingByID(id);
            
            
            if (existingCountryEntity == null) {
                messages.add("No se encontró un país con el ID especificado.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
            messages.add("El país "+existingCountryEntity.getName()+" se actualizó a .");
            // Asignar el ID al DTO para mantener la coherencia y adaptar a Entity
            country.setId(id.toString());
            CountryDomain countryDomain = CountryDTOAdapter.getCountryDTOAdapter().adaptSource(country);
            CountryEntity updatedCountryEntity = CountryEntityAdapter.getCountryEntityAdapter().adaptSource(countryDomain);

            // Actualizar el país en la base de datos
            daoFactory.getCountryDAO().update(updatedCountryEntity);
            
            List<CountryEntity> countryEntityList = List.of(updatedCountryEntity);
            List<CountryDomain> countryDomains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(countryEntityList);

            // Paso 3: Convertir el dominio a DTO y prepararlo para la respuesta
            List<CountryDTO> countryDTOs = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(countryDomains);

            // Preparar mensajes de éxito
            messages.add(updatedCountryEntity.getName()+" de manera satisfactoria.");
            responseWithData.setData(countryDTOs);
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.OK);

        } catch (final Exception exception) {
            // Manejar excepciones y generar respuesta de error
            messages.add("Error al actualizar el país. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        var messages = new ArrayList<String>();
        
        try {
        	var deleteCountryFacade = new DeleteCountryFacadeImpl();
        	deleteCountryFacade.execute(id);

            messages.add("El país se eliminó de manera satisfactoria");
            return GenerateResponse.generateSuccessResponse(messages);
        	

        } catch (final Exception exception) {
            messages.add("Error al eliminar el país. Por favor intente nuevamente.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<CountryResponse> retrieveAll() {
        // Inicializar la respuesta y los mensajes
        CountryResponse responseWithData = new CountryResponse();
        //List<String> messages = new ArrayList<>();
        var messages = new ArrayList<String>();
        
        try {
        	
            // Paso 1: Obtener todas las entidades de países desde la base de datos
            List<CountryEntity> countryEntities = daoFactory.getCountryDAO().findAll();
    
            // Paso 2: Adaptar las entidades a dominios
            List<CountryDomain> countryDomains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(countryEntities);
     
            // Paso 3: Convertir dominios a DTOs para la respuesta
            List<CountryDTO> countryDTOs = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(countryDomains);

            // Paso 4: Preparar la respuesta exitosa
        	responseWithData.setData(countryDTOs);
            messages.add("Los países fueron consultados satisfactoriamente.");
            responseWithData.setMessages(messages);

            // Retornar respuesta exitosa con datos
            return new ResponseEntity<>(responseWithData, HttpStatus.OK);

        } catch (Exception exception) {
            // Registro de la excepción (para propósitos de depuración)
            exception.printStackTrace();

            // Mensaje de error para el cliente
            messages.add("Error al consultar los países. Por favor intente nuevamente.");
            responseWithData.setMessages(messages);

            // Retornar respuesta con error interno del servidor
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> retrieveById(@PathVariable UUID id) {
        var responseWithData = new CountryResponse();
        var messages = new ArrayList<String>();
        
        try {
        	// Paso 1: Obtener la entidad del país desde la base de datos
            CountryEntity countryEntity = daoFactory.getCountryDAO().fingByID(id);

            if (countryEntity == null) {
                messages.add("No se encontró un país con el ID especificado.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
         // Paso 2: Adaptar la entidad a un dominio
            
            List<CountryEntity> countryEntityList = List.of(countryEntity);
            List<CountryDomain> countryDomains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(countryEntityList);

            // Paso 3: Convertir el dominio a DTO y prepararlo para la respuesta
            List<CountryDTO> countryDTOs = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(countryDomains);

            // Paso 4: Establecer los datos y mensajes en la respuesta
            responseWithData.setData(countryDTOs);
     
            messages.add("El país fue consultada satisfactoriamente.");
            responseWithData.setMessages(messages);
            
            return new GenerateResponse<CountryResponse>().generateSuccessResponseWithData(responseWithData);

        } catch (final Exception exception) {
            messages.add("Error al consultar el país. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
	public ResponseEntity<CountryResponse> validateLogin(String username, String password) {
		var responseWithData = new CountryResponse();
        var messages = new ArrayList<String>();
        
        try {
        	UUID idConverted = UUIDHelper.convertToUUID(password);
        	// Paso 1: Obtener la entidad del país desde la base de datos
            CountryEntity countryEntity = daoFactory.getCountryDAO().fingByID(idConverted);

            if (countryEntity == null) {
                messages.add("No se encontró un país con esa clave.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
         // Paso 2: Adaptar la entidad a un dominio
            List<CountryEntity> countryEntityList = Arrays.asList(countryEntity);

            List<CountryDomain> countryDomains = CountryEntityAdapter.getCountryEntityAdapter().adaptTarget(countryEntityList);

            // Paso 3: Convertir el dominio a DTO y prepararlo para la respuesta
            List<CountryDTO> countryDTOs = CountryDTOAdapter.getCountryDTOAdapter().adaptTarget(countryDomains);

            // Paso 4: Establecer los datos y mensajes en la respuesta
            responseWithData.setData(countryDTOs);
     
            messages.add("El país fue consultada satisfactoriamente.");
            responseWithData.setMessages(messages);
            
            return new GenerateResponse<CountryResponse>().generateSuccessResponseWithData(responseWithData);

        } catch (final Exception exception) {
            messages.add("Error al consultar el país. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
	}
}
