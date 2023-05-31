import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App extends Application {
    enum Formatos {DIA, SEMANA, MES};
    private Formatos formatoSeleccionado = Formatos.SEMANA;
    private DateTimeFormatter formatter = null;
    private LocalDate fechaSeleccionada;


    private void retrocederFecha() {
        switch (formatoSeleccionado) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.minusDays(1);
            case SEMANA -> fechaSeleccionada = fechaSeleccionada.minusWeeks(1);
            case MES -> fechaSeleccionada = fechaSeleccionada.minusMonths(1);
        }
    }
    private void avanzarFecha() {
        switch (formatoSeleccionado) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.plusDays(1);
            case SEMANA -> fechaSeleccionada = fechaSeleccionada.plusWeeks(1);
            case MES -> fechaSeleccionada = fechaSeleccionada.plusMonths(1);
        }
    }
    private Node contenidoCentroSemana() {
        int n = 1;//Temporal. tendria que recibir la fecha del lunes seleccionado por parametro o algo asi
        var labelLunes = new Label("Lunes\n"+ n);
        var labelMartes = new Label("Martes\n" + (n+1));
        var labelMiercoles = new Label("Miercoles\n" + (n+2));
        var labelJueves = new Label("Jueves\n" + (n+3));
        var labelViernes = new Label("Viernes\n" + (n+4));
        var labelSabado = new Label("Sábado\n" + (n+5));
        var labelDomingo = new Label("Domingo\n" + (n+6));


        BorderStroke borde = new BorderStroke(
                Paint.valueOf("black"),  // Color del borde (negro en este caso)
                BorderStrokeStyle.SOLID, // Estilo del borde (sólido en este caso)
                null,    // Radios de las esquinas del borde (ninguno en este caso)
                new BorderWidths(1) // Anchura del borde (1 píxel en este caso)
        );

        //Ajusto las propiedades de cada label
        Label[] labels = {labelLunes, labelMartes, labelMiercoles, labelJueves, labelViernes, labelSabado, labelDomingo};
        for (Label label : labels) {
            label.textAlignmentProperty().set(TextAlignment.CENTER);//Para que tengan texto centrado
            label.alignmentProperty().set(Pos.TOP_CENTER);
            label.setPrefWidth(100);//modifico el ancho de cada label
            label.setPrefHeight(200);
            label.setBorder(new Border(borde));
        }

        var contenidoCentro = new HBox(labelLunes, labelMartes, labelMiercoles, labelJueves, labelViernes, labelSabado, labelDomingo);
        contenidoCentro.setAlignment(Pos.TOP_CENTER);

        return contenidoCentro;
    }

    //semana
    private Node contenidoBarraSuperior(LocalDate fechaSeleccionada) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Dia", "Semana", "Mes");
        choiceBox.setValue("Semana");//formato semana por defecto
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Acciones a realizar cuando se modifica la opción seleccionada
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                this.formatoSeleccionado = Formatos.DIA;
                this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.println("Se selecciono la opcion 'Dia'");
            } else if (newValue.intValue() == 1) {
                formatoSeleccionado = Formatos.SEMANA;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                System.out.println("Se selecciono la opcion 'Semana'");
            } else if (newValue.intValue() == 2) {
                formatoSeleccionado = Formatos.MES;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");//Estaria bueno que diga "Mayo 2023"
                System.out.println("Se selecciono la opcion 'Mes'");
            }
        });

        var espacioDerecha = new Label(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")) + "\n" +
                                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


        var flechaIzquierda = new Button("<-");
        flechaIzquierda.setOnAction(actionEvent -> {
            retrocederFecha();
            System.out.println("Hizo click en retroceder. Fecha Seleccionada: " + this.fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        var flechaDerecha = new Button("->");
        flechaDerecha.setOnAction(actionEvent -> {
            avanzarFecha();
            System.out.println("Hizo click en avanzar. Fecha Seleccionada: " + this.fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });


        var flechas = new TilePane(flechaIzquierda, flechaDerecha);
        var fecha = new Label(fechaSeleccionada.format(formatter));
        var espacioCentro = new VBox(fecha, flechas);


        var contenedor = new HBox(choiceBox, espacioCentro, espacioDerecha);
        contenedor.setSpacing(200);//temporal. No deberia ser un tamaño fijo

        return contenedor;

    }

    @Override
    public void start(Stage stage) throws Exception {
        LocalTime horaActual = LocalTime.now();//la hora actual debe actualizarse cada segundo
        LocalDate fechaActual = LocalDate.now();
        fechaSeleccionada = fechaActual;



        var barraSuperior = contenidoBarraSuperior(fechaSeleccionada);
        barraSuperior.setStyle("-fx-background-color: red;");//para probar. Despues lo saco

        var contenidoCentro = contenidoCentroSemana();
        contenidoCentro.setStyle("-fx-background-color: green;");//para probar. Despues lo saco


        var botonAgregarEvento = new Button("agregar evento/Tarea");
        botonAgregarEvento.setOnAction(actionEvent -> {
            //habria que lanzar como una ventana (cuadro) donde se pueda poner los datos del evento con un boton para confirmar
            System.out.println("Se agrego el evento");
        });

        var barraInferior = new StackPane(botonAgregarEvento);



        var contenedor = new BorderPane();
        contenedor.setTop(barraSuperior);
        contenedor.setCenter(contenidoCentro);
        contenedor.setBottom(barraInferior);

        /**
         * COSAS QUE FALTAN:
         * contenidoCentro Dia y ContenidoCentro Mes. (la barra inferior y superior van a ser las mismas)
         * actualizar la hora constantemente.
         * cambiar la forma de mostrar cuando se indique (cambiar el contenido centro)
         * cambiar la fecha cuando avance o retroceda
         * Mejorar las proporciones, tamaños  (estetica)
         * Abrir un cuadro por encima cuando se haga click en agregarEvento, que tenga una especie de formulario
         *
         * Aplicar toodo lo del calendario
         * que se vean los eventos y tareas en las fechas correspondientes ordenados por hora
         *
         */


        var sceneSemana = new Scene(contenedor, 640, 480);
        stage.setScene(sceneSemana);
        stage.show();
    }
}
