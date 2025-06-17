package com.gymmanagement.model;

import java.util.List;

import com.gymmanagement.database.Database;
import com.gymmanagement.model.ClassSession;
import com.gymmanagement.model.Gym;
import com.gymmanagement.user.Member;

public class GymMemberNotifier implements GymListener {
    private final List<Member> allMembers;

    public GymMemberNotifier(List<Member> allMembers) {
        this.allMembers = allMembers;
    }

    @Override
    public void onClassAdded(ClassSession session) {
        Gym gym = session.getGym();
        for (Member m : allMembers) {
            if (m.getGym() != null && m.getGym().getGymID().equals(gym.getGymID())) {
                System.out.println("📢 [BİLDİRİM] " + m.getName() + ", yeni sınıf eklendi: " + session.getName());
                m.addNotification("📢 [BİLDİRİM] " + m.getName() + ", yeni sınıf eklendi: " + session.getName());
                Database.getInstance().updateMember(m);
            }
        }
    }
}
