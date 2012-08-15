package com.hendyirawan.droolsnull;

import java.util.Date;

public class Notify {
	
	private Date sentTime;

	public Notify(Date sentTime) {
		super();
		this.sentTime = sentTime;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}
	
}
