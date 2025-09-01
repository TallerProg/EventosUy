package ServidorCentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServidorCentral.logica.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

class AltaEdicionEventoTest {
    private IControllerEvento controller;
    private Evento eventoBase;
    private Organizador organizador;

    @BeforeEach
    void setUp() {
        controller = new ControllerEvento(); 
        eventoBase = new Evento(
            "ConferenciaX", 
            "C-2025", 
            "Evento de prueba", 
            LocalDate.now(), 
            new ArrayList<>()
        );
        organizador = new Organizador(
            "org1", 
            "org1@mail.com", 
            "Org Principal", 
            "Organizador Principal"
        );
    }

    @Test
    void testAltaEdicionValida() {
        assertDoesNotThrow(() -> {
            controller.altaEdicionDeEvento(
                "Edicion2025",     
                "ED2025",          
                "Montevideo",      
                "Uruguay",         
                LocalDate.of(2025, 5, 1),   
                LocalDate.of(2025, 5, 10),  
                LocalDate.of(2025, 4, 20),  
                eventoBase,
                organizador
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
            eventoBase, organizador
        );

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            controller.altaEdicionDeEvento(
                "Edicion2025", "ED2025",
                "Montevideo", "Uruguay",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2025, 4, 20), 
                eventoBase, organizador
            );
        });

        assertTrue(ex.getMessage().contains("Ya existe"));
    }

    @Test
    void testAltaEdicionFechasInvalidas() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            controller.altaEdicionDeEvento(
                "Edicion2026", "ED2026",
                "Montevideo", "Uruguay",
                LocalDate.of(2025, 5, 10),  
                LocalDate.of(2025, 5, 1),   
                LocalDate.of(2025, 4, 20),  
                eventoBase,
                organizador
            );
        });

        assertTrue(ex.getMessage().toLowerCase().contains("fecha"));
    }
}
