package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.ResidentDomain;
import co.edu.uco.victusresidencias.dto.ResidentDTO;
import co.edu.uco.victusresidencias.entity.ResidentEntity;
import co.edu.uco.victusresidencias.entity.ResidentialComplexEntity;

import java.util.List;

public class ResidentEntityAdapter implements Adapter<ResidentEntity, ResidentDomain> {
    private static final Adapter<ResidentEntity,ResidentDomain> instance = new ResidentEntityAdapter();

    public static Adapter<ResidentEntity,ResidentDomain> getResidentEntityAdapter() {return instance;}

    @Override
    public ResidentEntity adaptSource(ResidentDomain data) {
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.RESIDENT);

        // Convert StateDomain to StateEntity
        var entityToAdapt = new ResidentEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        entityToAdapt.setName(domainToAdapt.getName());
        entityToAdapt.setLastName(domainToAdapt.getLastName());
        entityToAdapt.setIdType(domainToAdapt.getIdType());
        entityToAdapt.setDocumentNumber(domainToAdapt.getIdNumber());
        entityToAdapt.setBirthDate(domainToAdapt.getBirthDate());
        entityToAdapt.setContactNumber(domainToAdapt.getContactNumber());
        entityToAdapt.setPassword(domainToAdapt.getPassword());
        //entityToAdapt.setProperty(domainToAdapt.getProperty());
        //Es necesario el adaptador de property
        /*private String name;
    private String lastName;
    private String idType; // Cambiado de documentType a idType
    private String documentNumber; // Sigue siendo String
    private LocalDate birthDate;
    private String contactNumber; // Sigue siendo String
    private String password;
    private PropertyEntity property;*/
        return null;
    }

    @Override
    public ResidentDomain adaptTarget(ResidentEntity data) {
        return null;
    }

    @Override
    public List<ResidentDomain> adaptTarget(List<ResidentEntity> data) {
        return List.of();
    }
}
