package com.gymmanagement.model;

public class RoleVerificationHandler extends BaseHandler {

	private String expectedRole;
	
	 public RoleVerificationHandler(String expectedRole) {
	        this.expectedRole = expectedRole;
	    }
	 
	 public boolean handle(LoginRequest request) {
		 if(!request.loggedInUser.getRole().equals(request)) {
			 System.out.println("Yetkisiz giris :"+ expectedRole + " olmalisiniz.");
		 }
		 request.role=expectedRole;
		 return super.handle(request);
	 }
}
