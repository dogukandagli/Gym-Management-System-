package com.gymmanagement.user;

public class CardioDecorator extends TrainingDecorator {
		
	public CardioDecorator(TrainingProgram baseProgram) {
		super(baseProgram);
	}
	
	 public String getDescription() {
	        return baseProgram.getDescription() + " + Kardiyo";
	    }
	 
	  public double getDuration() {
	        return baseProgram.getDuration() + 20.0;
	    }
	  
	  public double getCalori() {
		  return baseProgram.getCalori()+300;
	  }
}
