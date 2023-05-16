import java.time.LocalDate;

public class Main {

    /**
     *
     * private static void leerArchivoJson(String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el archivo JSON
            File file = new File("datos.json");
            JsonNode rootNode = objectMapper.readTree(file);

            // Iterar sobre los elementos del JSON y mostrar su contenido
            rootNode.fields().forEachRemaining(elemento -> {
                String campo = elemento.getKey();
                JsonNode valor = elemento.getValue();

                if (valor.isArray()) {
                    int year = valor.get(0).asInt();
                    int month = valor.get(1).asInt();
                    int day = valor.get(2).asInt();
                    int hour = valor.get(3).asInt();
                    int minute = valor.get(4).asInt();
                    LocalDateTime fecha = LocalDateTime.of(year, month, day, hour, minute);

                    System.out.println(campo +": " + fecha);
                }
                else
                    System.out.println(campo+": " + valor);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public static void main(String[] args) {
        //inicializo un calendario con eventos y tareas
        var c = new Calendario();
        LocalDate fecha1 = LocalDate.of(2023, 1,1);
        Frecuencia frecuencia1 = new FrecuenciaSemanal(fecha1,1,null);
        c.crearEvento("evento1", "esta es la descripcion del evento1",
                "1/1/2023", "1/1/2023","10:30", "13:10",
                false, frecuencia1);

        LocalDate fecha2 = LocalDate.of(2023, 1, 4);
        Frecuencia frecuencia2 = new FrecuenciaCero(fecha2);
        c.crearTarea("Tarea1", "esta es la Tarea1","4/1/2023",
                true,"",frecuencia2);

        c.crearTarea("Tarea2", "esta es la Tarea 2","7/5/2023",
                false,"20:30",frecuencia2);



        c.serializarJson("DatosDelCalendario.json");
    }
}
