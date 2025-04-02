package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.ResidentialComplexDomain;
import co.edu.uco.victusresidencias.entity.ResidentialComplexEntity;

public class ResidentialComplexEntityAdapter implements Adapter<ResidentialComplexEntity,ResidentialComplexDomain> {
	private static final Adapter<ResidentialComplexEntity,ResidentialComplexDomain> instance = new ResidentialComplexEntityAdapter();
	
	private ResidentialComplexEntityAdapter() {
		
	}
	
	public static Adapter<ResidentialComplexEntity,ResidentialComplexDomain> getResidentialComplexEntityAdapter(){
		return instance;
	}

	@Override
	public ResidentialComplexEntity adaptSource(ResidentialComplexDomain data) {
		// Ensure data is not null, use a default value if it is
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.RESIDENTIAL_COMPLEX);

        // Convert StateDomain to StateEntity
        var entityToAdapt = new ResidentialComplexEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setAddress(domainToAdapt.getAddress());
        entityToAdapt.setCity(CityEntityAdapter.getCityEntityAdapter().adaptSource(domainToAdapt.getCity()));
        entityToAdapt.setContactReception(domainToAdapt.getReceptionContact());
        entityToAdapt.setDescription(domainToAdapt.getDescription());
        entityToAdapt.setAdministrator(AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptSource(domainToAdapt.getAdministrator()));

        return entityToAdapt;
	}

	@Override
	public ResidentialComplexDomain adaptTarget(ResidentialComplexEntity data) {
		// Ensure data is not null, use a default value if it is
        var entityToAdapt = ObjectHelper.getDefault(data, new ResidentialComplexEntity());

        // Convert StateEntity to StateDomain
        return ResidentialComplexDomain.create(
            entityToAdapt.getId(),
            entityToAdapt.getName(),
            entityToAdapt.getAddress(),
            CityEntityAdapter.getCityEntityAdapter().adaptTarget(entityToAdapt.getCity()),
            entityToAdapt.getContactReception(),
            entityToAdapt.getDescription(),
            AdministratorEntityAdapter.getAdministratorEntityAdapter().adaptTarget(entityToAdapt.getAdministrator())// Adapt CountryEntity to CountryDomain
        );
	}

	@Override
	public List<ResidentialComplexDomain> adaptTarget(final List<ResidentialComplexEntity> data) {
		
		var results = new ArrayList<ResidentialComplexDomain>();
		
		for (ResidentialComplexEntity entity : data) {
			results.add(adaptTarget(entity));
		}
		
		return results;
	}
}
