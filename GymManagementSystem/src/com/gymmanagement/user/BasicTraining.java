package com.gymmanagement.user;

import java.util.ArrayList;
import java.util.List;

public class BasicTraining implements TrainingProgram {

	private List<TrainingProgram> exercises = new ArrayList<>();

	public void addExercise(TrainingProgram component) {
		exercises.add(component);
	}

	public String getDescription() {
		return "Temel Govde Antreman Programi : " + exercises.stream().map(TrainingProgram::getDescription)
				.reduce((a, b) -> a + ", " + b).orElse("Boş Program");
	}

	public double getDuration() {
		return exercises.stream().mapToDouble(TrainingProgram::getDuration).sum();
	}

	public double getCalori() {
		return 150.0;
	}
}
