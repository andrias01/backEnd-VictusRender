package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;

import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.CityDTO;

public final class CityResponse extends ResponseWithData<CityDTO>{

	public static final Response build(final List<String> messages,final List<CityDTO> data) {
		var response= new CityResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;
	}
}
