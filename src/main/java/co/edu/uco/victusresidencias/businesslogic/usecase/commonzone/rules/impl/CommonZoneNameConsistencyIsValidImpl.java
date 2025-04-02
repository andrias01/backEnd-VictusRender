package co.edu.uco.victusresidencias.businesslogic.usecase.commonzone.rules.impl;

import co.edu.uco.victusresidencias.businesslogic.usecase.commonzone.rules.CommonZoneNameConsistencyIsValid;
import co.edu.uco.victusresidencias.crosscutting.exceptions.BusinessLogicVictusResidenciasException;
import co.edu.uco.victusresidencias.crosscutting.helpers.TextHelper;

public final class CommonZoneNameConsistencyIsValidImpl implements CommonZoneNameConsistencyIsValid{
	@Override
	public void execute(final String data,final String nameAtribute) {
		validateNotNull(data,nameAtribute);
		validateLen(data,nameAtribute);
		validateFormat(data,nameAtribute);
	}
	
	private void validateLen(final String data,final String nameAtribute) {
		if(!TextHelper.maxLenIsValid(data, 50)) {
			var userMessage = "El nombre de la Zona Común no puede contener máximo 50 ...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage);
			
		}
	}
	
	private void validateFormat(final String data,final String nameAtribute) {
		if (!TextHelper.containsOnlyLettersAndSpaces(data)) {
			var userMessage = "El nombre de la zona común solo puede contener letras y espacios...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage);
			
		}	
	}
	
	private void  validateNotNull(final String data,final String nameAtribute) {
		if (TextHelper.isEmpty(data)) {
			var userMessage = "El nombre de la zona común no puede estar vacio...";
			throw BusinessLogicVictusResidenciasException.crear(userMessage);
			
		}
	}
}
