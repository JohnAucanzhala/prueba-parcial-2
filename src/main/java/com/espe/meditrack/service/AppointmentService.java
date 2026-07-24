package com.espe.meditrack.service;

import com.espe.meditrack.model.Appointment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppointmentService {

    private final List<Appointment> appointments;
    public AppointmentService() {
        this.appointments = Arrays.asList(
                new Appointment("A1", "Juan Perez", "Cardiologia", 25.0, Arrays.asList("juan@mail.com")),
                new Appointment("A2", "Maria Lopez", "Pediatria", 15.0, Arrays.asList("maria@mail.com", "papa@mail.com")),
                new Appointment("A3", "Carlos Ruiz", "Dermatologia", 30.0, Arrays.asList("carlos@mail.com")),
                new Appointment("A4", "Ana Torres", "Neurologia", 0.0, Arrays.asList("ana@mail.com")),      // invalida: costo 0
                new Appointment("A5", "Luis Vera", "Oftalmologia", 20.0, Collections.emptyList())            // invalida: sin correos
        );
    }

    public AppointmentService(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Flux<Appointment> getValidAppointments() {
        return Flux.fromIterable(appointments)

                .filter(a -> a.getCostUsd() != null && a.getCostUsd() > 0
                        && a.getNotifyEmails() != null && !a.getNotifyEmails().isEmpty())

                .map(a -> new Appointment(a.getId(), a.getPatientName(), a.getSpecialty().toUpperCase(),
                        a.getCostUsd(), a.getNotifyEmails()))

                .defaultIfEmpty(new Appointment("SIN_CITAS", "N/A", "GENERAL", 0.0, Collections.emptyList()));
    }

    public Mono<Appointment> findById(String id) {
        return getValidAppointments()
                .filter(a -> a.getId().equals(id))
                .next()
                .switchIfEmpty(Mono.error(new AppointmentNotFoundException(id)));
    }
}