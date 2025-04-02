package co.edu.uco.victusresidencias.controller.response;

import java.util.List;


import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;

public abstract class ResponseWithData<T> extends Response {
	
	private List<T> data;

	public final List<T> getData() {
		return data;
	}

	public final void setData(final List<T> data) {
		
		this.data = ObjectHelper.getDefault(data, this.data);
	}
	
	

}
