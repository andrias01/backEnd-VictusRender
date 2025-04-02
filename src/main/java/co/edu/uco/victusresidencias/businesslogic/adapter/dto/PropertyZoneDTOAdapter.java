package co.edu.uco.victusresidencias.businesslogic.adapter.dto;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.PropertyZoneDomain;
import co.edu.uco.victusresidencias.dto.PropertyZoneDTO;

public class PropertyZoneDTOAdapter implements Adapter<PropertyZoneDomain, PropertyZoneDTO> {

    private static final Adapter<PropertyZoneDomain, PropertyZoneDTO> instance = new PropertyZoneDTOAdapter();

    private PropertyZoneDTOAdapter() {
    }

    public static Adapter<PropertyZoneDomain, PropertyZoneDTO> getPropertyZoneDTOAdapter() {
        return instance;
    }

    @Override
    public PropertyZoneDomain adaptSource(PropertyZoneDTO data) {
        // Usar un DTO por defecto si data es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, PropertyZoneDTO.create());

        // Convertimos el DTO a Domain
        return PropertyZoneDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getPropertyZoneType(),
                dtoToAdapt.getPropertyZoneNumber(),
                ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter().adaptSource(dtoToAdapt.getResidentialComplex())
        );
    }

    @Override
    public PropertyZoneDTO adaptTarget(PropertyZoneDomain data) {
        // Usar un Domain por defecto si data es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.PROPERTY_ZONE);

        // Convertimos el Domain a DTO
        return PropertyZoneDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setPropertyZoneType(domainToAdapt.getPropertyZoneType())
                .setPropertyZoneNumber(domainToAdapt.getPropertyZoneNumber())
                .setResidentialComplex(ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter().adaptTarget(domainToAdapt.getResidentialComplex()));
    }
    @Override
	public List<PropertyZoneDTO> adaptTarget(final List<PropertyZoneDomain> data) {
		
		var results = new ArrayList<PropertyZoneDTO>();
		
		for (PropertyZoneDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
}
