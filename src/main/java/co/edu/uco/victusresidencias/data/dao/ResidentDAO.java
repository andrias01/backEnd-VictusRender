package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;
import co.edu.uco.victusresidencias.entity.ResidentEntity;

public interface ResidentDAO 
    extends RetrieveDAO<ResidentEntity, UUID>,
            CreateDAO<ResidentEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<ResidentEntity> {
}
