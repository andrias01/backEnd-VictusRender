package co.edu.uco.victusresidencias.businesslogic.adapter.dto;

import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.domain.ReservationDomain;
import co.edu.uco.victusresidencias.dto.ReservationDTO;

public class ReservationDTOAdapter implements Adapter<ReservationDomain,ReservationDTO>{
	private static final Adapter<ReservationDomain,ReservationDTO> instance = new ReservationDTOAdapter();
	
	private ReservationDTOAdapter() {
		
	}
	public static Adapter<ReservationDomain,ReservationDTO> getReservationDTOAdapter(){
		return instance;
	}
	@Override
	public ReservationDomain adaptSource(ReservationDTO data) {
		var dtoToAdapt = ObjectHelper.getDefault(data, ReservationDTO.create());
		return ReservationDomain.create(
				UUIDHelper.convertToUUID(dtoToAdapt.getId()),
				dtoToAdapt.getGuestNumber(),
				TurnDTOAdapter.getTurnDTOAdapter().adaptSource(dtoToAdapt.getTurn()),
				ResidentDTOAdapter.getResidentDTOAdapter().adaptSource(dtoToAdapt.getResident())
				);
	}

    @Override
    public ReservationDTO adaptTarget(ReservationDomain data) {
        // Usar un Domain por defecto si data es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, ReservationDomain.create());

        // Convertimos el Domain a DTO
        return ReservationDTO.create()
                .setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
                .setResident(ResidentDTOAdapter.getResidentDTOAdapter().adaptTarget(domainToAdapt.getResident()))
                .setGuestNumber(domainToAdapt.getGuestNumber())
                .setTurn(TurnDTOAdapter.getTurnDTOAdapter().adaptTarget(domainToAdapt.getTurn()));
                
                
    }
    @Override
	public List<ReservationDTO> adaptTarget(final List<ReservationDomain> data) {
		
		var results = new ArrayList<ReservationDTO>();
		
		for (ReservationDomain domain : data) {
			results.add(adaptTarget(domain));
		}
		
		return results;
	}

}
