package co.edu.uco.victusresidencias.controller.response.concrete;

import java.util.List;

import co.edu.uco.victusresidencias.controller.response.Response;
import co.edu.uco.victusresidencias.controller.response.ResponseWithData;
import co.edu.uco.victusresidencias.dto.CommonZoneDTO;

public final class CommonZoneResponse extends ResponseWithData<CommonZoneDTO>{

	public static final Response build(final List<String> messages,final List<CommonZoneDTO> data) {
		var response= new CommonZoneResponse();
		response.setMessages(messages);
		response.setData(data);
		return response;
	}
}
