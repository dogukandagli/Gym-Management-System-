package com.gymmanagement.model;

public interface Handler {
    	Handler setNext(Handler next);

	    boolean handle(LoginRequest request);
}
