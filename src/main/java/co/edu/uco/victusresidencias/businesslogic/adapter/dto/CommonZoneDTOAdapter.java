package co.edu.uco.victusresidencias.businesslogic.adapter.dto;



import java.util.ArrayList;
import java.util.List;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.crosscutting.helpers.UUIDHelper;
import co.edu.uco.victusresidencias.domain.CommonZoneDomain;
import co.edu.uco.victusresidencias.dto.CommonZoneDTO;


public class CommonZoneDTOAdapter implements Adapter<CommonZoneDomain, CommonZoneDTO>{
	
	private static final Adapter<CommonZoneDomain, CommonZoneDTO> instance = new CommonZoneDTOAdapter();
	 private CommonZoneDTOAdapter() {
	    }

	    public static Adapter<CommonZoneDomain, CommonZoneDTO> getCommonZoneDTOAdapter() {
	        return instance;
	    }

	    @Override
	    public CommonZoneDomain adaptSource(CommonZoneDTO data) {
	        // Si el DTO es nulo, usamos un DTO por defecto
	        var dtoToAdapt = ObjectHelper.getDefault(data, CommonZoneDTO.create());

	        // Convertimos el DTO a Domain
	        return CommonZoneDomain.create(
	                UUIDHelper.convertToUUID(dtoToAdapt.getId()),
	                dtoToAdapt.getName(),
	                dtoToAdapt.getDescription(),
	                dtoToAdapt.getCapacityPeople(),
	                dtoToAdapt.getUsageTime(),
	                dtoToAdapt.getUsageTimeUnit(),
	                dtoToAdapt.getRules(),
	                ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter().adaptSource(dtoToAdapt.getResidentialComplex())
	        );
	    }

	    @Override
	    public CommonZoneDTO adaptTarget(CommonZoneDomain data) {
	        // Si el Domain es nulo, usamos un Domain por defecto
	        var domainToAdapt = ObjectHelper.getDefault(data,createDefault.COMMON_ZONE);
	        
	        return CommonZoneDTO.create()
	        		.setId(ObjectHelper.getDefault(domainToAdapt.getId().toString(), UUIDHelper.getDefaultAsString()))
	        		.setName(domainToAdapt.getName())
	        		.setDescription(domainToAdapt.getDescription())
	        		.setCapacityPeople(domainToAdapt.getPeopleLimit())
	        		.setUsageTime(domainToAdapt.getUseTime())
	        		.setUsageTimeUnit(domainToAdapt.getUseTimeUnit())
	        		.setRules(domainToAdapt.getRules())
	        		.setResidentialComplex(ResidentialComplexDTOAdapter.getResidentialComplexDTOAdapter()
	        				.adaptTarget(domainToAdapt.getResidentialComplex()));

	    }
	    @Override
		public List<CommonZoneDTO> adaptTarget(final List<CommonZoneDomain> data) {
			
			var results = new ArrayList<CommonZoneDTO>();
			
			for (CommonZoneDomain domain : data) {
				results.add(adaptTarget(domain));
			}
			
			return results;
		}
}
