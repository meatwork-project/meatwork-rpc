package com.meatwork.rpc.api;

import com.meatwork.core.api.di.Service;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
@RpcService(entrypoint = "serverStop")
public class StopServer implements Remote<Void>{

	@Override
	public HttpResponse execute(Void unused) {
		RpcOnStartup.group.shutdownGracefully();
		return HttpResponse.ok();
	}
}
