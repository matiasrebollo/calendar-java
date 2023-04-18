import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaC implements Frecuencia{
    public TipoFrecuencia tipo;
    public FrecuenciaMensual frecuenciaMensual;
    private int intervalo;
    private ArrayList<DayOfWeek> diasDeLaSemana;
    private LocalDate fechaFin;

    public FrecuenciaC(TipoFrecuencia tipo) {
        this.tipo = tipo;
        this.intervalo = 1; //por defecto
        frecuenciaMensual = FrecuenciaMensual.MISMODIA;//por defecto
        this.fechaFin = LocalDate.MAX; //por defecto
        this.diasDeLaSemana = new ArrayList<>();
    }

    public int getIntervalo() {
        return intervalo;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * En caso de ser frecuencia de tipo SEMANAL,
     * agrega el dia de la semana al array si no fue agregado antes
     * o lo quita del array si este ya estaba agregado
     * */
    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia){
        if (this.tipo != TipoFrecuencia.SEMANAL) {
            return;
        }
        if (diasDeLaSemana.contains(dia)){
            diasDeLaSemana.remove(dia);
        }
        else {
            diasDeLaSemana.add(dia);
        }
    }
    public boolean incluyeElDia(DayOfWeek dia) {
        return diasDeLaSemana.contains(dia);
    }

    public void setTipo(TipoFrecuencia tipo) {
        this.tipo = tipo;
    }
    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
    }

    public void setFrecuenciaMensual(FrecuenciaMensual frecuenciaMensual) {
        this.frecuenciaMensual = frecuenciaMensual;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Recibe una fecha inicial (del evento o tarea) y una fecha cualquiera.
     * Devuelve true si la segunda pertenece a la frecuencia de la primera,
     * es decir si la tarea o evento ocurrira en esa fecha
     *
     *
     *
     * cambiar nombre de la funcion!!!
     * HACER pruebas!!
     * */
    public boolean coincidenFechas(LocalDate fecha, LocalDate fechaCualqueira){
        if (fecha.equals(fechaCualqueira)) {
            return true;
        }
        else if (fecha.isBefore(fechaCualqueira) && fechaCualqueira.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fecha, fechaCualqueira);
            int diferenciaDeMeses = (int)MONTHS.between(fecha, fechaCualqueira);
            int diaDelMes = fecha.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualqueira.getDayOfMonth();

            switch (this.tipo){
                case CERO -> {
                    return false;
                }
                case DIARIA -> {
                    int cadaCuantosDias = this.getIntervalo();
                    return (diferenciaDeDias % cadaCuantosDias == 0);
                }
                case SEMANAL -> {
                    int intervalo = this.getIntervalo();
                    DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();
                    return (this.incluyeElDia(diaCualquiera) && (diferenciaDeDias % (intervalo*7)) == 0);
                }
                case MENSUAL -> {
                    int intervalo = this.getIntervalo();
                    if (diferenciaDeMeses % intervalo != 0) {
                        return false;
                    }
                    if (this.frecuenciaMensual == FrecuenciaMensual.MISMONUMERO) {
                        return (diaDelMes == diaDelMesCualquiera);
                    }
                    else if (this.frecuenciaMensual == FrecuenciaMensual.MISMODIA){
                        int nroSemana = (diaDelMes + 6) / 7;// 7: cant de dias de una semana
                        int nroSemanaCualquiera = (diaDelMesCualquiera + 6) / 7;
                        DayOfWeek dia = fecha.getDayOfWeek();
                        DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();

                        return  (dia.equals(diaCualquiera) && (nroSemana == nroSemanaCualquiera));
                    }
                }
                case ANUAL -> {
                    int intervalo = this.getIntervalo();
                    Month mes = fecha.getMonth();
                    Month mesCualquiera = fechaCualqueira.getMonth();
                    return (diaDelMes == diaDelMesCualquiera && mes.equals(mesCualquiera) &&
                            diferenciaDeMeses / intervalo*12 == 0);
                }
            }
        }
        return false;
    }
}
