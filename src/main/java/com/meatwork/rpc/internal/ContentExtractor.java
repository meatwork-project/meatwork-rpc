package com.meatwork.rpc.internal;

import com.meatwork.core.api.di.Service;

/*
 * Copyright (c) 2016 Taliro.
 * All rights reserved.
 */
@Service
public class ContentExtractor implements Extractor {

    private boolean separatorFounded;

    @Override
    public HttpHeader extract(String content) throws RpcRemoteException {
        HttpHeader.Builder builder = new HttpHeader.Builder();
        String[] split = content.split("\n");
		separatorFounded = false;
        for (String line : split) {
            computedLine(line, builder);
        }
        return builder.build();
    }

    private void computedLine(String line, HttpHeader.Builder builder) throws RpcRemoteException {
        String[] col = line.toLowerCase().split(" ");
        if (containsMethodHttp(col[0])) {
	        HttpMethod method = HttpMethod.valueOf(col[0].toUpperCase());
			if(!method.equals(HttpMethod.POST)) {
				throw new RpcRemoteException("method POST is only accepted !");
			}
	        builder.path(method, col[1].replaceAll("/", ""));
        } else if (col[0].toLowerCase().contains("content-type")) {
            builder.addHeader("Content-Type", col[1]);
        } else if (col[0].toLowerCase().contains("content-length")) {
            builder.addHeader("Content-Length", col[1]);
        } else if (col[0].toLowerCase().contains("content-encoding")) {
            builder.addHeader("Content-Encoding", col[1]);
        } else if (col[0].toLowerCase().contains("content-language")) {
            builder.addHeader("Content-Language", col[1]);
        } else if (line.trim().isEmpty()) {
            separatorFounded = true;
        } else if (separatorFounded) {
	        builder.body(line);
        }
    }

    private boolean containsMethodHttp(String line) {
        return line.equals("post") || line.equals("get") || line.equals("patch") || line.equals("put") || line.equals("delete");
    }
}
