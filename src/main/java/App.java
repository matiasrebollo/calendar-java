import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App extends Application {
    enum Formatos {DIA, SEMANA, MES};
    private Formatos formatoSeleccionado = Formatos.SEMANA;
    private static final DateTimeFormatter FORMATTER_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter formatter = FORMATTER_FECHA;

    private LocalDate fechaSeleccionada;
    private LocalDate fechaActual;
    private LocalTime horaActual;


    private void retrocederFecha() {
        switch (formatoSeleccionado) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.minusDays(1);
            case SEMANA -> {
                while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {//para tener el lunes seleccionado y simplificar cosas
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.minusWeeks(1);
            }
            case MES -> fechaSeleccionada = fechaSeleccionada.minusMonths(1);
        }
    }
    private void avanzarFecha() {
        switch (formatoSeleccionado) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.plusDays(1);
            case SEMANA -> {
                while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {//para tener el lunes seleccionado y simplificar cosas
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.plusWeeks(1);
            }
            case MES -> fechaSeleccionada = fechaSeleccionada.plusMonths(1);
        }
    }

    //Contenido del centro
    private Node contenidoCentroSemana() {
        int n = 1;//numero del lunes seleccionado (mejorar)
        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);

        var columnaLunes = new VBox(new Label("Lunes\n" + n));
        var columnaMartes = new VBox(new Label("Martes\n" + (n+1)));
        var columnaMiercoles = new VBox(new Label("Miercoles\n" + (n+2)));
        var columnaJueves = new VBox(new Label("Jueves\n" + (n+3)));
        var columnaViernes = new VBox(new Label("Viernes\n" + (n+4)));
        var columnaSabado = new VBox(new Label("Sabado\n" + (n+5)));
        var columnaDomingo = new VBox(new Label("Domingo\n" + (n+6)));

        //Ajusto las propiedades de cada columna
        VBox[] columnas = {columnaLunes, columnaMartes, columnaMiercoles, columnaJueves, columnaViernes, columnaSabado, columnaDomingo};
        for (VBox col: columnas) {
            col.setAlignment(Pos.TOP_CENTER);
            col.setBorder(new Border(borde));
            HBox.setHgrow(col, Priority.ALWAYS);
        }

        var contenidoCentro = new HBox(columnaLunes, columnaMartes, columnaMiercoles, columnaJueves, columnaViernes, columnaSabado, columnaDomingo);
        contenidoCentro.setAlignment(Pos.TOP_CENTER);

        return contenidoCentro;
    }

    private Node contenidoCentroDia(){
        return null;
    }
    private Node contenidoCentroMes(){
        return null;
    }


    //Barra superior...

    /**
     * crea una caja de opciones (Dia, Semana, Mes),
     * y se configuran las aciones a realizar en caso de qué opción se seleccione
     *///INCOMPLETO
    private Node crearChoiceBox() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Dia", "Semana", "Mes");
        choiceBox.setValue("Semana");//formato semana por defecto

        // Acciones a realizar cuando se modifica la opción seleccionada
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                //cambiar contenido centro...
                this.formatoSeleccionado = Formatos.DIA;
                this.formatter = FORMATTER_FECHA;
                //System.out.println("Se selecciono la opcion 'Dia'");
            } else if (newValue.intValue() == 1) {
                formatoSeleccionado = Formatos.SEMANA;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                //System.out.println("Se selecciono la opcion 'Semana'");
            } else if (newValue.intValue() == 2) {
                formatoSeleccionado = Formatos.MES;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");//Estaria bueno que diga "Mayo 2023"
                //System.out.println("Se selecciono la opcion 'Mes'");
            }
        });
        return choiceBox;
    }

    /**
     * Crea un objeto Label con la fecha y hora actuales, que se actualizan cada 1 segundo
     * */
    private Node labelFechaHoraActual() {
        var labelFechaHora = new Label(horaActual.format(FORMATTER_HORA) +"\n"+ fechaActual.format(FORMATTER_FECHA));
        labelFechaHora.setAlignment(Pos.CENTER);//para centrar en el espacio
        labelFechaHora.setTextAlignment(TextAlignment.CENTER);//para centrar el texto

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {//realiza cada 1 segundo
            fechaActual = LocalDate.now();
            horaActual = LocalTime.now();
            labelFechaHora.setText(horaActual.format(FORMATTER_HORA) +"\n"+ fechaActual.format(FORMATTER_FECHA));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        return labelFechaHora;
    }

    /**
     * Crea un VBox que contiene la fecha seleccionada y dos botones
     * con flechas para avanzar o retroceder esa fecha
     */
    private Node bloqueSeleccionarFecha() {
        var fecha = new Label(this.fechaSeleccionada.format(formatter));
        var flechaIzquierda = new Button("<-");
        flechaIzquierda.setOnAction(actionEvent -> {
            retrocederFecha();
            fecha.setText(this.fechaSeleccionada.format(formatter));
        });
        var flechaDerecha = new Button("->");
        flechaDerecha.setOnAction(actionEvent -> {
            avanzarFecha();
            fecha.setText(this.fechaSeleccionada.format(formatter));
        });

        var flechas = new TilePane(flechaIzquierda, flechaDerecha);
        flechas.setAlignment(Pos.CENTER);

        var contenedor = new VBox(fecha, flechas);
        contenedor.setAlignment(Pos.CENTER);
        return contenedor;
    }

    /**
     * Contiene:
     * las opciones (a la izquierda),
     * la fecha seleccionada con los botones para avanzar o retroceder (en el centro)
     * y la fecha y hora actual (a la derecha)
     * */
    private Node contenidoBarraSuperior() {
        var espacioIzquierda = crearChoiceBox();
        var espacioDerecha = labelFechaHoraActual();
        var espacioCentro =  bloqueSeleccionarFecha();

        HBox.setHgrow(espacioCentro,Priority.ALWAYS);
        var contenedor = new HBox(espacioIzquierda, espacioCentro, espacioDerecha);
        return contenedor;
    }







    @Override
    public void start(Stage stage) throws Exception {
        fechaActual = LocalDate.now();
        horaActual = LocalTime.now();
        fechaSeleccionada = fechaActual;



        var barraSuperior = contenidoBarraSuperior();
        barraSuperior.setStyle("-fx-background-color: red;");//para probar. Despues lo saco

        var contenidoCentro = contenidoCentroSemana();
        contenidoCentro.setStyle("-fx-background-color: green;");//para probar. Despues lo saco

        var barraIzquierda = new StackPane(new Label("  "));
        var barraDerecha = new StackPane(new Label("  "));

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
        contenedor.setLeft(barraIzquierda);
        contenedor.setRight(barraDerecha);

        /**
         * COSAS QUE FALTAN:
         * contenidoCentro Dia y ContenidoCentro Mes. (la barra inferior y superior van a ser las mismas)
         * cambiar la forma de mostrar cuando se indique (cambiar el contenido centro)
         * Mejorar las proporciones, tamaños  (estetica)
         * Abrir un cuadro por encima cuando se haga click en agregarEvento, que tenga una especie de formulario (averiguar como se hace)
         *
         * Aplicar lo del calendario
         * que se vean los eventos y tareas en las fechas correspondientes ordenados por hora
         *
         */


        var sceneSemana = new Scene(contenedor, 640, 480);
        stage.setScene(sceneSemana);
        stage.show();
    }
}
