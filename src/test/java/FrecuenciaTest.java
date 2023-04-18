import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class FrecuenciaTest {
    @Test
    public void frecuenciaCero() {
        var fecha = LocalDate.of(2023, 1, 1);
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.CERO, fecha);

        assertEquals(LocalDate.MAX, f.calcularFechaFin());
    }
    @Test
    public void frecuenciaDiaria() {
        var fecha = LocalDate.of(2023, 1, 1);
        int repeticiones = 5;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fecha, 1,repeticiones);
        var fechaEsperada = LocalDate.of(2023,1, 6);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaUnaSemana() {
        var fecha = LocalDate.of(2023, 1, 1);
        int repeticiones = 2;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, 1,repeticiones);
        var fechaEsperada = LocalDate.of(2023,1, 15);

        //var fechaDevuelta = f.calcularFechaFin();

        //assertEquals(fechaEsperada, fechaDevuelta);
    }
}
