package com.isa.rest.service.jsp;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Prediction implements Serializable {
	// person
	private String who;
	// his/her prediction
	private String what;

	public Prediction() {
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getWho() {
		return this.who;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getWhat() {
		return this.what;
	}
}
