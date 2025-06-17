package com.gymmanagement.user;

public abstract class TrainingDecorator implements TrainingProgram{

	protected TrainingProgram baseProgram;
	
	public TrainingDecorator(TrainingProgram baseProgram) {
		this.baseProgram=baseProgram;
	}
}
