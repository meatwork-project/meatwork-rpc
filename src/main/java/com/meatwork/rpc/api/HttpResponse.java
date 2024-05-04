package com.meatwork.rpc.api;

import com.meatwork.rpc.internal.HttpStatusCode;
import com.meatwork.rpc.internal.MsgPackSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public final class HttpResponse {

    private final HttpStatusCode status;
    private final Object body;
    private final Map<String, String> header;

    private static String httpMode = "HTTP/1.0";

    public HttpResponse(HttpStatusCode status, Object body, Map<String, String> header) {
        this.status = status;
        this.body = body;
        this.header = header == null ? new HashMap<>() : header;
    }

	public static HttpResponse ok(Object object) {
		return new HttpResponse(HttpStatusCode.OK, object, null);
	}

	public static HttpResponse ko(Object object) {
		return new HttpResponse(HttpStatusCode.SERVICE_UNAVAILABLE, object, null);
	}

	public static HttpResponse ok() {
		return new HttpResponse(HttpStatusCode.OK, null, null);
	}

    public static void setHttpMode() {
        httpMode = "HTTP/2.0";
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

	public void addContentType(String value) {
		this.header.put(
				"Content-Type",
				value
		);
	}

    private String toCapitalize(String str) {
        List<String> words = new ArrayList<>();
        for (String el : str.split("-")) {
            if (el == null || str.isEmpty()) {
                words.add(str);
                continue;
            }
            words.add(Character.toUpperCase(str.charAt(0)) + str.substring(1));
        }
        return String.join("-", words);
    }

    @Override
    public String toString() {
		String encoder = "";
	    if(body != null) {
		    encoder = MsgPackSerializer.encoder(body);
	    }

	    return """
        %s %s %s
        %s
        Content-Length: %s

        %s
        """.formatted(
                httpMode,
                status.getCode(),
                status.getCode() > 200 && status.getCode() < 300 ? "OK" : "KO",
                header.entrySet().stream().map(it -> "%s: %s".formatted(toCapitalize(it.getKey()), it.getValue())).collect(Collectors.joining("\n")),
                encoder.length(),
                encoder
            );
    }
}
