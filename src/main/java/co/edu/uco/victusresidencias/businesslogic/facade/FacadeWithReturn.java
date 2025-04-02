package co.edu.uco.victusresidencias.businesslogic.facade;

public interface FacadeWithReturn<T, R> {
	R execute (T data);
}
