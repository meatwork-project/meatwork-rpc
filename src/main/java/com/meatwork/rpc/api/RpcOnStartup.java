package com.meatwork.rpc.api;

import com.meatwork.core.api.di.Service;
import com.meatwork.core.api.service.ApplicationStartup;
import com.meatwork.rpc.internal.RpcConfiguration;
import com.meatwork.rpc.internal.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.inject.Inject;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
public class RpcOnStartup implements ApplicationStartup {

	private final RpcConfiguration rpcConfiguration;
	private final ServerHandler serverHandler;
	static EventLoopGroup group;

	@Override
	public int priority() {
		return 999;
	}

	@Inject
	public RpcOnStartup(RpcConfiguration rpcConfiguration,
	                    ServerHandler serverHandler) {
		this.rpcConfiguration = rpcConfiguration;
		this.serverHandler = serverHandler;
	}


	@Override
    public void run(String[] args) throws Exception {
		int serverPort = 7777;
		if(rpcConfiguration != null) {
			serverPort = rpcConfiguration.getServerPort();
		}
		runServer(serverPort);
	}

	private void runServer(int port) throws InterruptedException {
		group = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new ChannelInitializer<SocketChannel>() {
				 @Override
				 public void initChannel(SocketChannel ch) {
					 ch.pipeline().addLast(serverHandler);
				 }
			 })
			 .option(ChannelOption.SO_BACKLOG, 128)
			 .childOption(ChannelOption.SO_KEEPALIVE, true);

			b.bind(port).sync().channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
