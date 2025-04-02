package co.edu.uco.victusresidencias.initializer.domain;

public class City {
	private String nombre;
	private long id;
	
	
	public City(String nombre, long id) {
		this.nombre = nombre;
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
