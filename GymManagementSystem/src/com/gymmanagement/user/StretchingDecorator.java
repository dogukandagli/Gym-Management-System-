package com.gymmanagement.user;

public class StretchingDecorator extends TrainingDecorator {
	public StretchingDecorator(TrainingProgram baseProgram) {
		super(baseProgram);
	}

	public String getDescription() {
		return baseProgram.getDescription() + " + Esneme Hareketleri";
	}

	public double getDuration() {
		return baseProgram.getDuration() + 10.0;
	}

	public double getCalori() {
		return baseProgram.getCalori() + 50;
	}
}
