package co.edu.uco.victusresidencias.domain;

import java.util.UUID;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

public class StateDomain extends Domain {

	private String name;
	private CountryDomain country;
	
	private StateDomain(final UUID id, final String name,final CountryDomain country) {
		super(id);
		setName(name);
		setCountry(country);
	}
	
	public static final StateDomain create (final UUID id, final String name,final CountryDomain country){
		return new StateDomain(id, name, country);
	}
	
	static final StateDomain create() {
		return new StateDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, CountryDomain.create());
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

	public CountryDomain getCountry() {
		return country;
	}

	private void setCountry(final CountryDomain country) {
		this.country = ObjectHelper.getDefault(country, CountryDomain.create());
	}
	
	

}
