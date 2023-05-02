import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class FrecuenciaTest{
    @Test
    public void frecuenciaCero() {
        var fecha = LocalDate.of(2023, 1, 1);
        var f  = new Frecuencia(new FrecuenciaCero(), fecha, 1,null);

        assertEquals(LocalDate.MAX, f.calcularFechaFin());
    }
    @Test
    public void frecuenciaDiaria() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 5;
        var f  = new Frecuencia(new FrecuenciaDiaria(), fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 5);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaDiariaCadaTresDias() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 3;
        int ocurrencias = 5;
        var f  = new Frecuencia(new FrecuenciaDiaria(), fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 13);//3dias *(5-1) = +12dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }

    //SEMANAL
    @Test
    public void frecuenciaSemanalCadaUnaSemana() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,1, 15);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaDosSemanas() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 5;
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2023,2, 26);//14*(5-1) = +56 dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalCadaTresSemanas100Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 3;
        int ocurrencias = 100;
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo,ocurrencias);
        var fechaEsperada = LocalDate.of(2028,9, 10);//21dias*(100-1) = +2079dias

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaSemanalMartesYJueves(){
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 6;
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo,ocurrencias);
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
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo,ocurrencias);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.TUESDAY);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.FRIDAY);

        var fechaEsperada = LocalDate.of(2023,1, 20);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }

    //MENSUAL
    @Test
    public void frecuenciaMensualCadaUnMes() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMONUMERO), fecha, intervalo,ocurrencias);
        //f.setTipoFrecuenciaMensual(MISMONUMERO);
        var fechaEsperada = LocalDate.of(2023,3, 1);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaMensual1erLunes() {
        var fecha = LocalDate.of(2023, 1, 2);
        int intervalo = 1;
        int ocurrencias = 4;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, ocurrencias);
        //f.setTipoFrecuenciaMensual(MISMODIA);
        var fechaEsperada = LocalDate.of(2023,4, 3);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaMensual2doMartes() {
        var fecha = LocalDate.of(2023, 2, 14);
        int intervalo = 1;
        int ocurrencias = 4;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, ocurrencias);
        ////f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMODIA);
        var fechaEsperada = LocalDate.of(2023,5, 9);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaMensual4toLunes() {
        var fecha = LocalDate.of(2023, 1, 23);
        int intervalo = 1;
        int ocurrencias = 4;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, ocurrencias);
        ////f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMODIA);
        var fechaEsperada = LocalDate.of(2023,4, 24);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaMensualCadaDosMeses() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 5;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMONUMERO), fecha, intervalo,ocurrencias);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2023,9, 1);//2mes*(5-1) = +8 meses

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaDosMeses1000Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        int ocurrencias = 1000;
        var f  = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMONUMERO), fecha, intervalo,ocurrencias);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2189,7, 1);//2mes*(1000-1) = +1998 meses

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }

    //Anual
    @Test
    public void frecuenciaCadaUnAnio() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 1;
        int ocurrencias = 3;
        var f  = new Frecuencia(new FrecuenciaAnual(), fecha, intervalo,ocurrencias);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2025,1, 1);

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaCincoAnios() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 5;
        int ocurrencias = 4;
        var f  = new Frecuencia(new FrecuenciaAnual(), fecha, intervalo,ocurrencias);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(2038,1, 1);//5anios*(4-1) = +15anios

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void frecuenciaCadaCincoAnios1000Veces() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 5;
        int ocurrencias = 1000;
        var f  = new Frecuencia(new FrecuenciaAnual(), fecha, intervalo,ocurrencias);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMONUMERO);
        var fechaEsperada = LocalDate.of(7018,1, 1);//5anio*(1000-1) = +495anios

        var fechaDevuelta = f.calcularFechaFin();

        assertEquals(fechaEsperada, fechaDevuelta);
    }


    //Pruebas de método fechaCorrespondeAFrecuencia()
    @Test
    public void fechaCorrespondeAFrecuenciaDiaria() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaFin = LocalDate.of(2023,5,1);
        int intervalo = 1;
        var f = new Frecuencia(new FrecuenciaDiaria(), fecha, intervalo, fechaFin);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(LocalDate.of(2023, 3, 10)));
        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaFin));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(LocalDate.of(2023, 5, 2)));
    }
    @Test
    public void fechaCorrespondeAFrecuenciaSemanal() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaFin = LocalDate.of(2023,5,1);
        var fechaIncluida = LocalDate.of(2023, 3, 5);
        var fechaPosterior = fechaFin.plusDays(1);
        int intervalo = 1;

        var f = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo, fechaFin);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida));
        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaFin));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaPosterior));
    }
    @Test
    public void fechaCorrespondeAFrecuenciaSemanalDomingoYViernes() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaIncluida = LocalDate.of(2023, 1, 6);
        var fechaNoIncluida = LocalDate.of(2023, 1, 4);
        int intervalo = 1;
        var f  = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo, null);

        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.FRIDAY);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida));
    }
    @Test
    public void fechaCorrespondeAFrecuenciaCada3Meses() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaIncluida = LocalDate.of(2023, 4, 1);
        var fechaNoIncluida = LocalDate.of(2023, 2, 1);
        int intervalo = 3;

        var f = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMONUMERO), fecha, intervalo, null);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida));
    }
    @Test
    public void fechaCorrespondeAFrecuenciaCada2MesesMismoDia() {
        var fecha = LocalDate.of(2023, 1, 1);//primer domingo
        var fechaIncluida = LocalDate.of(2023, 3, 5);
        var fechaIncluida2 = LocalDate.of(2023, 5, 7);
        var fechaNoIncluida = LocalDate.of(2023, 3, 1);
        var fechaNoIncluida2 = LocalDate.of(2023, 2, 5);

        int intervalo = 2;

        var f = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, null);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMODIA);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida));
        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida2));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida2));

    }
    @Test
    public void fechaCorrespondeAFrecuenciaCada2Anios() {
        var fecha = LocalDate.of(2023, 1, 1);
        var fechaIncluida = LocalDate.of(2025, 1, 1);
        var fechaIncluida2 = LocalDate.of(2027, 1, 1);
        var fechaNoIncluida = LocalDate.of(2024, 1, 1);
        var fechaNoIncluida2 = LocalDate.of(2025, 2, 1);

        int intervalo = 2;

        var f = new Frecuencia(new FrecuenciaAnual(), fecha, intervalo, null);

        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida));
        assertEquals(true, f.fechaCorrespondeAFrecuencia(fechaIncluida2));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida));
        assertEquals(false, f.fechaCorrespondeAFrecuencia(fechaNoIncluida2));
    }

    //Pruebas de obtenerProximaFecha()
    @Test
    public void obtenerProximaFechaSemanal1() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        var fechaEsperada = LocalDate.of(2023, 1, 15);

        var f = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo, null);

        var fechaDevuelta = f.obtenerProximaFecha();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void obtenerProximaFechaSemanal2() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        var fechaEsperada = LocalDate.of(2023, 1, 4);//miercoles

        var f = new Frecuencia(new FrecuenciaSemanal(), fecha, intervalo, null);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.WEDNESDAY);
        var fechaDevuelta = f.obtenerProximaFecha();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void obtenerProximaFechaMensual1() {
        var fecha = LocalDate.of(2023, 1, 1);
        int intervalo = 2;
        var fechaEsperada = LocalDate.of(2023, 3, 1);

        var f = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, null);
        f.modificarTipoFrecuenciaMensual(FrecuenciaMensual.Tipo.MISMONUMERO);
        var fechaDevuelta = f.obtenerProximaFecha();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
    @Test
    public void obtenerProximaFechaMensual2() {
        var fecha = LocalDate.of(2023, 1, 1);//1er domingo
        int intervalo = 4;
        var fechaEsperada = LocalDate.of(2023, 5, 7);

        var f = new Frecuencia(new FrecuenciaMensual(FrecuenciaMensual.Tipo.MISMODIA), fecha, intervalo, null);
        //f.setFrecuenciaMensual(Frecuencia.FrecuenciaMensual.MISMODIA);
        var fechaDevuelta = f.obtenerProximaFecha();

        assertEquals(fechaEsperada, fechaDevuelta);
    }
}
