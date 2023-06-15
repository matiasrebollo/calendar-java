import com.fasterxml.jackson.databind.ObjectMapper;
import org.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

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
        var fecha = LocalDate.of(2023,1,1);
        var evento = c.crearEvento("evento1", "", fecha, fecha, "00:00", "00:30" , false, null);

        assertEquals(1, c.cantidadEventos());
        assertTrue(c.existeEvento(evento));
    }

    @Test
    public void seCreaUnaTareaCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,5,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true, "", null);

        assertEquals(1, c.cantidadTareas());
        assertTrue(c.existeTarea(tarea));
        assertEquals("tarea1", tarea.getTitulo());
        assertEquals("", tarea.getDescripcion());
        assertEquals("2023-05-02",tarea.getFechaInicio().toString());

    }

    @Test
    public void seEliminaUnEventoCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var evento = c.crearEvento("evento1", "", fecha, fecha, "00:00", "00:30" , false, null);
        c.eliminarEvento(evento);

        assertEquals(0, c.cantidadEventos());
        assertFalse(c.existeEvento(evento));
    }

    @Test
    public void seEliminaUnaTareaCorretamente(){
        var c = new Calendario();

        var fecha = LocalDate.of(2023,1,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true, "", null);

        c.eliminarTarea(tarea);
        assertEquals(0, c.cantidadTareas());
        assertFalse(c.existeTarea(tarea));
    }

    @Test
    public void seModificaElTituloDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var evento = c.crearEvento("evento1", "", fecha, fecha, "00:00", "00:30" , false, null);

        evento.setTitulo("nuevo titulo");

        assertEquals("nuevo titulo", evento.getTitulo());
    }

    @Test
    public void seModificaElTituloDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true,"", null);
        tarea.setTitulo("Nuevo Titulo");

        assertEquals("Nuevo Titulo",tarea.getTitulo());
        assertEquals(1, c.cantidadTareas());
        assertTrue(c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaDescripcionDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,5,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true,"", null);

        tarea.setDescripcion("nueva descripcion");

        assertEquals("nueva descripcion", tarea.getDescripcion());

    }

    @Test
    public void seModificaLaDescripcionDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var evento = c.crearEvento("evento1", "primera descripcion", fecha, fecha, "00:00", "00:30" , false, null);

        evento.setDescripcion("segunda descripcion");

        assertEquals("segunda descripcion", evento.getDescripcion());
    }

    @Test
    public void seModificaLaFechaDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var fecha = LocalDate.of(2023,5,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true,"", null);
        LocalDate fechaNueva = LocalDate.of(2023, 10, 5);

        tarea.setFechaInicio(fechaNueva);

        assertEquals(fechaNueva, tarea.getFechaInicio());
        assertEquals(1, c.cantidadTareas());
        assertTrue(c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaFechaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fechaInicio = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);
        var evento = c.crearEvento("evento1", "", fechaInicio, fechaFin, "00:00", "00:30", false, null);
        LocalDate fechaEsperada = LocalDate.of(2023, 6, 2);

        evento.setFechaFin(fechaEsperada);


        LocalDate fechafin = fechaEsperada;

        assertEquals(fechaEsperada, evento.getFechaFin());
        assertEquals(fechaInicio, evento.getFechaInicio());

        LocalDate fechaInicioEsperada = LocalDate.of(2023, 2, 5);
        evento.setFechaInicio(fechaInicioEsperada);


        assertEquals(fechafin, evento.getFechaFin());
        assertEquals(fechaInicioEsperada, evento.getFechaInicio());
    }

    @Test
    public void seModificaLaHoraDeUnaTareaCorrectamente(){
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true,"20:30", null);

        tarea.setHoraInicio(LocalTime.of(15,30));

        assertEquals(LocalTime.of(15,30), tarea.getHoraInicio());
        assertEquals(1, c.cantidadTareas());
        assertTrue(c.existeTarea(tarea));
    }

    @Test
    public void seModificaLaHoraDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);
        var evento1 = c.crearEvento("evento1", "", fecha, fechaFin, "10:00", "12:30" , false, null);
        evento1.setHoraInicio(LocalTime.of(11,0));

        LocalTime horaEsperada = LocalTime.of(11, 0);
        LocalTime horaFin = LocalTime.of(12,30);
        LocalTime horaInicio = horaEsperada;

        assertEquals(horaEsperada, evento1.getHoraInicio());
        assertEquals(horaFin, evento1.getHoraFin());

        evento1.setHoraFin(LocalTime.of(13,45));
        horaEsperada = LocalTime.of(13, 45);

        assertEquals(horaEsperada, evento1.getHoraFin());
        assertEquals(horaInicio, evento1.getHoraInicio());
    }

    @Test
    public void seMarcaUnEventoTodoElDiaCorrectamente() {
        var c = new Calendario();
        var fechaInicio = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);

        var evento = c.crearEvento("evento", "", fechaInicio, fechaFin, "00:00", "00:30" , false, null);
        evento.marcarTodoElDia();
        assertEquals(fechaInicio, evento.getFechaInicio());
        assertEquals(fechaFin, evento.getFechaFin());
        assertEquals(LocalTime.MIN, evento.getHoraInicio());
        assertEquals(LocalTime.MAX, evento.getHoraFin());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fechaInicio = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);
        var evento = c.crearEvento("evento", "", fechaInicio, fechaFin, "00:00", "00:30" , false, null);

        var frecuencia = new FrecuenciaDiaria(fechaInicio, 3, 3);

        evento.setFrecuencia(frecuencia);

        assertEquals(frecuencia, evento.getFrecuencia());
    }

    @Test
    public void seModificaLaFrecuenciaDeUnaTareaCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,2);
        var tarea = c.crearTarea("tarea1", "", fecha,true,"20:30", null);
        LocalDate fecha2 = LocalDate.of(2023,5,2);

        var frecuencia = new FrecuenciaDiaria(fecha2, 3, null);
        tarea.setFrecuencia(frecuencia);

        assertEquals(frecuencia, tarea.getFrecuencia());
    }

    @Test
    public void seAgregaUnaAlarmaAUnEventoCorrectamente() {
        var c = new Calendario();
        var fechaInicio = LocalDate.of(2023,1,2);
        var fechaFin = LocalDate.of(2023,1,30);
        var evento = c.crearEvento("evento", "", fechaInicio, fechaFin, "00:00", "00:30" , false, null);
        evento.agregarAlarma(null, 2, Alarma.UnidadesDeTiempo.HORAS, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(1, evento.cantidadAlarmas());

        LocalDateTime fechaYHora = LocalDateTime.of(LocalDate.of(2023,1,1), LocalTime.of(23,30));
        evento.agregarAlarma(fechaYHora, 0, null, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(2, evento.cantidadAlarmas());
    }

    @Test
    public void seDestruyeUnaAlarmaDeUnEventoCorrectamente() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);

        var evento = c.crearEvento("evento", "", fecha, fechaFin, "00:00", "00:30" , false, null);
        var alarma = evento.agregarAlarma(null, 2, Alarma.UnidadesDeTiempo.HORAS, Alarma.EfectosAlarma.NOTIFICACION);
        assertEquals(1, evento.cantidadAlarmas());
        evento.destruirAlarma(alarma);
        assertEquals(0, evento.cantidadAlarmas());
    }

    @Test
    public void siModificoUnEventoEsteSeActualizaEnElArray() {
        var c = new Calendario();
        var fecha = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);
        var evento = c.crearEvento("evento", "", fecha, fechaFin, "00:00", "00:30" , false, null);
        assertEquals(true, c.existeEvento(evento));
        evento.setTitulo("nuevo titulo");
        assertEquals("nuevo titulo", evento.getTitulo());
        assertEquals(true, c.existeEvento(evento));
    }

    @Test
    public void serializacion() throws IOException {
        var c = new Calendario();

        LocalDateTime fechaHoraEvento = LocalDateTime.of(2023,1,1,8,0);
        var fechaInicio = LocalDate.of(2023,1,1);
        var fechaFin = LocalDate.of(2023,1,30);
        var fechaTarea = LocalDate.of(2023,2,2);

        Frecuencia f = new FrecuenciaDiaria(fechaHoraEvento.toLocalDate(),2,10);
        var evento = c.crearEvento("evento1", "", fechaInicio, fechaFin, "08:00", "10:30" , false, f);
        var tarea1 = c.crearTarea("org.Tarea 1", "es la primer tarea",fechaTarea,true, "", null);
        evento.agregarAlarma(null,5, Alarma.UnidadesDeTiempo.MINUTOS, Alarma.EfectosAlarma.NOTIFICACION);
        c.serializar(new ObjectMapper(), "DatosTest.json");

        assertTrue(Files.exists(Path.of("DatosTest.json")));//existe el archivo

        String contenido = Files.readString(Path.of("DatosTest.json"));
        assertNotNull(contenido);
    }

    @Test
    public void deserializacion() throws IOException {
        LocalDateTime fechaHoraEvento = LocalDateTime.of(2023,1,1,8,0);
        LocalDateTime fechaHoraFinEvento = LocalDateTime.of(2023,1,30,10,30);
        LocalDate fechaTarea = LocalDate.of(2023,2,2);
        Frecuencia f = new FrecuenciaDiaria(fechaHoraEvento.toLocalDate(),2,10);
        var eventoOriginal = new Evento("evento1","",fechaHoraEvento,fechaHoraFinEvento,
                                    false,f);
        var tareaOriginal = new Tarea("org.Tarea 1", "es la primer tarea", fechaTarea, true, null, null);

        var c = Calendario.deserializar(new ObjectMapper(), "DatosTest.json");

        assertNotEquals(null,c);
        assertEquals(1,c.cantidadEventos());
        assertEquals(1,c.cantidadTareas());

        var e1 = c.obtenerEvento(0);
        var t1 = c.obtenerTarea(0);

        assertEquals(eventoOriginal.getTitulo(), e1.getTitulo());
        assertEquals(eventoOriginal.getDescripcion(), e1.getDescripcion());
        assertEquals(eventoOriginal.getFechaInicio(), e1.getFechaInicio());
        assertEquals(eventoOriginal.getFechaFin(), e1.getFechaFin());
        assertEquals(tareaOriginal.getTitulo(), t1.getTitulo());
        assertEquals(tareaOriginal.getDescripcion(), t1.getDescripcion());
        assertEquals(tareaOriginal.getFechaInicio(), t1.getFechaInicio());
        assertEquals(tareaOriginal.getHoraInicio(), t1.getHoraInicio());
    }
}
