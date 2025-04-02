package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;



import co.edu.uco.victusresidencias.entity.CityEntity;

public interface CityDAO 
extends RetrieveDAO<CityEntity, UUID>,
CreateDAO<CityEntity>,
DeleteDAO<UUID>,
UpdateDAO<CityEntity> {

}
