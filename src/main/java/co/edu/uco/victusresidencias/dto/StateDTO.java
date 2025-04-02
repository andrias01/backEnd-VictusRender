package co.edu.uco.victusresidencias.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class StateDTO extends DomainDTO {
	
	private String name;
	private CountryDTO country;
	
	public StateDTO() {
		super(UUIDHelper.getDefaultAsString());
		setName(TextHelper.EMPTY);
		setCountry(CountryDTO.create());
		
	}
	public static final StateDTO create() {
		return new StateDTO();
	}
	
	public String getName() {
		return name;
	}

	public StateDTO setName(String name) {
		this.name = TextHelper.applyTrim(name);
		return this;
	}

	public StateDTO setId(final String id) {
		super.setIdentifier(id);
		return this;
	}
	@Override
	public String getId() {
		return super.getId();
	}
	public CountryDTO getCountry() {
		return country;
	}
	public StateDTO setCountry(final CountryDTO country) {
		this.country = ObjectHelper.getDefault(country, CountryDTO.create());
		return this;
	}
//	public static void main(String[] args) {
//		System.out.print(StateDTO.create().getCountry().getName());
//		System.out.print("\n");
//		System.out.print(StateDTO.create().getCountry().getId());
//	}
	

}