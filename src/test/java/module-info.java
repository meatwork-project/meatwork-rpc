/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
module com.meatwork.rpc.test {
	requires com.meatwork.rpc;
	requires com.meatwork.core;
	requires org.junit.jupiter.api;
	requires org.mockito.junit.jupiter;
	requires org.mockito;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;

	opens com.meatwork.rpc.test;

}