package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;


import co.edu.uco.victusresidencias.entity.AdministratorEntity;

public interface AdministratorDAO 
    extends RetrieveDAO<AdministratorEntity, UUID>,
            CreateDAO<AdministratorEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<AdministratorEntity> {
}
