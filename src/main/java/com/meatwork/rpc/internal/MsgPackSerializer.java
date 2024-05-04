package com.meatwork.rpc.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
public class MsgPackSerializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsgPackSerializer.class);
	private static final ObjectMapper mapper = new ObjectMapper(new MessagePackFactory());

	public static Object decode(String body, Class<?> clazz) throws IOException {
		byte[] bytes = toBytes(body);
		return mapper.readValue(bytes, clazz);
	}

	public static String encoder(Object object) {
		try {
			byte[] bytes = mapper.writeValueAsBytes(object);
			return bytesToHexString(bytes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private static String bytesToHexString(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	private static byte[] toBytes(String body)  {
		body = body.replace(" ", "");
		int len = body.length();
		byte[] byteArray = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			byteArray[i / 2] = (byte) ((Character.digit(body.charAt(i), 16) << 4)
					+ Character.digit(body.charAt(i+1), 16));
		}
		return byteArray;
	}

}
