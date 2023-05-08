import java.time.LocalDate;

public class FrecuenciaCero extends Frecuencia {
    //esta frecuencia representa una única vez

    public FrecuenciaCero(LocalDate fecha) {
        super(fecha, 1, fecha);
    }

    public LocalDate calcularFechaFin() {
        return fechaFin;
    }
    public LocalDate obtenerFechaProxima(){
        return null;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera){
        return fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin);
    }
}
