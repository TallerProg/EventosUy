package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

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

        DTevento dto = new DTevento(nombre, sigla, desc, alta, null, null, img);

        assertEquals(nombre, dto.getNombre());
        assertEquals(sigla, dto.getSigla());
        assertEquals(desc, dto.getDescripcion());
        assertEquals(alta, dto.getFAlta());
        assertEquals(img, dto.getImg());
    }

    @Test
    @DisplayName("Listas null -> vacías")
    void testListasNullVacias() {
        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), null, null, "img.png");

        assertNotNull(dto.getCategorias());
        assertTrue(dto.getCategorias().isEmpty());

        assertNotNull(dto.getEdiciones());
        assertTrue(dto.getEdiciones().isEmpty());

        assertNotNull(dto.getDTCategorias());
        assertTrue(dto.getDTCategorias().isEmpty());

        assertNotNull(dto.getDTEdiciones());
        assertTrue(dto.getDTEdiciones().isEmpty());
    }

    @Test
    @DisplayName("Conserva referencias de listas no nulas")
    void testConservaReferencias() {
        List<Categoria> categorias = new ArrayList<>();
        List<servidorcentral.logica.Edicion> ediciones = new ArrayList<>();

        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), categorias, ediciones, "img.png");

        assertSame(categorias, dto.getCategorias());
        assertSame(ediciones, dto.getEdiciones());
    }

    @Test
    @DisplayName("getDTCategorias mapea Categoria -> DTCategoria")
    void testGetDTCategoriasMapea() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria("Tecnología"));
        categorias.add(new Categoria("Salud"));

        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), categorias, null, "img.png");
        List<DTCategoria> dts = dto.getDTCategorias();

        assertEquals(2, dts.size());
        assertEquals("Tecnología", dts.get(0).getNombre());
        assertEquals("Salud", dts.get(1).getNombre());
    }

    @Test
    @DisplayName("getDTEdiciones vacío cuando no hay ediciones")
    void testGetDTEdicionesVacio() {
        DTevento dto = new DTevento("E", "S", "D", LocalDate.now(), null, null, "img.png");
        List<DTEdicion> dts = dto.getDTEdiciones();
        assertNotNull(dts);
        assertTrue(dts.isEmpty());
    }
}
