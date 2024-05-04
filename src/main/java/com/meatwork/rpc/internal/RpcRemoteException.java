package com.meatwork.rpc.internal;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public class RpcRemoteException extends Exception{

	public RpcRemoteException(String message) {
		super(message);
	}

	public RpcRemoteException(String message,
	                          Throwable cause) {
		super(
				message,
				cause
		);
	}
}
