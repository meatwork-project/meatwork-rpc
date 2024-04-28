package com.meatwork.rpc.module;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.meatwork.rpc.api.RpcOnStartup;
import com.meatwork.tools.api.service.ApplicationStartup;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public class RpcModule extends AbstractModule {

	@Override
	protected void configure() {
		Multibinder
				.newSetBinder(
						binder(),
						ApplicationStartup.class
				)
				.addBinding()
				.to(RpcOnStartup.class);
	}
}
