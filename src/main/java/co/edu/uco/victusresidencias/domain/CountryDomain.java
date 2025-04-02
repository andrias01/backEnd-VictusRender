package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class CountryDomain extends Domain {

	private String name;
	
	private CountryDomain(final UUID id, final String name) {
		super(id);
		setName(name);
		// TODO Auto-generated constructor stub
	}
	
	public static final CountryDomain create (
			final UUID id, 
			final String name){
		return new CountryDomain(id, name);
	}
	
	static final CountryDomain create() {
		return new CountryDomain(UUIDHelper.getDefault(), TextHelper.EMPTY);
	}
	
	public String getName() {
		return name;
	}

	private void setName(final String name) {
		this.name = TextHelper.applyTrim(name);
	}
	
	@Override
	public UUID getId() {
		return super.getId();
	}

}
