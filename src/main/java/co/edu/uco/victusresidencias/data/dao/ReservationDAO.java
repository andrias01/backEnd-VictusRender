package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;
import co.edu.uco.victusresidencias.entity.ReservationEntity;

public interface ReservationDAO 
    extends RetrieveDAO<ReservationEntity, UUID>,
            CreateDAO<ReservationEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<ReservationEntity> {
}
