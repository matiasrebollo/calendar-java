import java.time.DayOfWeek;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate fecha = LocalDate.of(2023, 1, 1);
        var c = new Calendario();
        var tarea1 = c.crearTarea("Titulo1", "Esta es la primer tarea creada","1/1/2023",true,"",null);

        var f = new FrecuenciaC(Frecuencia.TipoFrecuencia.SEMANAL,fecha, 1, null);
        f.agregarOQuitarDiaDeLaSemana(DayOfWeek.FRIDAY);
        var tarea2 = c.crearTarea("Titulo2", "Esta es la segunda tarea creada","1/1/2023",true,"",f);

        for (int i = 1; i < 8; i++) {
            System.out.println( i +" de Enero, " + fecha.getDayOfWeek().toString());
            c.mostrarTareasDelDia(fecha);
            fecha = fecha.plusDays(1);
        }

    }
}
