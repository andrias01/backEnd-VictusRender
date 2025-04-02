package co.edu.uco.victusresidencias.businesslogic.adapter.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.domain.ResidentialComplexDomain;
import co.edu.uco.victusresidencias.dto.ResidentialComplexDTO;

public class ResidentialComplexDTOAdapter implements Adapter<ResidentialComplexDomain, ResidentialComplexDTO> {

    private static final Adapter<ResidentialComplexDomain, ResidentialComplexDTO> instance = new ResidentialComplexDTOAdapter();

    private ResidentialComplexDTOAdapter() {
    }

    public static Adapter<ResidentialComplexDomain, ResidentialComplexDTO> getResidentialComplexDTOAdapter() {
        return instance;
    }

    @Override
    public ResidentialComplexDomain adaptSource(ResidentialComplexDTO data) {
        // Usar un DTO por defecto si el dato es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, ResidentialComplexDTO.create());

        // Adaptación de DTO a Domain
        return ResidentialComplexDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getName(),
                dtoToAdapt.getAddress(),
                CityDTOAdapter.getCityDTOAdapter().adaptSource(dtoToAdapt.getCity()),
                dtoToAdapt.getPhoneNumber(),
                dtoToAdapt.getDescription(),
                AdministratorDTOAdapter.getAdministratorDTOAdapter().adaptSource(dtoToAdapt.getAdministrator())// Adaptamos la ciudad
        );
    }

    @Override
    public ResidentialComplexDTO adaptTarget(ResidentialComplexDomain data) {
        // Usar un Domain por defecto si el dato es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.RESIDENTIAL_COMPLEX);

        // Adaptación de Domain a DTO
        return ResidentialComplexDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setName(domainToAdapt.getName())
                .setAddress(domainToAdapt.getAddress())
                .setCity(CityDTOAdapter.getCityDTOAdapter().adaptTarget(domainToAdapt.getCity())); // Adaptamos la ciudad
    }
    @Override
	public List<ResidentialComplexDTO> adaptTarget(final List<ResidentialComplexDomain> data) {
		
		var results = new ArrayList<ResidentialComplexDTO>();
		
		for (ResidentialComplexDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
}
