import org.junit.Assert;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.Date;
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
    public void seCreaUnaTareaCorrectamente() {
        var calendario = new Calendario();
        LocalDate fecha = LocalDate.of(2023,5,20);

        calendario.crearTarea("tarea1", "", fecha,true);

        assertEquals(1,calendario.cantidadTareas());
    }

}
