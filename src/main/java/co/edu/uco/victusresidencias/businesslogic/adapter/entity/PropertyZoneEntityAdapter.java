package co.edu.uco.victusresidencias.businesslogic.adapter.entity;

import co.edu.uco.victusresidencias.businesslogic.adapter.Adapter;
import co.edu.uco.victusresidencias.domain.PropertyDomain;
import co.edu.uco.victusresidencias.domain.PropertyZoneDomain;
import co.edu.uco.victusresidencias.entity.PropertyEntity;
import co.edu.uco.victusresidencias.entity.PropertyZoneEntity;

import java.util.List;

public class PropertyZoneEntityAdapter implements Adapter<PropertyZoneEntity, PropertyZoneDomain> {
    private static Adapter<PropertyZoneEntity, PropertyZoneDomain> instance = new PropertyZoneEntityAdapter();
    private PropertyZoneEntityAdapter(){
    }
    public static final Adapter<PropertyZoneEntity, PropertyZoneDomain> getPropertyZoneEntityAdapter(){
        return instance;
    }

    @Override
    public PropertyZoneEntity adaptSource(PropertyZoneDomain data) {

        return null;
    }

    @Override
    public PropertyZoneDomain adaptTarget(PropertyZoneEntity data) {
        return null;
    }

    @Override
    public List<PropertyZoneDomain> adaptTarget(List<PropertyZoneEntity> data) {
        return List.of();
    }
}
