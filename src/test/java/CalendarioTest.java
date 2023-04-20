import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

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
    public void seModificaElTituloDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);

        c.modificarEvento(evento, Calendario.Elementos.TITULO, "nuevo titulo", "", null);

        assertEquals("nuevo titulo", evento.getTitulo());
    }

    @Test
    public void seModificaElTituloDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);

        c.modificarTarea(tarea, Calendario.Elementos.TITULO,"Hacer compras", null);

        assertEquals("Hacer compras",tarea.getTitulo());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaDescripcionDeUnaTareaCorrectamente() {
        var c = new Calendario();

        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);
        c.modificarTarea(tarea, Calendario.Elementos.DESCRIPCION, "nueva descripcion", null);

        assertEquals("nueva descripcion", tarea.getDescripcion());

    }

    @Test
    public void seModificaLaDescripcionDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "primera descripcion", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);

        c.modificarEvento(evento, Calendario.Elementos.DESCRIPCION, "segunda descripcion", "", null);

        assertEquals("segunda descripcion", evento.getDescripcion());
    }

    @Test
    public void seModificaLaFechaDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);
        LocalDate fechaNueva = LocalDate.of(2023, 10, 5);

        c.modificarTarea(tarea, Calendario.Elementos.FECHA, "5/10/2023", null);

        assertEquals(fechaNueva, tarea.getFecha());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaFechaDeUnEventoCorrectamente() {
        var c = new Calendario();

        var evento1 = c.crearEvento("evento1", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento1, Calendario.Elementos.FECHA, "", "2/6/2023", null);
        LocalDate fechaInicio1 = LocalDate.of(2023, 1, 1);
        LocalDate fechaFin1 = LocalDate.of(2023, 6, 2);
        assertEquals(fechaInicio1, evento1.getFechaInicio());
        assertEquals(fechaFin1, evento1.getFechaFin());

        var evento2 = c.crearEvento("evento2", "", "5/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento2, Calendario.Elementos.FECHA, "10/10/2023", "", null);
        LocalDate fechaInicio2 = LocalDate.of(2023, 10, 10);
        LocalDate fechaFin2 = LocalDate.of(2023, 1, 30);
        assertEquals(fechaInicio2, evento2.getFechaInicio());
        assertEquals(fechaFin2, evento2.getFechaFin());

        var evento3 = c.crearEvento("evento3", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento3, Calendario.Elementos.FECHA, "10/10/2023", "2/6/2023", null);
        assertEquals(fechaInicio2, evento3.getFechaInicio());
        assertEquals(fechaFin1, evento3.getFechaFin());
    }

    @Test
    public void seModificaLaHoraDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"20:30", null);

        c.modificarTarea(tarea, Calendario.Elementos.HORARIO, "15:30", null);

        assertEquals("15:30", tarea.getHorario().toString());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaHoraDeUnEventoCorrectamente() {
        var c = new Calendario();

        var evento1 = c.crearEvento("evento1", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento1, Calendario.Elementos.HORARIO, "", "01:00", null);
        LocalTime horaInicio1 = LocalTime.MIDNIGHT;
        LocalTime horaFin1 = LocalTime.of(1,0);
        assertEquals(horaInicio1, evento1.getHorarioInicio());
        assertEquals(horaFin1, evento1.getHorarioFin());

        var evento2 = c.crearEvento("evento2", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento2, Calendario.Elementos.HORARIO, "00:15", "", null);
        LocalTime horaInicio2 = LocalTime.of(0,15);
        LocalTime horaFin2 = LocalTime.of(0,30);
        assertEquals(horaInicio2, evento2.getHorarioInicio());
        assertEquals(horaFin2, evento2.getHorarioFin());

        var evento3 = c.crearEvento("evento3", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento3, Calendario.Elementos.HORARIO, "00:15", "01:00", null);
        assertEquals(horaInicio2, evento3.getHorarioInicio());
        assertEquals(horaFin1, evento3.getHorarioFin());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        LocalDate fechaInicio = LocalDate.of(2023,1,1);
        FrecuenciaC frecuencia = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fechaInicio, 3, 3);
        c.modificarEvento(evento, Calendario.Elementos.FRECUENCIA, "", "", frecuencia);

        assertEquals(frecuencia, evento.getFrecuencia());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"20:30", null);
        LocalDate fecha = LocalDate.of(2023,5,2);
        FrecuenciaC frecuencia = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fecha, 3, 3);
        c.modificarTarea(tarea, Calendario.Elementos.FRECUENCIA, "", frecuencia);

        assertEquals(frecuencia, tarea.getFrecuencia());
    }
}
