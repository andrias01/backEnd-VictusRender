package co.edu.uco.victusresidencias.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.victusresidencias.businesslogic.adapter.dto.AdministratorDTOAdapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.entity.AdministratorEntityAdapter;
import co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl.DeleteAdminstratorFacadeImpl;
import co.edu.uco.victusresidencias.businesslogic.facade.administrator.impl.RegisterNewAdministratorFacadeImpl;
import co.edu.uco.victusresidencias.controller.response.GenerateResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.AdministratorResponse;
import co.edu.uco.victusresidencias.controller.response.concrete.GenericResponse;
import co.edu.uco.victusresidencias.crosscutting.exceptions.UcoApplicationException;
import co.edu.uco.victusresidencias.crosscutting.exceptions.VictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.dto.AdministratorDTO;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;

// @CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = "https://tangerine-profiterole-824fd8.netlify.app")
@RestController
@RequestMapping("/api/v1/administrator")
//@CrossOrigin(origins = {"http://localhost:4200"})
public final class AdministratorController {

	private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public AdministratorController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    @GetMapping("/dummy")
    public AdministratorDTO getDummy() {
        return AdministratorDTO.create();
    }
   
    @PostMapping("/create")
    public ResponseEntity<GenericResponse> create(@RequestBody AdministratorDTO admin) {
        var messages = new ArrayList<String>();
        
        try {
        	var registerNewAdministratorFacade = new RegisterNewAdministratorFacadeImpl();
            registerNewAdministratorFacade.execute(admin);

            messages.add("El admin se registró de forma satisfactoria");
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
    public ResponseEntity<AdministratorResponse> update(@PathVariable UUID id, @RequestBody AdministratorDTO admin) {
        var responseWithData = new AdministratorResponse();
        var messages = new ArrayList<String>();

        try {
            // Paso 1: Obtener el administrador actual de la base de datos
            AdministratorEntity existingAdministratorEntity = daoFactory.getAdministratorDAO().fingByID(id);

            if (existingAdministratorEntity == null) {
                messages.add("No se encontró un administrador con el ID especificado.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
            // Paso 2: Fusionar datos nuevos con los existentes
            if (admin.getName() != "") existingAdministratorEntity.setName(admin.getName());
            if (admin.getLastName() != "") existingAdministratorEntity.setLastName(admin.getLastName());
            if (admin.getIdType() != "") existingAdministratorEntity.setIdType(admin.getIdType());
            if (admin.getIdNumber() != "") existingAdministratorEntity.setIdNumber(admin.getIdNumber());
            if (admin.getContactNumber() != "") existingAdministratorEntity.setContactNumber(admin.getContactNumber());
            if (admin.getEmail() != "") existingAdministratorEntity.setEmail(admin.getEmail());
            if (admin.getPassword() != "") existingAdministratorEntity.setPassword(admin.getPassword());

            // Paso 3: Guardar los cambios en la base de datos
            daoFactory.getAdministratorDAO().update(existingAdministratorEntity);

            // Paso 4: Convertir la entidad actualizada a DTO para la respuesta
            List<AdministratorEntity> adminEntityList = List.of(existingAdministratorEntity);
            List<AdministratorDomain> adminDomains = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptTarget(adminEntityList);
            List<AdministratorDTO> adminsDTOs = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptTarget(adminDomains);

            // Mensaje de éxito
            messages.add("El administrador " + existingAdministratorEntity.getName() + " se actualizó correctamente.");
            responseWithData.setData(adminsDTOs);
            responseWithData.setMessages(messages);

            return new ResponseEntity<>(responseWithData, HttpStatus.OK);

        } catch (final Exception exception) {
            // Manejo de errores
            messages.add("Error al actualizar el administrador. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable UUID id) {
        var messages = new ArrayList<String>();
        
        try {
        	var deleteAdminFacade = new DeleteAdminstratorFacadeImpl();
        	deleteAdminFacade.execute(id);

            messages.add("El admin se eliminó de manera satisfactoria");
            return GenerateResponse.generateSuccessResponse(messages);
        	

        } catch (final Exception exception) {
            messages.add("Error al eliminar el admin. Por favor intente nuevamente.");
            exception.printStackTrace();
            return GenerateResponse.generateFailedResponse(messages);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<AdministratorResponse> retrieveAll() {
        // Inicializar la respuesta y los mensajes
    	AdministratorResponse responseWithData = new AdministratorResponse();
        //List<String> messages = new ArrayList<>();
        var messages = new ArrayList<String>();
        
        try {
        	
            // Paso 1: Obtener todas las entidades de administradores desde la base de datos
            List<AdministratorEntity> adminEntities = daoFactory.getAdministratorDAO().findAll();
    
            // Paso 2: Adaptar las entidades a dominios
            List<AdministratorDomain> adminDomains = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptTarget(adminEntities);
     
            // Paso 3: Convertir dominios a DTOs para la respuesta
            List<AdministratorDTO> adminDTOs = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptTarget(adminDomains);

            // Paso 4: Preparar la respuesta exitosa
        	responseWithData.setData(adminDTOs);
            messages.add("Los administradores fueron consultados satisfactoriamente.");
            responseWithData.setMessages(messages);

            // Retornar respuesta exitosa con datos
            return new ResponseEntity<>(responseWithData, HttpStatus.OK);

        } catch (Exception exception) {
            // Registro de la excepción (para propósitos de depuración)
            exception.printStackTrace();

            // Mensaje de error para el cliente
            messages.add("Error al consultar los administradores. Por favor intente nuevamente.");
            responseWithData.setMessages(messages);

            // Retornar respuesta con error interno del servidor
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorResponse> retrieveById(@PathVariable UUID id) {
        var responseWithData = new AdministratorResponse();
        var messages = new ArrayList<String>();
        
        try {
        	// Paso 1: Obtener la entidad del país desde la base de datos
            AdministratorEntity adminEntity = daoFactory.getAdministratorDAO().fingByID(id);

            if (adminEntity == null) {
                messages.add("No se encontró un admin con el ID especificado.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
            List<AdministratorEntity> adminEntityList = Arrays.asList(adminEntity);

         // Paso 2: Adaptar la entidad a un dominio
           // List<AdministratorEntity> adminEntityList = List.of(adminEntity);
            List<AdministratorDomain> adminDomains = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptTarget(adminEntityList);

            // Paso 3: Convertir el dominio a DTO y prepararlo para la respuesta
            List<AdministratorDTO> administratorDTOs = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptTarget(adminDomains);

            // Paso 4: Establecer los datos y mensajes en la respuesta
            responseWithData.setData(administratorDTOs);
     
            messages.add("El admin fue consultada satisfactoriamente.");
            responseWithData.setMessages(messages);
            
            return new GenerateResponse<AdministratorResponse>().generateSuccessResponseWithData(responseWithData);

        } catch (final Exception exception) {
            messages.add("Error al consultar el admin. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
	public ResponseEntity<AdministratorResponse> validateLogin(String username, String password) {
		var responseWithData = new AdministratorResponse();
        var messages = new ArrayList<String>();
        
        try {
        	UUID idConverted = UUIDHelper.convertToUUID(password);
        	// Paso 1: Obtener la entidad del país desde la base de datos
            AdministratorEntity adminEntity = daoFactory.getAdministratorDAO().fingByID(idConverted);

            if (adminEntity == null) {
                messages.add("No se encontró un admin con esa clave.");
                responseWithData.setMessages(messages);
                return new ResponseEntity<>(responseWithData, HttpStatus.NOT_FOUND);
            }
         // Paso 2: Adaptar la entidad a un dominio
            List<AdministratorEntity> adminEntityList = List.of(adminEntity);
            List<AdministratorDomain> adminDomains = AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptTarget(adminEntityList);

            // Paso 3: Convertir el dominio a DTO y prepararlo para la respuesta
            List<AdministratorDTO> adminDTOs = AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptTarget(adminDomains);

            // Paso 4: Establecer los datos y mensajes en la respuesta
            responseWithData.setData(adminDTOs);
     
            messages.add("El admin fue consultada satisfactoriamente.");
            responseWithData.setMessages(messages);
            
            return new GenerateResponse<AdministratorResponse>().generateSuccessResponseWithData(responseWithData);

        } catch (final Exception exception) {
            messages.add("Error al consultar el admin. Por favor intente nuevamente.");
            exception.printStackTrace();
            responseWithData.setMessages(messages);
            return new ResponseEntity<>(responseWithData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
	}
}
