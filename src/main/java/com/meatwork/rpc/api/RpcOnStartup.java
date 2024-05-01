package com.meatwork.rpc.api;

import com.meatwork.tools.api.service.ApplicationStartup;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public class RpcOnStartup implements ApplicationStartup {

    private final Set<BindableService> services;
    private final RpcConfiguration rpcConfiguration;
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcOnStartup.class);

    public RpcOnStartup(Set<BindableService> services, RpcConfiguration rpcConfiguration) {
        this.services = services;
        this.rpcConfiguration = rpcConfiguration;
    }

    @Override
    public void run(String[] args) throws Exception {
        int port = Optional.ofNullable(rpcConfiguration).map(RpcConfiguration::getServerPort).orElse(8080);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Starting RPC on port {}", port);
        }

        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);

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
