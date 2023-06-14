package org;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {

        var c = new Calendario();

        //evento 1
        LocalDate fecha1 = LocalDate.of(2023, 1,1);
        Frecuencia frecuencia1 = new FrecuenciaSemanal(fecha1,1,null);
        c.crearEvento("evento1", "esta es la descripcion del evento1",
                fecha1, fecha1,"10:30", "13:10",
                false, frecuencia1);

        //tarea 1
        LocalDate fecha2 = LocalDate.of(2023, 1, 4);
        Frecuencia frecuencia2 = new FrecuenciaCero(fecha2);
        var t1 = c.crearTarea("Tarea1", "esta es la Tarea1",fecha2,
                true,"",frecuencia2);
        t1.agregarAlarma(null,10, Alarma.UnidadesDeTiempo.MINUTOS, Alarma.EfectosAlarma.NOTIFICACION);

        //tarea 2
        var fecha3 = LocalDate.of(2023,5,7);
        c.crearTarea("Tarea2", "esta es la org.Tarea 2",fecha3,
                false,"20:30",frecuencia2);


        var array= c.obtenerElementosDeUnLapsoDeDias(fecha1,10);
        for (int i = 0; i < array.size(); i++) {
            System.out.println("Array ["+i+"] "+ array.get(i).getTitulo());
            System.out.println("Hora: " + array.get(i).getHoraInicio().toString());
        }
        try {
            c.serializar(new ObjectMapper(),"Datos1.json");
            System.out.println("Objeto guardado en el archivo " + "Datos1.json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            Calendario c2 = Calendario.deserializar(new ObjectMapper(), "Datos1.json");
            //TEMPORAL
            //Para comparar a ojo Datos1 y Datos2 (para ver las cosas que no podemos testear)
            c2.serializar(new ObjectMapper(),"Datos2.json");
        } catch (IOException e) {
            e.printStackTrace();
        }





    }
}
