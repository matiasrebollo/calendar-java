import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;
public class FrecuenciaSemanal extends Frecuencia {

    private ArrayList<DayOfWeek> dias = new ArrayList<>();

    public FrecuenciaSemanal(LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        super(fechaInicio, intervalo, fechaFin);
        this.dias.add(fechaInicio.getDayOfWeek());
    }
    public FrecuenciaSemanal(LocalDate fechaInicio, int intervalo, int ocurrencias) {
        super(fechaInicio, intervalo, ocurrencias);
        this.dias.add(fechaInicio.getDayOfWeek());
        super.fechaFin = calcularFechaFin();
    }

    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia) {
        if (dias.contains(dia)) {
            dias.remove(dia);
        }
        else {
            dias.add(dia);
        }
    }
    public LocalDate calcularFechaFin(){
        if (fechaFin != null && ocurrencias == -1) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        int contador = 0;
        while (contador < ocurrencias) {
            var diaActual = fechaAux.getDayOfWeek();
            if (dias.contains(diaActual)) {
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

    public LocalDate obtenerFechaProxima() {
        fechaProxima = fechaProxima.plusDays(1);
        var diaSemana = fechaProxima.getDayOfWeek();
        while (!dias.contains(diaSemana)) {
            fechaProxima = fechaProxima.plusDays(1);
            if (fechaProxima.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                fechaProxima = fechaProxima.plusWeeks(intervalo - 1);
            }
            diaSemana = fechaProxima.getDayOfWeek();
        }
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }

        return fechaProxima;
    }

    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera) {
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualquiera);
            DayOfWeek diaCualquiera = fechaCualquiera.getDayOfWeek();
            return (dias.contains(diaCualquiera) && ((diferenciaDeDias/7) % (intervalo)) == 0);
        }
        return false;
    }
}
