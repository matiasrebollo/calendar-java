import java.time.LocalDate;

public class Main {


    public static void main(String[] args) {
        //inicializo un calendario con eventos y tareas
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


        //Serializar
       // ObjectMapper objectMapper = new ObjectMapper();
      //  objectMapper.registerModule(new JavaTimeModule());//para poder escribir LocalDateTime
        //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);  // Habilitar formato legible


//        c.serializar(objectMapper);



  //      Calendario c2 = Calendario.deserializar(objectMapper);

        //c2.serializar(objectMapper);



    }
}
