/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.rpc.test {

	requires com.meatwork.rpc;
	requires org.junit.jupiter.api;
	requires org.mockito.junit.jupiter;
	requires org.mockito;
	requires com.fasterxml.jackson.core;
	requires io.netty.buffer;
	requires com.fasterxml.jackson.databind;
	requires com.meatwork.core;

	opens com.meatwork.rpc.test;

}