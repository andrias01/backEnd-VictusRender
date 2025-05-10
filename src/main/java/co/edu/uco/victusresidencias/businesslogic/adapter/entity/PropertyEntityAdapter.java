package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.businesslogic.adapter.createDefault;
import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
import co.edu.uco.victusresidencias.domain.PropertyDomain;
import co.edu.uco.victusresidencias.entity.PropertyEntity;

import java.util.List;

public class PropertyEntityAdapter implements Adapter<PropertyEntity, PropertyDomain> {
    private static final Adapter<PropertyEntity,PropertyDomain> instance = new PropertyEntityAdapter();
    private PropertyEntityAdapter(){

    }
    public static Adapter<PropertyEntity,PropertyDomain> getPropertyEntityAdapter(){return instance;}

    @Override
    public PropertyEntity adaptSource(PropertyDomain data) {
        var domainToAdapt = ObjectHelper.getDefault(data, createDefault.PROPERTY);
        var entityToAdapt = new PropertyEntity();
        entityToAdapt.setId(domainToAdapt.getId());
        return null;
    }

    @Override
    public PropertyDomain adaptTarget(PropertyEntity data) {
        return null;
    }

    @Override
    public List<PropertyDomain> adaptTarget(List<PropertyEntity> data) {
        return List.of();
    }
}
