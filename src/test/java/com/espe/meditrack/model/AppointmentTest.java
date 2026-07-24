package com.espe.meditrack.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class AppointmentTest {

    @Test
    public void getters_datosValidos_devuelvenLoQueSeEnvioAlConstructor() {
        List<String> correos = new ArrayList<>();
        correos.add("test@mail.com");
        Appointment cita = new Appointment("A1", "Pedro Ramos", "Cardiologia", 20.0, correos);
        String id = cita.getId();

        assertEquals("A1", id);
        assertEquals("Pedro Ramos", cita.getPatientName());
        assertEquals("Cardiologia", cita.getSpecialty());
        assertEquals(20.0, cita.getCostUsd(), 0.0001);
        assertEquals(1, cita.getNotifyEmails().size());
    }

    @Test
    public void constructor_modificaListaOriginalDespuesDeCrear_noAfectaAlObjeto() {
        List<String> correosOriginales = new ArrayList<>();
        correosOriginales.add("uno@mail.com");
        Appointment cita = new Appointment("A2", "Laura Diaz", "Pediatria", 15.0, correosOriginales);

        correosOriginales.add("dos@mail.com");

        assertEquals(1, cita.getNotifyEmails().size());
    }

    @Test
    public void getNotifyEmails_llamadoDosVeces_devuelveReferenciasDistintas() {
        List<String> correos = new ArrayList<>();
        correos.add("a@mail.com");
        Appointment cita = new Appointment("A3", "Sofia Leon", "Dermatologia", 10.0, correos);

        List<String> primeraLlamada = cita.getNotifyEmails();
        List<String> segundaLlamada = cita.getNotifyEmails();
        assertNotSame(primeraLlamada, correos);
        assertNotSame(primeraLlamada, segundaLlamada);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNotifyEmails_intentaAgregarCorreo_lanzaExcepcion() {
        List<String> correos = new ArrayList<>();
        correos.add("a@mail.com");
        Appointment cita = new Appointment("A4", "Diego Salas", "Neurologia", 10.0, correos);

        cita.getNotifyEmails().add("intruso@mail.com");

    }
}