import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        assertEquals("2023-05-02",tarea.getFechaInicio().toString());

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
    }

    @Test
    public void seModificaElTituloDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);

        c.modificarEvento(evento, Calendario.Modificar.TITULO, null, "nuevo titulo", null);

        assertEquals("nuevo titulo", evento.getTitulo());
    }

    @Test
    public void seModificaElTituloDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);
        c.modificarTarea(tarea, Calendario.Modificar.TITULO, "Nuevo Titulo", null);

        assertEquals("Nuevo Titulo",tarea.getTitulo());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaDescripcionDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);

        c.modificarTarea(tarea, Calendario.Modificar.DESCRIPCION, "nueva descripcion", null);

        assertEquals("nueva descripcion", tarea.getDescripcion());

    }

    @Test
    public void seModificaLaDescripcionDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "primera descripcion", "1/1/2023", "1/1/2023", "00:00", "00:30" , false, null);

        c.modificarEvento(evento, Calendario.Modificar.DESCRIPCION, null, "segunda descripcion", null);

        assertEquals("segunda descripcion", evento.getDescripcion());
    }

    @Test
    public void seModificaLaFechaDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"", null);
        LocalDate fechaNueva = LocalDate.of(2023, 10, 5);

        c.modificarTarea(tarea, Calendario.Modificar.FECHA, "5/10/2023", null);

        assertEquals(fechaNueva, tarea.getFechaInicio());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaFechaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento1", "", "1/1/2023", "30/1/2023", "00:00", "00:30", false, null);

        c.modificarEvento(evento, Calendario.Modificar.FECHA, Calendario.Opcion.FIN, "2/6/2023", null);

        LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
        LocalDate fechaEsperada = LocalDate.of(2023, 6, 2);
        LocalDate fechafin = fechaEsperada;

        assertEquals(fechaEsperada, evento.getFechaFin());
        assertEquals(fechaInicio, evento.getFechaInicio());

        c.modificarEvento(evento, Calendario.Modificar.FECHA, Calendario.Opcion.INICIO, "5/2/2023", null);
        fechaEsperada = LocalDate.of(2023, 2, 5);

        assertEquals(fechafin, evento.getFechaFin());
        assertEquals(fechaEsperada, evento.getFechaInicio());
    }

    @Test
    public void seModificaLaHoraDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"20:30", null);

        c.modificarTarea(tarea, Calendario.Modificar.HORARIO, "15:30", null);

        assertEquals("15:30", tarea.getHoraInicio().toString());
        assertEquals(1, c.cantidadTareas());
        assertEquals(true, c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaHoraDeUnEventoCorrectamente() {
        var c = new Calendario();

        var evento1 = c.crearEvento("evento1", "", "1/1/2023", "30/1/2023", "10:00", "12:30" , false, null);
        c.modificarEvento(evento1, Calendario.Modificar.HORARIO, Calendario.Opcion.INICIO, "11:00", null);

        LocalTime horaEsperada = LocalTime.of(11, 0);
        LocalTime horaFin = LocalTime.of(12,30);
        LocalTime horaInicio = horaEsperada;

        assertEquals(horaEsperada, evento1.getHoraInicio());
        assertEquals(horaFin, evento1.getHoraFin());

        c.modificarEvento(evento1, Calendario.Modificar.HORARIO, Calendario.Opcion.FIN, "13:45", null);
        horaEsperada = LocalTime.of(13, 45);

        assertEquals(horaEsperada, evento1.getHoraFin());
        assertEquals(horaInicio, evento1.getHoraInicio());
    }

    @Test
    public void seMarcaUnEventoTodoElDiaCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.modificarEvento(evento, Calendario.Modificar.DIACOMPLETO, null, "", null);
        LocalDate fechaInicio = LocalDate.of(2023,1,1);
        LocalDate fechaFin = LocalDate.of(2023,1,30);

        assertEquals(fechaInicio, evento.getFechaInicio());
        assertEquals(fechaFin, evento.getFechaFin());
        assertEquals(LocalTime.MIN, evento.getHoraInicio());
        assertEquals(LocalTime.MAX, evento.getHoraFin());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);

        LocalDate fechaInicio = LocalDate.of(2023,1,1);
        Frecuencia frecuencia = new Frecuencia(new FrecuenciaDiaria(), fechaInicio, 3, 3);

        c.modificarEvento(evento, Calendario.Modificar.FRECUENCIA, null, "", frecuencia);

        assertEquals(frecuencia, evento.getFrecuencia());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var tarea = c.crearTarea("tarea1", "", "2/5/2023",true,"20:30", null);
        LocalDate fecha = LocalDate.of(2023,5,2);

        Frecuencia frecuencia = new Frecuencia(new FrecuenciaDiaria(), fecha, 3, null);
        c.modificarTarea(tarea, Calendario.Modificar.FRECUENCIA, "", frecuencia);

        assertEquals(frecuencia, tarea.getFrecuencia());
    }

    @Test
    public void seAgregaUnaAlarmaAUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento", "", "2/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        c.agregarAlarmaEvento(evento, "", "", 2, Alarma.UnidadesDeTiempo.HORAS, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(1, evento.cantidadAlarmas());

        c.agregarAlarmaEvento(evento, "1/1/2023", "23:30", 0, null, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(2, evento.cantidadAlarmas());
    }

    @Test
    public void seDestruyeUnaAlarmaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var evento = c.crearEvento("evento", "", "1/1/2023", "30/1/2023", "00:00", "00:30" , false, null);
        var alarma = c.agregarAlarmaEvento(evento, "", "", 2, Alarma.UnidadesDeTiempo.HORAS, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(1, evento.cantidadAlarmas());
        c.destruirAlarmaEvento(evento, alarma);
        assertEquals(0, evento.cantidadAlarmas());
    }
}
