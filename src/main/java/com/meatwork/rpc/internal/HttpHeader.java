package com.meatwork.rpc.internal;

import java.util.HashMap;
import java.util.Map;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public record HttpHeader(String path, HttpMethod method, Map<String, String> header, String body) {

	public static class Builder {
		private String path;
		private HttpMethod method;
		private final Map<String, String> header = new HashMap<>();
		private String body;

		public Builder() {}

		public void path(HttpMethod method, String path) {
			this.path = path;
			this.method = method;
		}

		public void addHeader(String key, String value) {
			header.put(
					key,
					value
			);
		}

		public void body(String body) {
			this.body = body;
		}

		public HttpHeader build() {
			return new HttpHeader(path, method, header, body);
		}
	}

}
