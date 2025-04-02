package co.edu.uco.victusresidencias.businesslogic.usecase;

public interface UseWithReturn <D, R>{

	R execute(D data);
	
}
