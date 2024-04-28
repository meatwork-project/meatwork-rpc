import com.google.inject.Module;
import com.meatwork.rpc.module.RpcModule;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.rpc {
	requires io.grpc;
	requires io.grpc.protobuf;
	requires io.grpc.stub;
	requires com.meatwork.tools;
	requires com.google.guice;
	requires proto.google.common.protos;
	requires org.slf4j;

	uses Module;
	provides Module with RpcModule;

	exports com.meatwork.rpc.api;
}