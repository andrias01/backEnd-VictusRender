package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;
import co.edu.uco.victusresidencias.entity.TurnEntity;

public interface TurnDAO 
    extends RetrieveDAO<TurnEntity, UUID>,
            CreateDAO<TurnEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<TurnEntity> {
}
