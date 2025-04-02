package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;
import co.edu.uco.victusresidencias.entity.AdministratorEntity;

public class AdministratorEntityAdapter implements Adapter<AdministratorEntity,AdministratorDomain>{
private static final Adapter<AdministratorEntity,AdministratorDomain> instance = new AdministratorEntityAdapter();
	
	private AdministratorEntityAdapter() {
		
	}
	public static Adapter<AdministratorEntity,AdministratorDomain> getAdministratorEntityAdapter(){
		return instance;
	}
	@Override
	public AdministratorEntity adaptSource(AdministratorDomain data) {
		// Ensure data is not null, use a default value if it is
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.ADMINISTRATOR);

        // Convert CityDomain to CityEntity
        var entityToAdapt = new AdministratorEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setContactNumber(domainToAdapt.getContactNumber());
        entityToAdapt.setLastName(domainToAdapt.getLastName());
        entityToAdapt.setEmail(domainToAdapt.getEmail());
        entityToAdapt.setIdNumber(domainToAdapt.getIdNumber());
        entityToAdapt.setIdType(domainToAdapt.getIdType());
        entityToAdapt.setPassword(domainToAdapt.getPassword());

        return entityToAdapt;
	}
	@Override
	public AdministratorDomain adaptTarget(AdministratorEntity data) {
		// Ensure data is not null, use a default value if it is
        var entityToAdapt = ObjectHelper.getDefault(data, new AdministratorEntity());

        // Convert CityEntity to CityDomain
        return AdministratorDomain.create(
            entityToAdapt.getId(),
            entityToAdapt.getName(),
            entityToAdapt.getLastName(),
            entityToAdapt.getIdType(),
            entityToAdapt.getIdNumber(),
            entityToAdapt.getContactNumber(),
            entityToAdapt.getEmail(),
            entityToAdapt.getPassword());
	}
	@Override
	public List<AdministratorDomain> adaptTarget(final List<AdministratorEntity> data) {
		
		var results = new ArrayList<AdministratorDomain>();
		
		for (AdministratorEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}

}
