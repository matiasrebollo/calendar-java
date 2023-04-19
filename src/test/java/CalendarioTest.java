import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CalendarioTest {

    @Test
    public void elCalendarioRecienCreadoNoTieneEventosNiTareas(){
        var calendario = new Calendario();
        assertEquals(0, calendario.cantidadEventos());
        assertEquals(0, calendario.cantidadTareas());
    }

    @Test
    public void seCreaUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);

        assertEquals(1, c.cantidadEventos());
        assertEquals(true, c.existeEvento(evento));
    }

    @Test
    public void seCreaUnaTareaCorrectamente() {
        var c = new Calendario();

        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true, "", null);

        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
        assertEquals("tarea1", tarea.getTitulo());
        assertEquals("", tarea.getDescripcion());
        assertEquals("2023-05-02",tarea.getFecha().toString());
    }

    @Test
    public void seEliminaUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);
        c.eliminarEvento(evento);

        assertEquals(0, c.cantidadEventos());
        assertEquals(false, c.existeEvento(evento));
    }

    @Test
    public void seEliminaUnaTareaCorretamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true, "", null);

        c.eliminarTarea(tarea);
        assertEquals(0, c.cantidadTareas());
        assertEquals(false, c.existeTarea(tarea));

        //assertEquals(null, tarea);
    }
    @Test
    public void modificoElTituloDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);

        c.modificarTarea(tarea, Calendario.Elementos.TITULO,"Hacer compras");

        assertEquals("Hacer compras",tarea.getTitulo());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }
    @Test
    public void seModificaLaFechaDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);
        LocalDate fechaNueva = LocalDate.of(2023, 10, 5);

        c.modificarTarea(tarea, Calendario.Elementos.FECHA, "5/10/2023");

        assertEquals(fechaNueva, tarea.getFecha());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }
    @Test
    public void seModificaLaHoraDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"20:30", null);

        c.modificarTarea(tarea, Calendario.Elementos.HORARIO, "15:30");

        assertEquals("15:30", tarea.getHorario().toString());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }


}
