package co.edu.uco.victusresidencias.businesslogic.usecase;

import co.edu.uco.victusresidencias.data.dao.DAOFactory;

public interface RuleWithFactory<T> {
	boolean execute(T data, DAOFactory factory);
}
