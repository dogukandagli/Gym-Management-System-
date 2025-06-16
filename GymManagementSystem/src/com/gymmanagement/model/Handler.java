package com.gymmanagement.model;

import com.sun.net.httpserver.Request;

public interface Handler {
    	Handler setNext(Handler next);

	    boolean handle(LoginRequest request);
}
