import org.Alarma;
import org.Evento;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class AlarmaTest {
    private Alarma crearAlarma() {
        // Crear una instancia de org.Alarma 5 minutos antes
        LocalDateTime fechaHoraEvento = LocalDateTime.of(2023, 4, 30, 12, 0); // Ejemplo de fecha y hora de evento
        var evento = new Evento("titulo", "",fechaHoraEvento,fechaHoraEvento,false,null);
        int intervalo = 5; // Ejemplo de intervalo
        Alarma.UnidadesDeTiempo unidad = Alarma.UnidadesDeTiempo.MINUTOS; // Ejemplo de unidad de tiempo
        Alarma.EfectosAlarma efecto = Alarma.EfectosAlarma.NOTIFICACION; // Ejemplo de efecto de alarma
        return new Alarma(evento, intervalo, unidad, efecto);
    }

    @Test
    public void testCalcularFechaHoraAlarma() {
        var alarma = crearAlarma();
        LocalDateTime fechaHoraAlarmaEsperada = LocalDateTime.of(2023, 4, 30, 11, 55); // El resultado esperado del cálculo
        assertEquals(fechaHoraAlarmaEsperada, alarma.getFechaHoraAlarma());
    }

    @Test
    public void testActualizarFechaHoraAlarma() {
        var alarma = crearAlarma();
        LocalDateTime nuevaFechaHoraEvento = LocalDateTime.of(2023, 5, 1, 8, 0); // Nueva fechaHoraEvento
        alarma.actualizarFechaHoraAlarma(nuevaFechaHoraEvento);
        LocalDateTime fechaHoraAlarmaEsperada = LocalDateTime.of(2023, 5, 1, 7, 55); // El resultado esperado del cálculo
        assertEquals(fechaHoraAlarmaEsperada, alarma.getFechaHoraAlarma());
    }
    @Test
    public void alarmaEnviaNotificacion() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        var evento = new Evento("","",fechaHora,fechaHora,false,null);
        Alarma a = new Alarma(evento,fechaHora, Alarma.EfectosAlarma.NOTIFICACION);

        int devuelto = a.reproducirEfecto();
        assertEquals(0, devuelto);
    }
    @Test
    public void alarmaReproduceSonido() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        var evento = new Evento("", "",fechaHora,fechaHora,false,null);
        Alarma a = new Alarma(evento,fechaHora, Alarma.EfectosAlarma.SONIDO);

        int devuelto = a.reproducirEfecto();
        assertEquals(1, devuelto);
    }
    @Test
    public void alarmaEnviaEmail() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        var evento = new Evento("", "",fechaHora,fechaHora,false,null);
        Alarma a = new Alarma(evento,fechaHora, Alarma.EfectosAlarma.EMAIL);

        int devuelto = a.reproducirEfecto();
        assertEquals(2, devuelto);
    }



    @Test
    public void alarmaSuena30minAntesDelEvento() {
        LocalDate fecha = LocalDate.of(2023,1,1);
        LocalDateTime fechaHoraInicio = LocalDateTime.of(fecha, LocalTime.of(20, 0));
        LocalDateTime fechaHoraFin = LocalDateTime.of(fecha, LocalTime.of(22, 30));
        Evento e = new Evento("Evento1", "",fechaHoraInicio, fechaHoraFin,false,null);
        Alarma alarma = e.agregarAlarma(null, 30, Alarma.UnidadesDeTiempo.MINUTOS, Alarma.EfectosAlarma.NOTIFICACION);

        LocalDateTime fechaHoraEsperada = LocalDateTime.of(fecha, LocalTime.of(19, 30));
        LocalDateTime fechaHoraDevuelta = alarma.getFechaHoraAlarma();

        assertEquals(fechaHoraEsperada, fechaHoraDevuelta);
    }

    @Test
    public void modificoEventoYSeModificalaAlarma() {
        LocalDate fecha = LocalDate.of(2023,1,1);
        LocalDateTime fechaHoraInicio = LocalDateTime.of(fecha, LocalTime.of(20, 0));
        LocalDateTime fechaHoraFin = LocalDateTime.of(fecha, LocalTime.of(22, 30));
        Evento e = new Evento("Evento1", "",fechaHoraInicio, fechaHoraFin,false,null);
        Alarma alarma = e.agregarAlarma(null, 30, Alarma.UnidadesDeTiempo.MINUTOS, Alarma.EfectosAlarma.NOTIFICACION);

        LocalDateTime fechaHoraEsperada = LocalDateTime.of(fecha, LocalTime.of(21, 0));
        e.setHoraInicio(LocalTime.of(21,30));

        LocalDateTime fechaHoraDevuelta = alarma.getFechaHoraAlarma();

        assertEquals(fechaHoraEsperada, fechaHoraDevuelta);
    }
}
