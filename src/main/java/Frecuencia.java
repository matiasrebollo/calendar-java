import java.time.DayOfWeek;
import java.time.LocalDate;


public class Frecuencia{

    private LocalDate fechaInicio;
    private int intervalo;
    private int ocurrencias;
    private LocalDate fechaFin;
    private EstrategiaFrecuencia tipo;
    private LocalDate fechaProxima;



    //recibe una fecha fin
    public Frecuencia(EstrategiaFrecuencia tipo, LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        if (fechaFin == null) {
            this.fechaFin = LocalDate.MAX;
        }
        else {
            this.fechaFin = fechaFin;
        }
        this.ocurrencias = -1; //-1 representa indefinidas repeticiones
        //this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());
        this.agregarOQuitarDiaDeLaSemana(fechaInicio.getDayOfWeek());
        this.fechaInicio = fechaInicio;
        this.fechaProxima = fechaInicio;
    }

    //recibe una cantidad de ocurrencias
    public Frecuencia(EstrategiaFrecuencia tipo, LocalDate fechaInicio, int intervalo, int ocurrencias) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        this.ocurrencias = ocurrencias;
        //this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());
        agregarOQuitarDiaDeLaSemana(fechaInicio.getDayOfWeek());
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
     * agrega el dia de la semana a la frecuencia si no fue agregado antes
     * o lo quita si este ya estaba agregado
     * */
    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia){
        if (tipo instanceof FrecuenciaSemanal) {
            ((FrecuenciaSemanal) tipo).agregarOQuitarDiaDeLaSemana(dia);
            this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
        }
    }

    public void setTipo(EstrategiaFrecuencia tipo) {
        this.tipo = tipo;
        this.fechaFin = this.tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
        this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void modificarTipoFrecuenciaMensual(FrecuenciaMensual.Tipo tipoFrecuenciaMensual) {
        if (tipo instanceof  FrecuenciaMensual) {
            ((FrecuenciaMensual) tipo).setTipoFrecuenciaMensual(tipoFrecuenciaMensual);
        }
        this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
        if (this.ocurrencias > 0){
            this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, this.fechaInicio, fechaFin);
        }
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    /*private int calcularNroSemana(LocalDate fecha) {
        int diaDelMes = fecha.getDayOfMonth();
        return (diaDelMes - 1) / 7 + 1; // 7: cant de dias de una semana
    }*/
    /*private LocalDate fechaFinSemanal(LocalDate fechaAux) {
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
    private LocalDate fechaFinMensual(LocalDate fechaAux){
        switch (tipoFrecuenciaMensual) {
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
*/

    /**
     * Devuelve la última fecha correspondiente a la frecuencia
     * */
    public LocalDate calcularFechaFin(){//Deberia ser privada, es publica para hacer las pruebas
        return tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
        /*if (ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
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
        return fechaAux;*/
    }


    /**
     * Devuelve la proxima fecha correspondiente a la frecuencia
     * o null si no hay proxima
     * */
    public LocalDate obtenerProximaFecha() {
        return tipo.obtenerFechaProxima(fechaProxima, intervalo, fechaFin);
        /*switch (tipo) {
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
                switch (tipoFrecuenciaMensual) {
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
        return fechaProxima;*/
    }


    /**
     * Recibe una fecha aleatoria y devuelve true si la fecha recibida
     * corresponde a la frecuencia.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     * */
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera) {
        return tipo.fechaCorrespondeAFrecuencia(fechaCualquiera, fechaInicio, fechaFin, intervalo);

        /*if (fechaInicio.equals(fechaCualqueira) || fechaCualqueira.equals(fechaFin)) {
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
                    switch (tipoFrecuenciaMensual) {
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
        return false;*/
    }
}
