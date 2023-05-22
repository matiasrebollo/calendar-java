import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {

        var c = new Calendario();

        //evento 1
        LocalDate fecha1 = LocalDate.of(2023, 1,1);
        Frecuencia frecuencia1 = new FrecuenciaSemanal(fecha1,1,null);
        c.crearEvento("evento1", "esta es la descripcion del evento1",
                "1/1/2023", "1/1/2023","10:30", "13:10",
                false, frecuencia1);

        //tarea 1
        LocalDate fecha2 = LocalDate.of(2023, 1, 4);
        Frecuencia frecuencia2 = new FrecuenciaCero(fecha2);
        var t1 = c.crearTarea("Tarea1", "esta es la Tarea1","4/1/2023",
                true,"",frecuencia2);
        t1.agregarAlarma(null,10, Alarma.UnidadesDeTiempo.MINUTOS, Alarma.EfectosAlarma.NOTIFICACION);

        //tarea 2
        c.crearTarea("Tarea2", "esta es la Tarea 2","7/5/2023",
                false,"20:30",frecuencia2);


        c.serializar(new ObjectMapper(),"Datos1.json");

        Calendario c2 = Calendario.deserializar(new ObjectMapper(), "Datos1.json");

        //TEMPORAL
        //Para comparar a ojo Datos1 y Datos2 (para ver las cosas que no podemos testear)
        c2.serializar(new ObjectMapper(),"Datos2.json");



    }
}
