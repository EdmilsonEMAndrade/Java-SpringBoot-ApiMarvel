package br.com.desafio.orange.talents.controller.exception.handler;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StandardError implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String message;
	private LocalDateTime timeStamp;
	
	public StandardError(Integer status, String message) {
		this.status = status;
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return message;
	}

	public void setMensagem(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}	
	
}
