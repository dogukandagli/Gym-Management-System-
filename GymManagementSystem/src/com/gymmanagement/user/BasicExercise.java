package com.gymmanagement.user;

public class BasicExercise implements TrainingProgram{

	   private String name;
	    private double duration;
	    
    public BasicExercise(String name, double duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getDescription() {
        return name;
    }

    public double getDuration() {
        return duration;
    }
    public double getCalori() {
    	return 0;
    }
}
