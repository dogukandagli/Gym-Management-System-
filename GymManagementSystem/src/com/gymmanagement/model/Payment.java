package com.gymmanagement.model;

import java.time.LocalDateTime;

import com.gymmanagement.user.Member;

public class Payment {
    private int id;
    private Member member;
    private double amount;
    private LocalDateTime paymentDate;
    private PaymentType paymentType;
    private PaymentStatus status;

    public Payment(int id, Member member, double amount, LocalDateTime paymentDate, PaymentType paymentType, PaymentStatus status) {
        this.id = id;
        this.member = member;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentType = paymentType;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}

// Enum for PaymentType
enum PaymentType {
    CASH,
    CREDIT_CARD,
    DEBIT_CARD,
    ONLINE
}

// Enum for PaymentStatus
enum PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    CANCELLED
} 