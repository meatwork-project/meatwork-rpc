package com.meatwork.rpc.api;

import com.meatwork.core.api.di.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
@RpcService(entrypoint = "serverStop")
public class StopServer implements Remote<Void>{

	public static final Logger LOGGER = LoggerFactory.getLogger(StopServer.class);

	@Override
	public HttpResponse execute(Void unused) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("prepare stop server...");
		}
		RpcOnStartup.group.shutdownGracefully();
		return HttpResponse.ok();
	}
}
