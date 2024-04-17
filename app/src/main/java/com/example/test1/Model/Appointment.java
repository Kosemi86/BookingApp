package com.example.test1.Model;

public class Appointment {
    private long id;
    private long barberId;
    private String appointmentRating;
    private String appointmentDetails;

    public Appointment(long id, long barberId, String appointmentRating, String appointmentDetails) {
        this.id = id;
        this.barberId = barberId;
        this.appointmentRating = appointmentRating;
        this.appointmentDetails = appointmentDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBarberId() {
        return barberId;
    }

    public void setBarberId(long barberId) {
        this.barberId = barberId;
    }

    public String getAppointmentRating() {
        return appointmentRating;
    }

    public void setAppointmentRating(String appointmentRating) {
        this.appointmentRating = appointmentRating;
    }

    public String getAppointmentDetails() {
        return appointmentDetails;
    }

    public void setAppointmentDetails(String appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
    }
}
