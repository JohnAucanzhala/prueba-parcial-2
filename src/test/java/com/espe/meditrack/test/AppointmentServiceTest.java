package com.espe.meditrack.service;

import com.espe.meditrack.model.Appointment;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppointmentServiceTest {

    @Test
    public void getValidAppointments_conCitasValidasEInvalidas_emiteSoloLasTresValidas() {
        AppointmentService service = new AppointmentService();
        Flux<Appointment> flujo = service.getValidAppointments();

        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void getValidAppointments_todasLasCitasInvalidas_emiteSoloLaGenerica() {
        List<Appointment> todasInvalidas = Arrays.asList(
                new Appointment("X1", "Nadie", "Rayos X", 0.0, Arrays.asList("x@mail.com")),
                new Appointment("X2", "Nadie2", "Rayos X", 10.0, Collections.emptyList())
        );
        AppointmentService service = new AppointmentService(todasInvalidas);

        Flux<Appointment> flujo = service.getValidAppointments();

        StepVerifier.create(flujo)
                .expectNextMatches(cita -> cita.getId().equals("SIN_CITAS"))
                .verifyComplete();
    }

    @Test
    public void findById_idExistente_emiteLaCitaCorrespondiente() {
        AppointmentService service = new AppointmentService();

        StepVerifier.create(service.findById("A1"))
                .expectNextMatches(cita -> cita.getId().equals("A1"))
                .verifyComplete();
    }

    @Test
    public void findById_idInexistente_terminaEnError() {
        AppointmentService service = new AppointmentService();

        StepVerifier.create(service.findById("NO_EXISTE"))
                .expectError(AppointmentNotFoundException.class)
                .verify();
    }
}