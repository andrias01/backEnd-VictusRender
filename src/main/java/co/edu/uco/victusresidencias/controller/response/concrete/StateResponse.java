package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;


import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.StateDTO;

public final class StateResponse extends ResponseWithData<StateDTO>{

	public static final Response build(final List<String> messages,final List<StateDTO> data) {
		var response= new StateResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;
	}
}
