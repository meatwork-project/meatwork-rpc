/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.rpc {
	requires com.meatwork.core;
	requires org.slf4j;
	requires java.xml;
	requires msgpack.core;
	requires commons.lang;
	requires org.reflections;
	requires com.fasterxml.jackson.databind;
	requires jakarta.inject;
	requires jackson.dataformat.msgpack;
	requires java.rmi;
	requires java.net.http;
	requires io.netty.transport;
	requires io.netty.buffer;

	exports com.meatwork.rpc.api;
	exports com.meatwork.rpc.internal to com.meatwork.rpc.test, com.meatwork.core;
}