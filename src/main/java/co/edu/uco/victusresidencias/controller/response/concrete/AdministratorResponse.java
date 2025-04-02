package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;

import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.AdministratorDTO;

public final class AdministratorResponse extends ResponseWithData<AdministratorDTO>{

	public static final Response build(final List<String> messages,final List<AdministratorDTO> data) {
		var response= new AdministratorResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;
	}
}
