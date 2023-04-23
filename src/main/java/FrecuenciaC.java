import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaC implements Frecuencia{
    private LocalDate fechaInicio;
    private TipoFrecuencia tipo;
    private FrecuenciaMensual frecuenciaMensual;
    private int intervalo;
    private ArrayList<DayOfWeek> diasDeLaSemana = new ArrayList<>();
    private LocalDate fechaFin;
    private int ocurrencias;

    private LocalDate fechaProxima;

    public FrecuenciaC(TipoFrecuencia tipo, LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        frecuenciaMensual = FrecuenciaMensual.MISMONUMERO;//por defecto
        if (fechaFin == null) {
            this.fechaFin = LocalDate.MAX;
        }
        else {
            this.fechaFin = fechaFin;
        }
        this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());
        this.fechaInicio = fechaInicio;
        this.fechaProxima = fechaInicio;
    }
    public FrecuenciaC(TipoFrecuencia tipo,LocalDate fechaInicio, int intervalo, int ocurrencias) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        this.ocurrencias = ocurrencias;
        this.frecuenciaMensual = FrecuenciaMensual.MISMONUMERO;//por defecto
        this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());
        this.fechaFin = calcularFechaFin();
        this.fechaProxima = fechaInicio;
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
    public void setTipo(TipoFrecuencia tipo) {
        this.tipo = tipo;
    }
    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
    }

    public void setFrecuenciaMensual(FrecuenciaMensual frecuenciaMensual) {
        this.frecuenciaMensual = frecuenciaMensual;
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
        if (this.ocurrencias > 0){
            this.fechaFin = calcularFechaFin();
        }
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    private LocalDate fechaFinSemanal(LocalDate fechaAux) {
        int contador = 0;
        while (contador < ocurrencias) {
            var diaActual = fechaAux.getDayOfWeek();
            if (diasDeLaSemana.contains(diaActual)) {
                contador++;
            }
            if (contador < ocurrencias) {
                fechaAux = fechaAux.plusDays(1);
                if (fechaAux.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {//si termina la semana
                    fechaAux = fechaAux.plusWeeks(intervalo-1);
                }
            }
        }
        return fechaAux;
    }

    private int calcularNroSemana(LocalDate fecha) {
        int diaDelMes = fecha.getDayOfMonth();
        return (diaDelMes - 1) / 7 + 1; // 7: cant de dias de una semana
    }
    private LocalDate fechaFinMensual(LocalDate fechaAux){
        switch (frecuenciaMensual) {
            case MISMONUMERO -> {
                for (int i = 0; i < ocurrencias-1; i++) {
                    fechaAux = fechaAux.plus(intervalo, ChronoUnit.MONTHS);//sumo 1 mes
                }
            }
            case MISMODIA -> {
                var diaInicial = fechaAux.getDayOfWeek();
                int nroSemana = calcularNroSemana(fechaAux);
                fechaAux = LocalDate.of(fechaAux.getYear(), fechaAux.getMonthValue(), 1);

                for (int i = 0; i < ocurrencias-1; i++) {
                  fechaAux = fechaAux.plusMonths(intervalo);
                }

                int nroSemanaAux = calcularNroSemana(fechaAux);

                while (!fechaAux.getDayOfWeek().equals(diaInicial)) {//busco el mismo dia
                    fechaAux = fechaAux.plusDays(1);
                }
                while (nroSemanaAux < nroSemana) { //busco la "misma semana"
                    fechaAux = fechaAux.plusWeeks(1);
                    nroSemanaAux++;
                }
            }
        }
        return fechaAux;
    }
    LocalDate calcularFechaFin(){
        LocalDate fechaAux = fechaInicio;
        switch (this.tipo) {
            case CERO -> {
                return fechaFin;
            }
            case DIARIA -> {
                for (int i = 0; i < ocurrencias-1; i++) {
                    fechaAux = fechaAux.plusDays(intervalo);
                }
            }
            case SEMANAL -> {
                fechaAux = fechaFinSemanal(fechaAux);
            }
            case MENSUAL -> {
                fechaAux = fechaFinMensual(fechaAux);
            }
            case ANUAL -> {
                for (int i = 0; i < ocurrencias-1; i++) {
                    fechaAux = fechaAux.plusYears(intervalo);
                }
            }
        }
        return fechaAux;
    }


    public LocalDate obtenerProximaFecha() {
        switch (tipo) {
            case CERO -> {
                return null;
            }
            case DIARIA -> {
                fechaProxima = fechaProxima.plusDays(intervalo);
            }
            case SEMANAL -> {
                fechaProxima = fechaProxima.plusDays(1);
                var diaSemana = fechaProxima.getDayOfWeek();
                while (!diasDeLaSemana.contains(diaSemana)) {
                    fechaProxima = fechaProxima.plusDays(1);
                    if (fechaProxima.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                        fechaProxima = fechaProxima.plusWeeks(intervalo - 1);
                    }
                    diaSemana = fechaProxima.getDayOfWeek();
                }
            }
            case MENSUAL -> {
                switch (frecuenciaMensual) {
                    case MISMONUMERO -> {
                        fechaProxima = fechaProxima.plusMonths(intervalo);
                    }
                    case MISMODIA -> {
                        var diaInicial = fechaProxima.getDayOfWeek();
                        int nroSemana = calcularNroSemana(fechaProxima);
                        fechaProxima = LocalDate.of(fechaProxima.getYear(), fechaProxima.getMonthValue(), 1);

                        fechaProxima = fechaProxima.plusMonths(intervalo);

                        int nroSemanaAux = calcularNroSemana(fechaProxima);

                        while (!fechaProxima.getDayOfWeek().equals(diaInicial)) {//busco el mismo dia
                            fechaProxima = fechaProxima.plusDays(1);
                        }
                        while (nroSemanaAux < nroSemana) { //busco la "misma semana"
                            fechaProxima = fechaProxima.plusWeeks(1);
                            nroSemanaAux++;
                        }
                    }
                }
            }
            case ANUAL -> {
                fechaProxima = fechaProxima.plusYears(intervalo);
            }
        }
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }
    /**
     * Devuelve true si la fecha recibida está incluida en la frecuencia,
     * es decir si es parte del ciclo de dis establecidos.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     * */
    public boolean fechaEstaIncluida(LocalDate fechaCualqueira){
        if (fechaInicio.equals(fechaCualqueira) || fechaCualqueira.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualqueira) && fechaCualqueira.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualqueira);
            int diferenciaDeMeses = (int)MONTHS.between(fechaInicio, fechaCualqueira);
            int diaDelMes = fechaInicio.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualqueira.getDayOfMonth();

            switch (this.tipo){
                case CERO -> {
                    return false;
                }
                case DIARIA -> {
                    return (diferenciaDeDias % intervalo == 0);
                }
                case SEMANAL -> {
                    DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();
                    return (diasDeLaSemana.contains(diaCualquiera) && ((diferenciaDeDias/7) % (intervalo)) == 0);

                }
                case MENSUAL -> {
                    if ((diferenciaDeMeses % intervalo) != 0) {
                        return false;
                    }
                    switch (frecuenciaMensual) {
                        case MISMONUMERO -> {
                            return (diaDelMes == diaDelMesCualquiera);
                        }
                        case MISMODIA -> {
                            int nroSemana = calcularNroSemana(fechaInicio);
                            int nroSemanaCualquiera = calcularNroSemana(fechaCualqueira);
                            DayOfWeek dia = fechaInicio.getDayOfWeek();
                            DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();

                            return  (dia.equals(diaCualquiera) && (nroSemana == nroSemanaCualquiera));
                        }
                    }
                }
                case ANUAL -> {
                    if ((diferenciaDeMeses/12) % intervalo != 0) {
                        return false;
                    }
                    Month mes = fechaInicio.getMonth();
                    Month mesCualquiera = fechaCualqueira.getMonth();
                    return (diaDelMes == diaDelMesCualquiera && mes.equals(mesCualquiera));
                }
            }
        }
        return false;
    }
}
