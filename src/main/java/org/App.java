package org;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.*;
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


    private void irAlLunes(){
        while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {//para tener el lunes seleccionado y simplificar cosas
            fechaSeleccionada = fechaSeleccionada.minusDays(1);
        }
    }
    private void irAlPrimerDiaDelMes(){
        while (fechaSeleccionada.getDayOfMonth() != 1) {//para tener el dia 1 del mes
            fechaSeleccionada = fechaSeleccionada.minusDays(1);
        }
    }
    private void retrocederFecha() {
        switch (modalidad) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.minusDays(1);
            case SEMANA -> {
                irAlLunes();
                fechaSeleccionada = fechaSeleccionada.minusWeeks(1);
            }
            case MES -> {
                irAlPrimerDiaDelMes();
                fechaSeleccionada = fechaSeleccionada.minusMonths(1);
            }
        }
    }
    private void avanzarFecha() {
        switch (modalidad) {
            case DIA -> fechaSeleccionada = fechaSeleccionada.plusDays(1);
            case SEMANA -> {
                irAlLunes();
                fechaSeleccionada = fechaSeleccionada.plusWeeks(1);
            }
            case MES -> {
                irAlPrimerDiaDelMes();
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

    private void mostrarEventosEnLaInterfazMes(VBox casilla, int dia){
        LocalDateTime fechaHoraInicio = LocalDateTime.of(2023,6,12,14,30);
        LocalDateTime fechaHoraFin = LocalDateTime.of(2023,6,12,18,30);
        var evento1 = new Evento("Evento 1", "Este es el evento 1",
                fechaHoraInicio,fechaHoraFin,false,null);
        var labelEvento = new Label(evento1.getTitulo());

        labelEvento.textAlignmentProperty().set(TextAlignment.LEFT);
        labelEvento.setOnMouseClicked(a -> {
            //mostrar todos los datos del evento
            //...
        });

        if (evento1.getFechaInicio().getDayOfYear() == dia){
            casilla.getChildren().add(labelEvento);
        }
    }

    private void crearDias(int[] auxiliares, HBox fila, int posicionDiaUno, int cantidadDiasMes, BorderStroke borde){
        int semana = 7;
        for (int i = 1; i <= semana; i++){
            String numero;
            boolean aux = false;
            if (auxiliares[1] < posicionDiaUno || auxiliares[0] > cantidadDiasMes){
                numero = "00";
                auxiliares[1]++;
                aux = true;
            } else {
                numero = String.valueOf(auxiliares[0]);
                if (numero.length() == 1){
                    numero = "0" + auxiliares[0];
                }
                auxiliares[0]++;
            }
            var dia = new VBox(new Label(numero));
            dia.setAlignment(Pos.TOP_LEFT);
            dia.setBorder(new Border(borde));
            /*if (numero.equals("00")){
                dia.setStyle("-fx-background-color: #f2f2f2;");
            }*/
            if (!aux && auxiliares[0] < cantidadDiasMes){
                mostrarEventosEnLaInterfazMes(dia, this.fechaSeleccionada.withMonth
                        (this.fechaSeleccionada.getMonth().getValue()).withDayOfMonth(auxiliares[0] - 1).getDayOfYear());
            }
            HBox.setHgrow(dia, Priority.ALWAYS);
            VBox.setVgrow(dia, Priority.ALWAYS);
            fila.getChildren().add(dia);
        }
    }

    //Contenido del centro
    private Node contenidoCentroMes() {

        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
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

        Month mes = this.fechaSeleccionada.getMonth();
        int diasMes = this.fechaSeleccionada.lengthOfMonth();
        DayOfWeek diaUno = this.fechaSeleccionada.withMonth(mes.getValue()).withDayOfMonth(1).getDayOfWeek();
        int posicion = diaUno.getValue();

        int n = 1;
        int aux = 1;
        int[] auxiliares= {n, aux};

        var centroMes = new VBox(diasSemana);
        for (int i = 0; i < 5; i++){
            HBox fila = new HBox();
            crearDias(auxiliares, fila, posicion, diasMes, borde);
            VBox.setVgrow(fila, Priority.ALWAYS);
            centroMes.getChildren().add(fila);
        }

        if (auxiliares[0] <= diasMes){
            HBox fila6 = new HBox();
            crearDias(auxiliares, fila6, posicion, diasMes, borde);
            VBox.setVgrow(fila6, Priority.ALWAYS);
            centroMes.getChildren().add(fila6);
        }
        return centroMes;
    }

    private void actualizarContenidoCentro() {
        switch (modalidad) {
            case DIA -> contenedor.setCenter(contenidoCentroDia());
            case SEMANA -> contenedor.setCenter(contenidoCentroSemana());
            case MES -> contenedor.setCenter(contenidoCentroMes());
        }
    }

    /**
     * Recibe un numero del 0 al 6 y devuelve el nombre del dia de la semana
     * 0 -> "Lunes"
     * */
    private String nDiaDeLaSemana(int n) {
        if (n == 0)
            return "Lunes\n";
        if (n == 1)
            return "Martes\n";
        if (n == 2)
            return "Miercoles\n";
        if (n == 3)
            return "Jueves\n";
        if (n == 4)
            return "Viernes\n";
        if (n == 5)
            return "Sabado\n";
        if (n == 6)
            return "Domingo\n";
        return "";
    }

    //tengo que tener todos los eventos que ocurren en la semana actual
    /**
     * Recibe una columna de uno de los dias de la semana, y
     * agrega todos los eventos que ocurren en ese dia en la columna recibida
     * */
    private void mostrarEventosEnLaInterfazSemana(VBox columnaDia, DayOfWeek diaSemana){
        //un evento de ejemplo:
        LocalDateTime fechaHoraInicio = LocalDateTime.of(2023,6,12,14,30);
        LocalDateTime fechaHoraFin = LocalDateTime.of(2023,6,12,18,30);
        var evento1 = new Evento("Evento 1", "Este es el evento 1",
                                fechaHoraInicio,fechaHoraFin,false,null);

        var labelEvento = new Label(evento1.getHoraInicio().format(DateTimeFormatter.ofPattern("hh:mm")) + "-" +
                                    evento1.getHoraFin().format(DateTimeFormatter.ofPattern("hh:mm")) + ": " +
                                    evento1.getTitulo());
        labelEvento.textAlignmentProperty().set(TextAlignment.LEFT);
        labelEvento.setOnMouseClicked(a -> {
            //mostrar todos los datos del evento
            //...
        });

        //si el evento ocurre este dia de la semana...
        if (evento1.getFechaInicio().getDayOfWeek().equals(diaSemana)) {
            columnaDia.getChildren().add(labelEvento);
        }
    }
    /**
     *
     */
    private Node contenidoCentroSemana() {
        int n = fechaSeleccionada.getDayOfMonth();
        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
        var contenido = new HBox();
        contenido.setAlignment(Pos.TOP_CENTER);

        int j = 1;//numero de dia del mes siguiente
        for (int i = 0; i < 7; i++) {
            var col = new VBox();
            var stackPane = new StackPane();
            stackPane.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black;");//borde inferior
            var label = new Label();
            label.setTextAlignment(TextAlignment.CENTER);
            String dia = nDiaDeLaSemana(i);//calcular dia

            if (n+i > fechaSeleccionada.lengthOfMonth()) {//si me paso del mes
                label.setText(dia+j);
                j++;
            }
            else{
                label.setText(dia+(n+i));
            }
            stackPane.getChildren().add(label);
            col.getChildren().add(stackPane);

            col.setBorder(new Border(borde));
            mostrarEventosEnLaInterfazSemana(col,fechaSeleccionada.plusDays(i).getDayOfWeek());
            HBox.setHgrow(col, Priority.ALWAYS);

            contenido.getChildren().add(col);
        }
        return contenido;
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
                contenedor.setCenter(contenidoCentroDia());
            }
            else if (newValue.intValue() == 1) {
                modalidad = Modalidades.SEMANA;
                formatter = FORMATTER_FECHA;
                contenedor.setCenter(contenidoCentroSemana());
            }
            else if (newValue.intValue() == 2) {
                modalidad = Modalidades.MES;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");//Estaria bueno que diga "Mayo 2023"
                contenedor.setCenter(contenidoCentroMes());
            }
        });
        return choiceBox;
    }

    /**
     * Crea un objeto Label con la fecha y hora actuales, que se actualizan cada 1 segundo
     * */
    private Node labelFechaHoraActual() {
        var labelFechaHora = new Label(horaActual.format(FORMATTER_HORA) +"\n"+ fechaActual.format(FORMATTER_FECHA));
        labelFechaHora.setTextAlignment(TextAlignment.CENTER);//para centrar el texto
        labelFechaHora.setFont(Font.font(15));//para agrandar el tamaño de la fuente

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
     * con flechas para avanzar o retroceder esa fecha.
     * Al pulsar alguno de los botones se actualiza el contenido centro
     */
    private Node bloqueSeleccionarFecha() {
        var labelFecha = new Label(this.fechaSeleccionada.format(formatter));
        labelFecha.setFont(Font.font(20));

        var flechaIzquierda = new Button("<-");
        flechaIzquierda.setFont(Font.font(15));
        flechaIzquierda.setOnAction(actionEvent -> {
            retrocederFecha();
            labelFecha.setText(this.fechaSeleccionada.format(formatter));
            actualizarContenidoCentro();
        });
        var flechaDerecha = new Button("->");
        flechaDerecha.setFont(Font.font(15));
        flechaDerecha.setOnAction(actionEvent -> {
            avanzarFecha();
            labelFecha.setText(this.fechaSeleccionada.format(formatter));
            actualizarContenidoCentro();
        });

        var flechas = new TilePane(flechaIzquierda, flechaDerecha);
        flechas.setAlignment(Pos.CENTER);

        var contenedor = new VBox(labelFecha, flechas);
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


    private ChoiceBox choiceBoxDeFrecuencia(VBox caja){
        var choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Ninguna","Diaria");//lo que pide el enunciado
        choiceBox.setValue("Ninguna");
        var spinner = new Spinner<>(0,31,0);//numero entre 0 y 31
        spinner.setPrefWidth(70);
        var labelIntervalo = new Label("Intervalo", spinner);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                caja.getChildren().remove(labelIntervalo);
            }
            else if (newValue.intValue() == 1) {
                caja.getChildren().add(labelIntervalo);
            }

        });

        return choiceBox;
    }
    private CheckBox crearCheckBox(VBox caja, Node horario) {
     CheckBox checkBox = new CheckBox();
     checkBox.setOnAction(actionEvent -> {
         if (checkBox.isSelected())
             caja.getChildren().get(6).setVisible(false);
         else
             caja.getChildren().get(6).setVisible(true);
     });
     return checkBox;
    }
    //private void abrirVentanaEmergente() {
    //        Dialog<Tarea> dialog = new Dialog<>();
    //        dialog.setTitle("Agregar un Tarea");
    //        var tituloField = new TextField();
    //        var descripcionField = new TextField();
    //        var fechaInicio = new DatePicker();
    //        var todoElDia = new CheckBox();
    //        var horario = new TextField();//debe ingresar en formato "hh:mm"
    //        var frecuencia = choiceBoxDeFrecuencia();
    //        var intervalo = new Spinner<>(0,31,0);//numero entre 0 y 31
    //
    //        //contenido del formulario
    //        dialog.getDialogPane().setContent(
    //                new VBox(14,
    //                        new Label("Titulo:"), tituloField,
    //                        new Label("Descripcion:"), descripcionField,
    //                        new Label("Fecha de Inicio"), fechaInicio,
    //                        new Label("Todo el dia", todoElDia),
    //                        new Label("Horario 'hh:mm'"), horario,
    //                        new Label("Frecuencia"), frecuencia,
    //                        new Label("Intervalo", intervalo))
    //                );
    //
    //        // Configurar los botones de Aceptar y Cancelar
    //        ButtonType btnAceptar = new ButtonType("Aceptar", ButtonBar.ButtonData.OK_DONE);
    //        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
    //        dialog.getDialogPane().getButtonTypes().addAll(btnAceptar, btnCancelar);
    //
    //        // Validar los campos de texto antes de aceptar
    //        dialog.setResultConverter(dialogButton -> {
    //            if (dialogButton == btnAceptar) {
    //                if (fechaInicio.getValue() == null){
    //                    Alert alert = new Alert(Alert.AlertType.WARNING);
    //                    alert.setTitle("Campos Vacíos");
    //                    alert.showAndWait();
    //                    return null;
    //                }
    //                Calendario c = new Calendario();//Temporal (el calendario tiene que ser un atributo)
    //                var tarea = c.crearTarea(tituloField.getText(),descripcionField.getText(),
    //                        fechaInicio.getValue().format(FORMATTER_FECHA),
    //                        todoElDia.allowIndeterminateProperty().getValue(),
    //                        horario.getText(),null);
    //
    //                return tarea;
    //            }
    //            return null;
    //        });
    //
    //        // Mostrar el diálogo y esperar hasta que se cierre
    //        dialog.showAndWait();
    //
    //        System.out.println("Titulo ingresado: " + tituloField.getText());
    //        System.out.println("Descripcion: " + descripcionField.getText());
    //        System.out.println("fecha: " + fechaInicio.getValue());
    //        System.out.println("Horario: " + horario.getText());
    //    }


    private Node contenidoTareaDeVentanaEmergente(){

        var tituloField = new TextField();
        var descripcionField = new TextArea();
        var fechaField = new TextField();
        var horarioField = new TextField();
        var labelHorario = new Label("Horario: ", horarioField);
        var todoElDiaCheck = new CheckBox();

        
        var contenido = new VBox(new Label("Titulo: "), tituloField, new Label("Descripcion:"),descripcionField,
                                 new Label("Fecha: "), fechaField, labelHorario);
        
        
        contenido.getChildren().add(new Label("Todo el dia: ", crearCheckBox(contenido,labelHorario)));
        contenido.getChildren().add(new Label("Frecuencia: "));
        contenido.getChildren().add(choiceBoxDeFrecuencia(contenido));

        return contenido;
    }
    private void abrirVentanaEmergente2(){
        var ventana = new Stage();

        var contenido = (VBox) contenidoTareaDeVentanaEmergente();
        ventana.setTitle("Agregar Tarea");

        var escena = new Scene(contenido,250,430);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.initOwner(ventanaPrincipal);
        ventana.setResizable(false);
        ventana.setScene(escena);
        ventana.showAndWait();
    }
    /**
     * Contiene un boton para agregar Tareas al Calendario y otro para agregar Eventos
     * */
    public Node contenidoBarraInferior() {
        var botonAgregarEvento = new Button("agregar Evento");
        var botonAgregarTarea = new Button("agregar Tarea");
        botonAgregarTarea.setOnAction(actionEvent -> {
            abrirVentanaEmergente2();

            System.out.println("Se agrego el evento");
        });
        botonAgregarEvento.setOnAction(actionEvent -> {

        });

        var barraInferior = new StackPane(botonAgregarEvento);
        barraInferior.getChildren().add(botonAgregarTarea);
        return barraInferior;
    }





    private Stage ventanaPrincipal;
    @Override
    public void start(Stage stage) throws Exception {
        fechaActual = LocalDate.now();
        horaActual = LocalTime.now();
        fechaSeleccionada = fechaActual;
        ventanaPrincipal = stage;


        contenidoCentro = contenidoCentroSemana();
        //contenidoCentro.setStyle("-fx-background-color: green;");//para probar. Despues lo saco

        var barraSuperior = contenidoBarraSuperior();
        barraSuperior.setStyle("-fx-background-color: #34a9c9;");//para probar. Despues lo saco

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
         * Al hacer  click en un evento mostrar la info

         * Mejorar las proporciones, tamaños  (estetica)

         * Usar los datos ingresados para crear un evento/tarea

         * que se vean los eventos y tareas en las fechas correspondientes ordenados por hora
         *
         */



        var sceneSemana = new Scene(contenedor, 640, 480);
        stage.setScene(sceneSemana);
        stage.show();
    }
}
