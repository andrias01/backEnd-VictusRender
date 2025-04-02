//package co.edu.uco.victusresidencias.businesslogic.usecase.state.rules.impl;
//
//import java.util.UUID;
//
//import co.edu.uco.victusresidencias.businesslogic.usecase.state.rules.StateExists;
//import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
//import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;
//import co.edu.uco.victusresidencias.data.dao.DAOFactory;
//import co.edu.uco.victusresidencias.entity.StateEntity;
//
//public final class StateExistsImpl implements StateExists{
//	@Override
//	public final boolean execute(final UUID data, final DAOFactory factory) {
//		var state = ObjectHelper.getDefault(factory.getStateDAO().fingByID(data), new StateEntity());
//		
//		if (data.compareTo(state.getId()) != 0) {
//			var userMessage = "No existe un departamento con el identificador" + data.toString();
//			throw BusinessLogicVictusResidenciasException.crear(userMessage);
//			}
//		
//	}
//}
