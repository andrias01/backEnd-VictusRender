package co.edu.uco.victusresidencias.businesslogic.adapter.dto;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import java.util.ArrayList;
import java.util.List;
import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.domain.UsageTimeUnitDomain;
import co.edu.uco.victusresidencias.dto.UsageTimeUnitDTO;

public class UsageTimeUnitDTOAdapter implements Adapter<UsageTimeUnitDomain, UsageTimeUnitDTO> {

    private static final Adapter<UsageTimeUnitDomain, UsageTimeUnitDTO> instance = new UsageTimeUnitDTOAdapter();

    private UsageTimeUnitDTOAdapter() {
    }

    public static Adapter<UsageTimeUnitDomain, UsageTimeUnitDTO> getUsageTimeUnitDTOAdapter() {
        return instance;
    }

    @Override
    public UsageTimeUnitDomain adaptSource(UsageTimeUnitDTO data) {
        // Usar un DTO por defecto si el dato es nulo
        var dtoToAdapt = ObjectHelper.getDefault(data, UsageTimeUnitDTO.create());

        // Adaptación de DTO a Domain
        return UsageTimeUnitDomain.create(
                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
                dtoToAdapt.getName(),
                dtoToAdapt.getAbbreviation(),
                dtoToAdapt.getInBased(),
                dtoToAdapt.getDescription()
        );
    }

    @Override
    public UsageTimeUnitDTO adaptTarget(UsageTimeUnitDomain data) {
        // Usar un Domain por defecto si el dato es nulo
        var domainToAdapt = ObjectHelper.getDefault(data, UsageTimeUnitDomain.create());

        // Adaptación de Domain a DTO
        return UsageTimeUnitDTO.create()
                .setId(domainToAdapt.getId().toString())
                .setName(domainToAdapt.getName())
                .setAbbreviation(domainToAdapt.getAbbreviation())
                .setInBased(domainToAdapt.getInBased())
                .setDescription(domainToAdapt.getDescription());
    }
    
    @Override
    public List<UsageTimeUnitDTO> adaptTarget(final List<UsageTimeUnitDomain> data) {
        var results = new ArrayList<UsageTimeUnitDTO>();
        
        for (UsageTimeUnitDomain domain : data) {
            results.add(adaptTarget(domain));
        }
        
        return results;
    }
}