package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;

import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.CountryDTO;

public final class CountryResponse extends ResponseWithData<CountryDTO>{

	public static final Response build(final List<String> messages,final List<CountryDTO> data) {
		var response= new CountryResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;   
	}
}
