package com.example.demo.payload.response;

public class MessageResponse {
	public Boolean success;
	public Object message;

	public MessageResponse(Boolean success, Object message) {
		this.success = success;
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
