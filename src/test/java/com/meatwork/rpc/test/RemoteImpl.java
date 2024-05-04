package com.meatwork.rpc.test;

import com.meatwork.core.api.di.Service;
import com.meatwork.rpc.api.HttpResponse;
import com.meatwork.rpc.api.Remote;
import com.meatwork.rpc.api.RpcService;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
@RpcService(entrypoint = "toto")
public class RemoteImpl implements Remote<JsonExemple> {
	@Override
	public HttpResponse execute(JsonExemple jsonExemple) {
		return HttpResponse.ok(jsonExemple);
	}
}
