package com.meatwork.rpc.internal;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public enum HttpStatusCode {

	OK(200),
	SERVICE_UNAVAILABLE(503);

	private final int code;

	HttpStatusCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
