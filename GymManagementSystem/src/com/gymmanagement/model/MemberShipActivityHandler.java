package com.gymmanagement.model;

import com.gymmanagement.user.Member;

public class MemberShipActivityHandler extends BaseHandler {

	public boolean handle(LoginRequest request) {
		if(request.loggedInUser instanceof Member) {
			Member member =(Member) request.loggedInUser;
			if(!member.isActive()) {
				System.out.println("Uyeliginiz aktif degil");
				return false;
			}
		}
		return super.handle(request);
	}
}
