package com.meatwork.rpc.internal;

import com.meatwork.core.api.di.Service;
import jakarta.inject.Inject;

import java.nio.charset.StandardCharsets;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
public class RequestDeserializer {

	private final Extractor extractors;

	@Inject
	public RequestDeserializer(Extractor extractors) {
		this.extractors = extractors;
	}

    public HttpHeader decoder(byte[] bytes) throws RpcRemoteException {
        String content = new String(
		        bytes,
		        StandardCharsets.UTF_8
        );
        return extractors.extract(content);
    }
}
