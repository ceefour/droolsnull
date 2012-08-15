package com.hendyirawan.droolsnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingService {
	
	private transient Logger log = LoggerFactory.getLogger(BookingService.class);
	
	public Double getFilledQty() {
		log.info("getFilledQty() called!");
		return 10.0;
	}

	public Double getNullQty() {
		log.info("getNullQty() called!");
		return null;
	}

}
