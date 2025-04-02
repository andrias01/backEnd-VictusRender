package co.edu.uco.victusresidencias.businesslogic.adapter.dto;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.PropertyDomain;
import co.edu.uco.victusresidencias.dto.PropertyDTO;

public class PropertyDTOAdapter implements Adapter<PropertyDomain, PropertyDTO> {

    private static final Adapter<PropertyDomain, PropertyDTO> instance = new PropertyDTOAdapter();

    private PropertyDTOAdapter() {
    }

    public static Adapter<PropertyDomain, PropertyDTO> getPropertyDTOAdapter() {
        return instance;
    }

    @Override
    public PropertyDomain adaptSource(PropertyDTO data) {
        // Si el DTO es nulo, usamos un DTO por defecto
        var dtoToAdapt = ObjectHelper.getDefault(data, PropertyDTO.create());

        // Convertimos el DTO a Domain
        return PropertyDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getPropertyType(),
                dtoToAdapt.getHousingNumber(),
                PropertyZoneDTOAdapter.getPropertyZoneDTOAdapter().adaptSource(dtoToAdapt.getPropertyZone())
        );
    }

    @Override
    public PropertyDTO adaptTarget(PropertyDomain data) {
        // Si el Domain es nulo, usamos un Domain por defecto
        var domainToAdapt = ObjectHelper.getDefault(data,createDefault.PROPERTY);
        
        return PropertyDTO.create()
        		.setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
        		.setHousingNumber(domainToAdapt.getPropertyNumber())
        		.setPropertyZone(PropertyZoneDTOAdapter.getPropertyZoneDTOAdapter().adaptTarget(domainToAdapt.getPropertyZone()));
        
    }
    @Override
	public List<PropertyDTO> adaptTarget(final List<PropertyDomain> data) {
		
		var results = new ArrayList<PropertyDTO>();
		
		for (PropertyDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
}
