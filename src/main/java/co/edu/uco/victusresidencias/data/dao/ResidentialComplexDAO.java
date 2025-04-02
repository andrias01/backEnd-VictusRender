package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;

import co.edu.uco.victusresidencias.entity.ResidentialComplexEntity;

public interface ResidentialComplexDAO 
    extends RetrieveDAO<ResidentialComplexEntity, UUID>,
            CreateDAO<ResidentialComplexEntity>,
            DeleteDAO<UUID>,
            UpdateDAO<ResidentialComplexEntity> {
}
