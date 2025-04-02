package co.edu.uco.victusresidencias.initializer.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import co.edu.uco.victusresidencias.initializer.domain.City;

@Repository  //base de datos
public class CityRepository {
	private final List<City> cities;
	
	public CityRepository() {
		this.cities = new ArrayList<>();
		
		this.cities.add(new City("hoal",159));
		this.cities.add(new City("Medellin",123));
		this.cities.add(new City("Bogota",456));
		this.cities.add(new City("Cali",789));
	}
	
	public City getById(long id) {
		return this.cities.stream()
				.filter(City -> City.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
	public City save(City city){
		if (this.getById(city.getId()) != null) {
			System.out.println("ya existe");
		}
		this.cities.add(city);
		return city;
	}
	
	public void delete(long id){
		City cityToDelete = this.getById(id);
		if (cityToDelete == null) {
			System.out.println("no existe");
		}
		
		this.cities.remove(cityToDelete);
	}
		
		
	public List<City> getCities(){
		return cities;
	}
}