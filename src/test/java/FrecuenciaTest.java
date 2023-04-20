import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class FrecuenciaTest{
    @Test
    public void frecuenciaCero() {
        var fecha = LocalDate.of(2023, 1, 1);
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.CERO, fecha, 1,null);

        assertEquals(LocalDate.MAX, f.calcularFechaFin());
    }
    @Test
    public void frecuenciaDiaria() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 5;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 5);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaDiariaCadaTresDias() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 3;
        int ocurrencias = 5;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 13);//3dias *(5-1) = +12dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaUnaSemana() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 15);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaDosSemanas() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 5;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,2, 26);//14*(5-1) = +56 dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaTresSemanas100Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 3;
        int ocurrencias = 100;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2028,9, 10);//21dias*(100-1) = +2079dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalMartesYJueves(){
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 6;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, intervalo,ocurrencias);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.TUESDAY);
        f.agregarOQuitarDiaDeLaSemana(fecha.getDayOfWeek());
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.THURSDAY);

        var fechaEsperada = LocalDate.of(2023,1, 19);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaDosSemanasDomingoMartesYViernes(){
        var fecha = LocalDate.of(2023, 1, 1);//Sunday
        int intervalo = 2;
        int ocurrencias = 6;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL, fecha, intervalo,ocurrencias);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.TUESDAY);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.FRIDAY);

        var fechaEsperada = LocalDate.of(2023,1, 20);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }

    //MENSUAL
    @Test
    public void frecuenciaCadaUnMes() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.MENSUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2023,3, 1);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaDosMeses() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 5;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.MENSUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2023,9, 1);//2mes*(5-1) = +8 meses

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaDosMeses1000Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 1000;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.MENSUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2189,7, 1);//2mes*(1000-1) = +1998 meses

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }

    @Test
    public void frecuenciaCadaUnAnio() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.ANUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2025,1, 1);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaCincoAnios() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 5;
        int ocurrencias = 4;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.ANUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2038,1, 1);//5anio*(4-1) = +15anios

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaCincoAnios1000Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 5;
        int ocurrencias = 1000;
        var f  = new FrecuenciaC(Frecuencia.TipoFrecuencia.ANUAL, fecha, intervalo,ocurrencias);
        f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(7018,1, 1);//5anio*(1000-1) = +495anios

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }



    @Test
    public void unaFechaEsParteDeLaFrecuenciaDiaria() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaFin = LocalDate.of(2023,5,1);
        int intervalo = 1;
        var f = new FrecuenciaC(Frecuencia.TipoFrecuencia.DIARIA, fecha, intervalo, fechaFin);

        assertEquals(true, f.fechaEstaIncluida(LocalDate.of(2023, 3, 10)));
        assertEquals(true, f.fechaEstaIncluida(LocalDate.of(2023, 5, 1)));
        assertEquals(false, f.fechaEstaIncluida(LocalDate.of(2023, 5, 2)));
    }
}
