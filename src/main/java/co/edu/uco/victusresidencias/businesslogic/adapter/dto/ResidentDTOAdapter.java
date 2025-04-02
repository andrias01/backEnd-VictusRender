package co.edu.uco.victusresidencias.businesslogic.adapter.dto;



import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.domain.ResidentDomain;
import co.edu.uco.victusresidencias.dto.ResidentDTO;

public class ResidentDTOAdapter implements Adapter<ResidentDomain, ResidentDTO>{
	
	private static final Adapter<ResidentDomain, ResidentDTO> instance = new ResidentDTOAdapter();

    private ResidentDTOAdapter() {
    }

    public static Adapter<ResidentDomain, ResidentDTO> getResidentDTOAdapter() {
        return instance;
    }

    @Override
    public ResidentDomain adaptSource(ResidentDTO data) {
        // Usar un DTO por defecto si data es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, ResidentDTO.create());
        
        // Convertimos el DTO a Domain
        return ResidentDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getIdType(),
                dtoToAdapt.getName(),
                dtoToAdapt.getLastName(), 
                dtoToAdapt.getIdNumber(),
                dtoToAdapt.getDate(),
                dtoToAdapt.getContactNumber(),
                dtoToAdapt.getPassword(),
                PropertyDTOAdapter.getPropertyDTOAdapter().adaptSource(dtoToAdapt.getProperty())
        );
    }
    @Override
    public ResidentDTO adaptTarget(ResidentDomain data) {
        // Usar un Domain por defecto si data es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.RESIDENT);

        // Convertimos el Domain a DTO
        return ResidentDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setName(domainToAdapt.getName())
                .setLastName(domainToAdapt.getLastName())
                .setIdNumber(domainToAdapt.getIdNumber())
                .setDate(domainToAdapt.getBirthDate())
                .setContactNumber(domainToAdapt.getContactNumber())
                .setPassword(domainToAdapt.getPassword())
                ;
    }
    @Override
	public List<ResidentDTO> adaptTarget(final List<ResidentDomain> data) {
		
		var results = new ArrayList<ResidentDTO>();
		
		for (ResidentDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}


}
