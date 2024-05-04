package com.meatwork.rpc.api;

import com.meatwork.core.api.di.IService;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@IService(scope = IService.Scope.MULTIPLE)
public interface Remote<I> {
	HttpResponse execute(I i);
}
