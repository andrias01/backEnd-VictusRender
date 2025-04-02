package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;

import co.edu.uco.victusresidencias.entity.UsageTimeUnitEntity;

public interface UsageTimeUnitDAO 
    extends RetrieveDAO<UsageTimeUnitEntity, UUID>,
            CreateDAO<UsageTimeUnitEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<UsageTimeUnitEntity> {
}
