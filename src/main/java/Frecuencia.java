import javax.swing.plaf.PanelUI;
import java.time.DayOfWeek;
import java.time.LocalDate;

public interface Frecuencia {
    enum TipoFrecuencia {CERO, DIARIA, SEMANAL, MENSUAL, ANUAL}
    enum FrecuenciaMensual {MISMONUMERO, MISMODIA} //ej. todos los meses, el dia 7 o todos los meses, el 3er lunes

    int getIntervalo();
    LocalDate getFechaFin();

    void setFrecuenciaMensual(FrecuenciaMensual frecuenciaMensual);
    void setFechaInicio(LocalDate fechaInicio);
    void setFechaFin(LocalDate fechaFin);
    void setIntervalo(int cadaCuanto);
    void setTipo(TipoFrecuencia tipo);
    /**
     * En caso de ser frecuencia de tipo SEMANAL,
     * agrega el dia de la semana a la frecuencia si no fue agregado antes
     * o lo quita si este ya estaba agregado
     * */
    void agregarOQuitarDiaDeLaSemana(DayOfWeek dia);

    /**
     * Devuelve la última fecha correspondiente a la frecuencia
     * */
    LocalDate calcularFechaFin();//Deberia ser privada, es publica para hacer las pruebas

    /**
     * Devuelve la proxima fecha correspondiente a la frecuencia
     * o null si no hay proxima
     * */
    LocalDate obtenerProximaFecha();

    /**
     * Recibe una fecha aleatoria y devuelve true si la fecha recibida
     * corresponde a la frecuencia.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     * */
    boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualqueira);

}
