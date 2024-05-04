package com.meatwork.rpc.internal;

import com.meatwork.core.api.di.IService;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@IService(scope = IService.Scope.SINGLE, mandatory = false)
public interface RpcConfiguration {
	int getServerPort();
}
