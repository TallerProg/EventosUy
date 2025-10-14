package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.ControllerEvento;
import servidorcentral.logica.Evento;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.Organizador;

class AltaEdicionEventoTest {
    private IControllerEvento controller;
    private Evento eventoBase;
    private Organizador organizador;

    @BeforeEach
    void setUp() {
        controller = new ControllerEvento();

        // Evento: constructor con 'img' al final
        eventoBase = new Evento(
            "ConferenciaX",
            "C-2025",
            "Evento de prueba",
            LocalDate.now(),
            new ArrayList<>(),
            "evento.png"
        );

        // Organizador: (nickname, correo, nombre, descripcion, contrasena, img)
        organizador = new Organizador(
            "org1",
            "org1@mail.com",
            "Org Principal",
            "Organizador Principal",
            "1234",
            "org.png"
        );
    }

    @Test
    void testAltaEdicionValida() {
        assertDoesNotThrow(() -> {
            controller.altaEdicionDeEvento(
                "Edicion2025",                // nombre
                "ED2025",                     // sigla
                "Montevideo",                 // ciudad
                "Uruguay",                    // pais
                LocalDate.of(2025, 5, 1),     // fInicio
                LocalDate.of(2025, 5, 10),    // fFin
                LocalDate.of(2025, 4, 20),    // fAlta
                eventoBase,
                organizador,
                "Ruta 1"                      // imagenWebPath
            );
        });
    }

    @Test
    void testAltaEdicionDuplicada() throws Exception {
        controller.altaEdicionDeEvento(
            "Edicion2025", "ED2025",
            "Montevideo", "Uruguay",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 10),
            LocalDate.of(2025, 4, 20),
            eventoBase, organizador, "Ruta 1"
        );

        Exception exe = assertThrows(IllegalArgumentException.class, () -> {
            controller.altaEdicionDeEvento(
                "Edicion2025", "ED2025",
                "Montevideo", "Uruguay",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2025, 4, 20),
                eventoBase, organizador, "Ruta 1"
            );
        });

        assertTrue(exe.getMessage().contains("Ya existe"));
    }

    @Test
    void testAltaEdicionFechasInvalidas() {
        Exception exe = assertThrows(IllegalArgumentException.class, () -> {
            controller.altaEdicionDeEvento(
                "Edicion2026", "ED2026",
                "Montevideo", "Uruguay",
                LocalDate.of(2025, 5, 10),   // inicio > fin
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 4, 20),
                eventoBase,
                organizador, "Ruta 1"
            );
        });

        assertTrue(exe.getMessage().toLowerCase().contains("fecha"));
    }
}

