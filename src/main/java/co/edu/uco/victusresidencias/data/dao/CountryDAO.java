package co.edu.uco.victusresidencias.data.dao;

import java.util.UUID;


import co.edu.uco.victusresidencias.entity.CountryEntity;

public interface CountryDAO 
	extends RetrieveDAO<CountryEntity, UUID>,
			CreateDAO<CountryEntity>,
			DeleteDAO<UUID>,
			UpdateDAO<CountryEntity>{

}
