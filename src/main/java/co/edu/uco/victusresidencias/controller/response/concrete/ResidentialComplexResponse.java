package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;

import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.ResidentialComplexDTO;

public final class ResidentialComplexResponse extends ResponseWithData<ResidentialComplexDTO>{

	public static final Response build(final List<String> messages,final List<ResidentialComplexDTO> data) {
		var response= new ResidentialComplexResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;
	}
}
