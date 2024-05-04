package com.meatwork.rpc.test;

import com.meatwork.rpc.api.RpcOnStartup;
import com.meatwork.rpc.internal.ContentExtractor;
import com.meatwork.rpc.internal.RequestDeserializer;
import com.meatwork.rpc.internal.ServerHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@ExtendWith(MockitoExtension.class)
public class RunRpcServerTest {

    @Test
    @Disabled
    public void testRunServer() throws Exception {
	    RequestDeserializer requestDeserializer = new RequestDeserializer(new ContentExtractor());
	    ServerHandler serverHandler = new ServerHandler(
			    requestDeserializer,
			    Set.of(new RemoteImpl()));
        RpcOnStartup rpcOnStartup = new RpcOnStartup(null,
                                                     serverHandler
        );
        rpcOnStartup.run(null);
    }
}
