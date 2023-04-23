import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class AlarmaTest {

    @Test
    public void alarmaSuena30minAntes() {
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
        e.modificarHorario(LocalTime.of(21,30), null);

        LocalDateTime fechaHoraDevuelta = alarma.getFechaHoraAlarma();

        assertEquals(fechaHoraEsperada, fechaHoraDevuelta);
    }

    @Test
    public void alarmaEnviaNotificacion() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        Alarma a = new Alarma(null,fechaHora, Alarma.EfectosAlarma.NOTIFICACION);

        int devuelto = a.reproducirEfecto();
        assertEquals(0, devuelto);
    }
    @Test
    public void alarmaReproduceSonido() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        Alarma a = new Alarma(null,fechaHora, Alarma.EfectosAlarma.SONIDO);

        int devuelto = a.reproducirEfecto();
        assertEquals(1, devuelto);
    }
    @Test
    public void alarmaEnviaEmail() {
        LocalDateTime fechaHora = LocalDateTime.of(2023,1,1, 20, 0);
        Alarma a = new Alarma(null,fechaHora, Alarma.EfectosAlarma.EMAIL);

        int devuelto = a.reproducirEfecto();
        assertEquals(2, devuelto);
    }
}
