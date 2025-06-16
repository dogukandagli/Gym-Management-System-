package com.gymmanagement.model;

import com.sun.net.httpserver.Request;

public abstract class BaseHandler implements Handler {
    private Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public boolean handle(LoginRequest request) {
        if (next != null) {
            return next.handle(request);
        }
        return false; 
    }
    
}
