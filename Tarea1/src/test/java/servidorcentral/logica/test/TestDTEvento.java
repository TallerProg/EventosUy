package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.Categoria;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.DTevento;

class DTEventoTest {

    @Test
    @DisplayName("Getters básicos")
    void testGettersBasicos() {
        String nombre = "Conferencia Salud";
        String sigla = "CS2024";
        String desc = "Evento de salud";
        LocalDate alta = LocalDate.of(2024, 7, 1);
        String img = "evento.png";

        DTevento dto = new DTevento(nombre, sigla, desc, alta, null, null, img, false);

        assertEquals(nombre, dto.getNombre());
        assertEquals(sigla, dto.getSigla());
        assertEquals(desc, dto.getDescripcion());
        assertEquals(alta, dto.getFAlta());
        assertEquals(img, dto.getImg());
    }

    @Test
    @DisplayName("Listas null -> vacías")
    void testListasNullVacias() {
        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), null, null, "img.png", false);

        assertNotNull(dto.getCategorias());
        assertTrue(dto.getCategorias().isEmpty());

        assertNotNull(dto.getEdiciones());
        assertTrue(dto.getEdiciones().isEmpty());

        assertNotNull(dto.getDtCategorias());
        assertTrue(dto.getDtCategorias().isEmpty());

        assertNotNull(dto.getDtEdiciones());
        assertTrue(dto.getDtEdiciones().isEmpty());
    }

    @Test
    @DisplayName("Conserva referencias de listas no nulas")
    void testConservaReferencias() {
        List<Categoria> categorias = new ArrayList<>();
        List<servidorcentral.logica.Edicion> ediciones = new ArrayList<>();

        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), categorias, ediciones, "img.png", false);

        assertSame(categorias, dto.getCategorias());
        assertSame(ediciones, dto.getEdiciones());
    }

    @Test
    @DisplayName("getDTCategorias mapea Categoria -> DTCategoria")
    void testGetDTCategoriasMapea() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Tecnología"));
        categorias.add(new Categoria("Salud"));

        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), categorias, null, "img.png", false);
        List<DTCategoria> dts = dto.getDtCategorias();

        assertEquals(2, dts.size());
        assertEquals("Tecnología", dts.get(0).getNombre());
        assertEquals("Salud", dts.get(1).getNombre());
    }

    @Test
    @DisplayName("getDTEdiciones vacío cuando no hay ediciones")
    void testGetDTEdicionesVacio() {
        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), null, null, "img.png", false);
        List<DTEdicion> dts = dto.getDtEdiciones();
        assertNotNull(dts);
        assertTrue(dts.isEmpty());
    }
}
