package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;
import co.edu.uco.victusresidencias.entity.ScheduledEntity;

public interface ScheduledDAO 
    extends RetrieveDAO<ScheduledEntity, UUID>,
            CreateDAO<ScheduledEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<ScheduledEntity> {
}
