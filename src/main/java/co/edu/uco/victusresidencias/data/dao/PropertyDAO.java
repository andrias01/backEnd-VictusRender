package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;
import co.edu.uco.victusresidencias.entity.PropertyEntity;

public interface PropertyDAO 
    extends RetrieveDAO<PropertyEntity, UUID>,
            CreateDAO<PropertyEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<PropertyEntity> {
}
