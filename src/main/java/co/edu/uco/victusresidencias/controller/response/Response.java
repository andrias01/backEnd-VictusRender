package co.edu.uco.victusresidencias.controller.response;

import java.util.ArrayList;

import java.util.List;

import co.edu.uco.victusresidencias.crosscutting.helpers.ObjectHelper;


public abstract class Response {

	private List<String> messages = new ArrayList<>();
	
	public final void setMessages(final List<String> messages) {
		this.messages = ObjectHelper.getDefault(messages, this.messages);
	}

	public List<String> getMessages() {
		return messages;
	}
	
	
}
