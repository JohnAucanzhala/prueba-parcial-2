package com.espe.meditrack.service;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String id) {
        super("No se encontro la cita con id: " + id);
    }
}