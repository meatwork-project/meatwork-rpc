package com.meatwork.rpc.internal;

import com.meatwork.core.api.di.Service;
import com.meatwork.rpc.api.HttpResponse;
import com.meatwork.rpc.api.Remote;
import com.meatwork.rpc.api.RpcService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    private final RequestDeserializer requestDeserializer;
    private final Set<Remote<?>> remoteSet;

    @Inject
    public ServerHandler(RequestDeserializer requestDeserializer, Set<Remote<?>> remoteSet) throws RpcRemoteException {
        this.requestDeserializer = requestDeserializer;
        this.remoteSet = remoteSet;
        if (!validateRemoteService()) {
            throw new RpcRemoteException("Error on configuration rpc service check all service are @RpcService([path]) enable");
        }
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws RpcRemoteException, IOException {
        ByteBuf in = (ByteBuf) msg;
        byte[] byteArray = toByteArray(in);
        HttpHeader decoder = requestDeserializer.decoder(byteArray);
        Remote remote = remoteSet
            .stream()
            .filter(it -> it.getClass().getAnnotation(RpcService.class).entrypoint().equals(decoder.path()))
            .findFirst()
            .orElseThrow(() -> new RpcRemoteException("No class for path %s found".formatted(decoder.path())));

        Method execute = Stream
            .of(remote.getClass().getDeclaredMethods())
            .filter(it -> it.getName().equals("execute") && !Objects.equals(it.getParameters()[0].getType(), Object.class))
            .findFirst()
            .orElse(null);

        Parameter parameter = execute.getParameters()[0];

        HttpResponse response;
        Type parameterizedType = parameter.getParameterizedType();
        Class<?> clazzParameterized = (Class<?>) parameterizedType;
        if (clazzParameterized.equals(Void.class)) {
            response = remote.execute(null);
        } else {
            response = remote.execute(MsgPackSerializer.decode(decoder.body(), clazzParameterized));
        }

        response.getHeader().putIfAbsent("Content-Type", "text/plain");

        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(response.toString().getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(byteBuf);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error(cause.getMessage());
        HttpResponse httpResponse = new HttpResponse(
            HttpStatusCode.SERVICE_UNAVAILABLE,
            cause.getMessage(),
            Map.of("Content-Type", "text/plain")
        );
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(httpResponse.toString().getBytes(StandardCharsets.UTF_8));
        ctx.write(byteBuf);
        ctx.flush();
        ctx.close();
    }

    private byte[] toByteArray(ByteBuf byteBuf) {
        ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
        byte[] byteArray = new byte[byteBuffer.remaining()];
        byteBuffer.get(byteArray);
        return byteArray;
    }

    private boolean validateRemoteService() {
        return this.remoteSet.stream().allMatch(it -> it.getClass().isAnnotationPresent(RpcService.class));
    }
}
