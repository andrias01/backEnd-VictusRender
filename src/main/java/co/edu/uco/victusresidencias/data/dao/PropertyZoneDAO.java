package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;

import co.edu.uco.victusresidencias.entity.PropertyZoneEntity;

public interface PropertyZoneDAO 
    extends RetrieveDAO<PropertyZoneEntity, UUID>,
            CreateDAO<PropertyZoneEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<PropertyZoneEntity> {
}
