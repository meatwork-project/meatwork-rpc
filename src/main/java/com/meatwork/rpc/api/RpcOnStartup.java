package com.meatwork.rpc.api;

import com.meatwork.tools.api.service.ApplicationStartup;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public class RpcOnStartup implements ApplicationStartup {

	private final Set<BindableService> services;
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcOnStartup.class);

	public RpcOnStartup(Set<BindableService> services) {
		this.services = services;
	}

	@Override
	public void run(String[] args) throws Exception {
		ServerBuilder<?> serverBuilder = ServerBuilder
				.forPort(8080);

		for (BindableService service : services) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("service %s finded".formatted(service.getClass().getName()));
			}
			serverBuilder.addService(service);
		}
		Server server = serverBuilder.build();
		server.start();
		server.awaitTermination();
	}
}
