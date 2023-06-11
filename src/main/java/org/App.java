package org;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    enum Modalidades {DIA, SEMANA, MES};
    private Modalidades modalidad = Modalidades.SEMANA;
    private static final DateTimeFormatter FORMATTER_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter formatter = FORMATTER_FECHA;

    private LocalDate fechaSeleccionada;
    private LocalDate fechaActual;
    private LocalTime horaActual;

    private Node contenidoCentro;
    private BorderPane contenedor;


    private void retrocederFecha() {
        switch (modalidad) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.minusDays(1);
            case SEMANA -> {
                while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {//para tener el lunes seleccionado y simplificar cosas
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.minusWeeks(1);
            }
            case MES -> {
                while (fechaSeleccionada.getDayOfMonth() != 1) {//para tener el dia 1 del mes
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.minusMonths(1);
            }
        }
    }
    private void avanzarFecha() {
        switch (modalidad) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.plusDays(1);
            case SEMANA -> {
                while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {//para tener el lunes seleccionado y simplificar cosas
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.plusWeeks(1);
            }
            case MES -> {
                while (fechaSeleccionada.getDayOfMonth() != 1) {//para tener el dia 1 del mes
                    fechaSeleccionada = fechaSeleccionada.minusDays(1);
                }
                fechaSeleccionada = fechaSeleccionada.plusMonths(1);
            }
        }
    }


    /**
     * Recibe un día de la semana y
     * Devuelve la cantidad de días que pasaron desde el lunes
     */
    private int calcularDiasDesdeElLunes(DayOfWeek dia){
        int cuantos = 0;
        switch (dia) {
            case TUESDAY -> cuantos = 1;
            case WEDNESDAY -> cuantos = 2;
            case THURSDAY -> cuantos = 3;
            case FRIDAY -> cuantos = 4;
            case SATURDAY -> cuantos = 5;
            case SUNDAY -> cuantos = 6;
        }
        return cuantos;
    }


    private Node contenidoCentroMes() {
        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
        int n = 1;
        var lunes = new VBox(new Label("Lunes"));
        var martes = new VBox(new Label("Martes"));
        var miercoles = new VBox(new Label("Miercoles"));
        var jueves = new VBox(new Label("Jueves"));
        var viernes = new VBox(new Label("Viernes"));
        var sabado = new VBox(new Label("Sabado"));
        var domingo = new VBox(new Label("Domingo"));
        VBox[] dias = {lunes, martes, miercoles, jueves, viernes, sabado, domingo};
        for (VBox dia: dias){
            dia.setAlignment(Pos.TOP_CENTER);
            HBox.setHgrow(dia, Priority.ALWAYS);
        }
        var diasSemana = new HBox(lunes, martes, miercoles, jueves, viernes, sabado, domingo);
        diasSemana.setAlignment(Pos.TOP_LEFT);

        for (int i = 0; i < 31; i++) {
            var dia = new VBox(new Label(""+n));

        }
        var dia1 = new VBox(new Label("01"));
        var dia2 = new VBox(new Label("02"));
        var dia3 = new VBox(new Label("03"));
        var dia4 = new VBox(new Label("04"));
        var dia5 = new VBox(new Label("05"));
        var dia6 = new VBox(new Label("06"));
        var dia7 = new VBox(new Label("07"));
        VBox[] primerSemana = {dia1, dia2, dia3, dia4, dia5, dia6, dia7};
        for (VBox dias1 : primerSemana){
            dias1.setAlignment(Pos.TOP_LEFT);
            dias1.getChildren().addAll(new Label(""), new Label(""), new Label(""));
            dias1.setBorder(new Border(borde));
            HBox.setHgrow(dias1, Priority.ALWAYS);
        }
        var fila1 = new HBox(dia1, dia2, dia3, dia4, dia5, dia6, dia7);
        fila1.setAlignment(Pos.TOP_LEFT);
        fila1.setBorder(new Border(borde));

        var dia8 = new VBox(new Label("08"));
        var dia9 = new VBox(new Label("09"));
        var dia10 = new VBox(new Label("10"));
        var dia11 = new VBox(new Label("11"));
        var dia12 = new VBox(new Label("12"));
        var dia13 = new VBox(new Label("13"));
        var dia14 = new VBox(new Label("14"));
        VBox[] segundaSemana = {dia8, dia9, dia10, dia11, dia12, dia13, dia14};
        for (VBox dias2 : segundaSemana){
            dias2.setAlignment(Pos.TOP_LEFT);
            dias2.getChildren().addAll(new Label(""), new Label(""), new Label(""));
            dias2.setBorder(new Border(borde));
            HBox.setHgrow(dias2, Priority.ALWAYS);
        }
        var fila2 = new HBox(dia8, dia9, dia10, dia11, dia12, dia13, dia14);
        fila2.setAlignment(Pos.TOP_LEFT);
        fila2.setBorder(new Border(borde));

        var dia15 = new VBox(new Label("15"));
        var dia16 = new VBox(new Label("16"));
        var dia17 = new VBox(new Label("17"));
        var dia18 = new VBox(new Label("18"));
        var dia19 = new VBox(new Label("19"));
        var dia20 = new VBox(new Label("20"));
        var dia21 = new VBox(new Label("21"));
        VBox[] tercerSemana = {dia15, dia16, dia17, dia18, dia19, dia20, dia21};
        for (VBox dias3 : tercerSemana){
            dias3.setAlignment(Pos.TOP_LEFT);
            dias3.getChildren().addAll(new Label(""), new Label(""), new Label(""));
            dias3.setBorder(new Border(borde));
            HBox.setHgrow(dias3, Priority.ALWAYS);
        }
        var fila3 = new HBox(dia15, dia16, dia17, dia18, dia19, dia20, dia21);
        fila3.setAlignment(Pos.TOP_LEFT);
        fila3.setBorder(new Border(borde));

        var dia22 = new VBox(new Label("22"));
        var dia23 = new VBox(new Label("23"));
        var dia24 = new VBox(new Label("24"));
        var dia25 = new VBox(new Label("25"));
        var dia26 = new VBox(new Label("26"));
        var dia27 = new VBox(new Label("27"));
        var dia28 = new VBox(new Label("28"));
        VBox[] cuartaSemana = {dia22, dia23, dia24, dia25, dia26, dia27, dia28};
        for (VBox dias4 : cuartaSemana){
            dias4.setAlignment(Pos.TOP_LEFT);
            dias4.getChildren().addAll(new Label(""), new Label(""), new Label(""));
            dias4.setBorder(new Border(borde));
            HBox.setHgrow(dias4, Priority.ALWAYS);
        }
        var fila4 = new HBox(dia22, dia23, dia24, dia25, dia26, dia27, dia28);
        fila4.setAlignment(Pos.TOP_LEFT);
        fila4.setBorder(new Border(borde));

        var dia29 = new VBox(new Label("29"));
        var dia30 = new VBox(new Label("30"));
        var dia31 = new VBox(new Label("31"));
        var diaaux1 = new VBox(new Label("01"));
        var diaaux2 = new VBox(new Label("02"));
        var diaaux3 = new VBox(new Label("03"));
        var diaaux4 = new VBox(new Label("04"));
        VBox[] quintaSemana = {dia29, dia30, dia31, diaaux1, diaaux2, diaaux3, diaaux4};
        for (VBox dias5 : quintaSemana){
            dias5.setAlignment(Pos.TOP_LEFT);
            dias5.getChildren().addAll(new Label(""), new Label(""), new Label(""));
            dias5.setBorder(new Border(borde));
            HBox.setHgrow(dias5, Priority.ALWAYS);
        }
        var fila5 = new HBox(dia29, dia30, dia31, diaaux1, diaaux2, diaaux3, diaaux4);
        fila5.setAlignment(Pos.TOP_LEFT);
        fila5.setBorder(new Border(borde));

        var centroMes = new VBox(diasSemana);
        centroMes.getChildren().addAll(fila1, fila2, fila3, fila4, fila5);
        VBox.setMargin(diasSemana, new Insets(10, 0,0,0));
        VBox.setMargin(fila1, new Insets(10,0,0,0));

        //quedaria hacer lo mismo para los dias que quedan, igualmente siento que esta mal. No puedo centrar todo correctamente,
        //y menos se me ocurre hacer que los numeros coincidan con los dias segun el mes. I NEED HELP
        return centroMes;
    }

    //Contenido del centro
    private void actualizarContenidoCentro() {
        switch (modalidad) {
            case DIA -> contenedor.setCenter(contenidoCentroDia());
            case SEMANA -> contenedor.setCenter(contenidoCentroSemana());
            case MES -> contenedor.setCenter(contenidoCentroMes());
        }
    }
    private Node contenidoCentroSemana() {
        int n = fechaSeleccionada.getDayOfMonth();
        int max = fechaSeleccionada.lengthOfMonth();
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



    //Barra superior...

    /**
     * Crea una caja de opciones (Dia, Semana, Mes),
     * y se configuran las aciones a realizar en caso de qué modalidad se seleccione
     */
    private Node choiceBoxModalidad() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Dia", "Semana", "Mes");
        choiceBox.setValue("Semana");//formato semana por defecto

        // Acciones a realizar cuando se modifica la opción seleccionada
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                this.modalidad = Modalidades.DIA;
                this.formatter = FORMATTER_FECHA;
                this.contenidoCentro = contenidoCentroDia();
                contenedor.setCenter(contenidoCentro);
            } else if (newValue.intValue() == 1) {
                modalidad = Modalidades.SEMANA;
                formatter = FORMATTER_FECHA;
                this.contenidoCentro = contenidoCentroSemana();
                contenedor.setCenter(contenidoCentro);
            } else if (newValue.intValue() == 2) {
                modalidad = Modalidades.MES;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");//Estaria bueno que diga "Mayo 2023"
                contenidoCentro = contenidoCentroMes();
                contenedor.setCenter(contenidoCentro);
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
            actualizarContenidoCentro();
            //this.contenedor.setCenter(contenidoCentro);
        });
        var flechaDerecha = new Button("->");
        flechaDerecha.setOnAction(actionEvent -> {
            avanzarFecha();
            fecha.setText(this.fechaSeleccionada.format(formatter));
            actualizarContenidoCentro();
            //this.contenedor.setCenter(contenidoCentroSemana());
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
        var espacioIzquierda = choiceBoxModalidad();
        var espacioDerecha = labelFechaHoraActual();
        var espacioCentro =  bloqueSeleccionarFecha();

        HBox.setHgrow(espacioCentro,Priority.ALWAYS);
        var contenedor = new HBox(espacioIzquierda, espacioCentro, espacioDerecha);
        return contenedor;
    }



    //barra inferior...
    private ChoiceBox choiceBoxDeFrecuencia(){
        var choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Ninguna","Diaria", "Semanal", "Mensual", "Anual");
        choiceBox.setValue("Ninguna");
        return choiceBox;
    }
    private void abrirVentanaEmergente() {
        Dialog<Tarea> dialog = new Dialog<>();
        dialog.setTitle("Agregar un Tarea");
        var tituloField = new TextField();
        var descripcionField = new TextField();
        var fechaInicio = new DatePicker();
        var horario = new TextField();//debe ingresar en formato "hh:mm"
        var frecuencia = choiceBoxDeFrecuencia();

        //contenido del formulario
        dialog.getDialogPane().setContent(
                new VBox(30,
                        new Label("Titulo:"), tituloField,
                        new Label("Descripcion:"), descripcionField,
                        new Label("Fecha de Inicio"), fechaInicio,
                        new Label("Horario 'hh:mm'"), horario,
                        new Label("Frecuencia"), frecuencia)
                );

        // Configurar los botones de Aceptar y Cancelar
        ButtonType btnAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAceptar, btnCancelar);

        // Validar los campos de texto antes de aceptar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAceptar) {
                if (fechaInicio.getValue() == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Campos Vacíos");
                    alert.showAndWait();
                    return null;
                }
                return new Tarea(tituloField.getText(),descripcionField.getText(),
                                 fechaInicio.getValue(),true,null,null);
            }
            return null;
        });

        // Mostrar el diálogo y esperar hasta que se cierre
        dialog.showAndWait().ifPresent(result -> {
            var titulo = result.getTitulo();
            // Realizar acciones con los valores del formulario (por ejemplo, iniciar sesión)
            System.out.println("Titulo: " + titulo);
        });
    }

    public Node contenidoBarraInferior() {
        var botonAgregarEvento = new Button("agregar evento/Tarea");
        botonAgregarEvento.setOnAction(actionEvent -> {
            abrirVentanaEmergente();
            //habria que lanzar como una ventana (cuadro) donde se pueda poner los datos del evento con un boton para confirmar
            System.out.println("Se agrego el evento");
        });

        var barraInferior = new StackPane(botonAgregarEvento);
        return barraInferior;
    }





    @Override
    public void start(Stage stage) throws Exception {
        fechaActual = LocalDate.now();
        horaActual = LocalTime.now();
        fechaSeleccionada = fechaActual;


        contenidoCentro = contenidoCentroMes();
        //contenidoCentro.setStyle("-fx-background-color: green;");//para probar. Despues lo saco

        var barraSuperior = contenidoBarraSuperior();
        barraSuperior.setStyle("-fx-background-color: #683b3b;");//para probar. Despues lo saco

        var barraIzquierda = new StackPane(new Label("  "));//para agregar un espacio de margen
        var barraDerecha = new StackPane(new Label("  "));//para agregar un espacio de margen


        var barraInferior = contenidoBarraInferior();



        contenedor = new BorderPane();
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
