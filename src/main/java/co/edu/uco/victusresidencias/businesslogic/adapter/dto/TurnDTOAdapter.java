package co.edu.uco.victusresidencias.businesslogic.adapter.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.domain.TurnDomain;
import co.edu.uco.victusresidencias.dto.TurnDTO;

public class TurnDTOAdapter implements Adapter<TurnDomain, TurnDTO> {

	private static final Adapter<TurnDomain, TurnDTO> instance = new TurnDTOAdapter();

    private TurnDTOAdapter() {
    }

    public static Adapter<TurnDomain, TurnDTO> getTurnDTOAdapter() {
        return instance;
    }
    @Override
    public TurnDomain adaptSource(TurnDTO data) {
        // Usar un DTO por defecto si data es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, TurnDTO.create());
        // Convertimos el DTO a Domain
        return TurnDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getName(),
                dtoToAdapt.getShiftNumber(),
                dtoToAdapt.getStartTime(), 
                dtoToAdapt.getEndTime(),
                dtoToAdapt.getStatus(),
                ScheduledDTOAdapter.getScheduledDTOAdapter().adaptSource(dtoToAdapt.getSchedule())
        );
    }
    @Override
    public TurnDTO adaptTarget(TurnDomain data) {
        // Usar un Domain por defecto si data es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.TURN);

        // Convertimos el Domain a DTO
        return TurnDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setName(domainToAdapt.getName())
                .setShiftNumber(domainToAdapt.getShiftNumber())
                .setStartTime(domainToAdapt.getStartTime())
                .setEndTime(domainToAdapt.getEndTime())
                .setStatus(domainToAdapt.getStatus())
                .setSchedule(ScheduledDTOAdapter.getScheduledDTOAdapter()
	        				.adaptTarget(domainToAdapt.getSchedule()));
                
                
    }
    @Override
	public List<TurnDTO> adaptTarget(final List<TurnDomain> data) {
		
		var results = new ArrayList<TurnDTO>();
		
		for (TurnDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}
    
}
