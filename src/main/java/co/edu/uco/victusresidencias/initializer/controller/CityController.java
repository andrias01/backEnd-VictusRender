package co.edu.uco.victusresidencias.initializer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uco.victusresidencias.initializer.domain.City;
import co.edu.uco.victusresidencias.initializer.repository.CityRepository;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	private CityRepository cityRepository;
	
	
	@Autowired
	public CityController(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}


	@GetMapping("/all")
	public List<City> getAll(){
		return this.cityRepository.getCities();
	}
	
	@GetMapping("/{id}")
	public City getById(@PathVariable long id) {
		return this.cityRepository.getById(id);
	}
	
	@PostMapping("/new")
	public City saveCity(@RequestBody City city){
		
		return this.cityRepository.save(city);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCity(@PathVariable long id){
		
		this.cityRepository.delete(id);
	}
	

}
