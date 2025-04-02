package co.edu.uco.victusresidencias.initializer.controller;

import co.edu.uco.victusresidencias.data.dao.impl.postgresql.PostgreSqlDAOFactory;
import co.edu.uco.victusresidencias.entity.CountryEntity;
import co.edu.uco.victusresidencias.crosscutting.exceptions.DataVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final PostgreSqlDAOFactory daoFactory;

    @Autowired
    public CountryController() {
        this.daoFactory = new PostgreSqlDAOFactory();
    }

    @GetMapping("/all")
    public List<CountryEntity> findAll() {
        // Llamamos a findAll del DAO
        return daoFactory.getCountryDAO().findAll();
    }

    @GetMapping("/{id}")
    public CountryEntity findById(@PathVariable UUID id) {
    	CountryEntity country = daoFactory.getCountryDAO().fingByID(id);
        if (country == null) {
        	var userMessage = "Se ha presentado un problema tratando de llevar a cabo el registro de la información del nuevo país. Por favor intente de nuevo y si el problema persiste reporte la novedad...";
			var technicalMessage = "Se ha presentado un problema al tratar de registrar la informaciòn del nuevo país en la base de datos SQL Server. Por favor valide el log de errores para encontrar mayores detalles del problema presentado...";
 
			throw DataVictusResidenciasException.crear(userMessage, technicalMessage);
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found with ID: " + id);
        }
        return country;
    }

    @PostMapping("/new")
    public String createCountry(@RequestBody CountryEntity countryEntity) {
        // Asignar un nuevo UUID si es necesario
        if (UUIDHelper.isDefault(countryEntity.getId())) {
            countryEntity.setId(UUID.randomUUID());
        }
        
        // Crear el país usando el DAO
        daoFactory.getCountryDAO().create(countryEntity);
        return "Country created successfully with name: " + countryEntity.getName();
    }

    @DeleteMapping("/{id}")
    public String deleteCountry(@PathVariable UUID id) {
        // Eliminar país usando el DAO
        daoFactory.getCountryDAO().delete(id);
        return "Country deleted successfully with id: " + id;
    }

    @PutMapping("/{id}")
    public String updateCountry(@PathVariable UUID id, @RequestBody CountryEntity countryEntity) {
        // Actualizar datos del país
        countryEntity.setId(id);
        daoFactory.getCountryDAO().update(countryEntity);
        return "Country updated successfully with id: " + id;
    }
}
