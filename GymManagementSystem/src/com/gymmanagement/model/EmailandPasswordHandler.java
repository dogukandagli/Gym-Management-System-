package com.gymmanagement.model;

import com.gymmanagement.database.Database;
import com.gymmanagement.user.User;
import com.sun.net.httpserver.Request;



public class EmailandPasswordHandler extends BaseHandler{
    
	public boolean handle(LoginRequest request) {
	    User user = Database.getInstance().getUserByEmail(request.email);
	    if (user != null && user.getPassword().equals(request.password)) {
	        request.loggedInUser = user;
	        return true;
	    }
	    System.out.println("invalid email or password");
	    return false;
	}

}
