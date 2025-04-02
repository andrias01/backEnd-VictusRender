package co.edu.uco.victusresidencias.businesslogic.usecase.administrator;

import java.util.List;


import co.edu.uco.victusresidencias.businesslogic.usecase.UseWithReturn;
import co.edu.uco.victusresidencias.domain.AdministratorDomain;

public interface FindAdministrator extends UseWithReturn<AdministratorDomain, List<AdministratorDomain>>{

}
