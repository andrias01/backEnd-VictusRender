package co.edu.uco.victusresidencias.businesslogic.adapter.dto;



import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.domain.ScheduledDomain;
import co.edu.uco.victusresidencias.dto.ScheduledDTO;

public class ScheduledDTOAdapter implements Adapter<ScheduledDomain, ScheduledDTO>{
	private static final Adapter<ScheduledDomain, ScheduledDTO> instance = new ScheduledDTOAdapter();

    private ScheduledDTOAdapter() {
    }

    public static Adapter<ScheduledDomain, ScheduledDTO> getScheduledDTOAdapter() {
        return instance;
    }
    @Override
    public ScheduledDomain adaptSource(ScheduledDTO data) {
        // Usar un DTO por defecto si data es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, ScheduledDTO.create());
        // Convertimos el DTO a Domain
        return ScheduledDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getName(),
                dtoToAdapt.getAvailability(),
                dtoToAdapt.getStartDateTime(), 
                dtoToAdapt.getEndDateTime(),
                CommonZoneDTOAdapter.getCommonZoneDTOAdapter().adaptSource(dtoToAdapt.getCommonArea())
        );
    }
    @Override
    public ScheduledDTO adaptTarget(ScheduledDomain data) {
        // Usar un Domain por defecto si data es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.SCHEDULED);

        // Convertimos el Domain a DTO
        return ScheduledDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setName(domainToAdapt.getName())
                .setAvailability(domainToAdapt.getAvailability())
                .setStartDateTime(domainToAdapt.getStartDateTime())
                .setEndDateTime(domainToAdapt.getEndDateTime())
                .setCommonArea(CommonZoneDTOAdapter.getCommonZoneDTOAdapter().adaptTarget(domainToAdapt.getCommonZone()));
                
                
                
    }
    @Override
	public List<ScheduledDTO> adaptTarget(final List<ScheduledDomain> data) {
		
		var results = new ArrayList<ScheduledDTO>();
		
		for (ScheduledDomain domain : data) {
			results.add(adaptTarget(domain));

		}
		return results;

    }
}
