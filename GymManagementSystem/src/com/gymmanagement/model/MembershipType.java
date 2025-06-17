package com.gymmanagement.model;

public enum MembershipType {
    BASIC(100, 1000),    // Aylık: 100, Yıllık: 1000
    PREMIUM(200, 2000),  // Aylık: 200, Yıllık: 2000
    STUDENT(50, 500);    // Aylık: 50, Yıllık: 500

    private final double monthlyPrice;
    private final double yearlyPrice;

    MembershipType(double monthlyPrice, double yearlyPrice) {
        this.monthlyPrice = monthlyPrice;
        this.yearlyPrice = yearlyPrice;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public double getYearlyPrice() {
        return yearlyPrice;
    }
}
