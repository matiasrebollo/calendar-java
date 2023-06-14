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
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    enum Modalidades {DIA, SEMANA, MES};
    private Modalidades modalidad = Modalidades.SEMANA;
    private static final DateTimeFormatter FORMATTER_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter formatter = FORMATTER_FECHA;

    private Calendario calendario;
    private LocalDate fechaSeleccionada;
    private Label labelFecha = new Label();

    private LocalDate fechaActual;
    private LocalTime horaActual;

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

    private VBox contenidoPrincipal(ElementoCalendario elemento){
        var titulo = new VBox(new Label("Titulo:   "), new Label(elemento.getTitulo()));
        var descripcion = new VBox(new Label("Descripcion:   "), new Label(elemento.getDescripcion()));
        var fechaYHoraInicio = new VBox();
        var fechaYHoraFin = new VBox();
        var duracion = new VBox();
        var completada = new VBox();
        if (elemento.getTypename().equals("Evento")){
            Evento evento = (Evento) elemento;
            if (evento.esTodoElDia()){
                fechaYHoraInicio.getChildren().addAll(new Label("Fecha inicio:   "),
                        new Label(evento.getFechaInicio().format(DateTimeFormatter.ofPattern("d/M/yyyy"))));
                fechaYHoraFin.getChildren().addAll(new Label("Fecha fin:   "),
                        new Label(evento.getFechaFin().format(DateTimeFormatter.ofPattern("d/M/yyyy"))));
                duracion.getChildren().addAll(new Label("Duracion:"), new Label("Todo el día"));
            } else {
                fechaYHoraInicio.getChildren().addAll(new Label("Fecha y hora inicio:   "),
                        new Label(evento.getFechaInicio().format(DateTimeFormatter.ofPattern("d/M/yyyy"))),
                        new Label(evento.getHoraInicio().format(DateTimeFormatter.ofPattern("hh:mm"))));
                fechaYHoraFin.getChildren().addAll(new Label("Fecha y hora fin:   "),
                        new Label(evento.getFechaFin().format(DateTimeFormatter.ofPattern("d/M/yyyy"))),
                        new Label(evento.getHoraFin().format(DateTimeFormatter.ofPattern("hh:mm"))));
            }
        } else {
            Tarea tarea = (Tarea) elemento;
            if (tarea.esTodoElDia()){
                fechaYHoraInicio.getChildren().addAll(new Label("Fecha inicio:   "),
                        new Label(tarea.getFechaInicio().format(DateTimeFormatter.ofPattern("d/M/yyyy"))));
                duracion.getChildren().addAll(new Label("Duracion:"), new Label("Todo el día"));
            } else {
                fechaYHoraInicio.getChildren().addAll(new Label("Fecha y hora inicio:   "),
                        new Label(tarea.getFechaInicio().format(DateTimeFormatter.ofPattern("d/M/yyyy"))),
                        new Label(tarea.getHoraInicio().format(DateTimeFormatter.ofPattern("hh:mm"))));
            }
            if (tarea.estaCompletada()){
                completada.getChildren().addAll(new Label("Estado:"), new Label("Completada"));
            } else {
                completada.getChildren().addAll(new Label("Estado:"), new Label("Incompleta"));
            }
        }
        var frecuencia = new HBox(new Label("Frecuencia:   "), new Label(elemento.getFrecuencia().getTipoFrecuencia()));
        ArrayList<Alarma> alarmas = elemento.getAlarmas();
        var enumeracion = new VBox();
        for (int i = 0; i < elemento.cantidadAlarmas(); i++){
            enumeracion.getChildren().add(new Label(alarmas.get(i).getFechaHoraAlarma().format(DateTimeFormatter.ofPattern("d/M/yyyy hh:mm"))));
        }
        var alarma = new HBox(new Label("Alarmas:   "), enumeracion);

        VBox formulario = new VBox(20);
        formulario.getChildren().addAll(titulo, descripcion, fechaYHoraInicio, fechaYHoraFin, duracion, completada, frecuencia, alarma);

        return formulario;
    }

    private HBox contenidoAlarma(ElementoCalendario elemento, Stage ventana){
        var botonAlarma = new Button("Agregar alarma");
        botonAlarma.setOnAction(e -> {
            var ventanaAlarma = new Stage();
            ventanaAlarma.setTitle("Agregar alarma");
            var intervaloCasilla = new Spinner<>(1,60,0);
            intervaloCasilla.setPrefWidth(70);
            var unidadCasilla = new ChoiceBox<>();
            unidadCasilla.getItems().addAll("minutos","horas", "dias", "semanas");
            unidadCasilla.setValue("minutos");
            var alarmaCasilla = new HBox();
            alarmaCasilla.getChildren().addAll(new Label("Agregar alarma"), intervaloCasilla, unidadCasilla, new Label("antes."));
            alarmaCasilla.setAlignment(Pos.CENTER);
            alarmaCasilla.setSpacing(5);
            var mensaje = new HBox(new Label("La alarma será una notificación por defecto."));
            mensaje.setAlignment(Pos.CENTER);
            var contenidoAlarma = new VBox(alarmaCasilla, mensaje);
            contenidoAlarma.setSpacing(10);
            contenidoAlarma.setAlignment(Pos.CENTER);
            var botonAceptar = new Button("Aceptar");
            var contenedorAceptar = new HBox(botonAceptar);
            contenedorAceptar.setAlignment(Pos.CENTER);
            var contenido = new BorderPane();
            contenido.setCenter(contenidoAlarma);
            contenido.setBottom(contenedorAceptar);
            botonAceptar.setOnAction(actionEvent -> {
                int intervalo = (int) intervaloCasilla.getValue();
                Alarma.UnidadesDeTiempo unidad;
                if (unidadCasilla.getValue().toString().equals("minutos")){
                    unidad = Alarma.UnidadesDeTiempo.MINUTOS;
                } else if (unidadCasilla.getValue().toString().equals("horas")){
                    unidad = Alarma.UnidadesDeTiempo.HORAS;
                } else if (unidadCasilla.getValue().toString().equals("dias")){
                    unidad = Alarma.UnidadesDeTiempo.DIAS;
                } else {
                    unidad = Alarma.UnidadesDeTiempo.SEMANAS;
                }
                elemento.agregarAlarma(null, intervalo, unidad, Alarma.EfectosAlarma.NOTIFICACION);
                System.out.println("Se agregó la alarma.");
                ventanaAlarma.close();
                ventana.close();
            });

            var escena = new Scene(contenido, 335, 100);
            ventanaAlarma.initModality(Modality.APPLICATION_MODAL);
            ventanaAlarma.initOwner(ventanaPrincipal);
            ventanaAlarma.setResizable(false);
            ventanaAlarma.setScene(escena);
            ventanaAlarma.showAndWait();

        });
        return new HBox(botonAlarma);
    }

    private void informacionElemento(ElementoCalendario elemento){

        var ventana = new Stage();
        ventana.setTitle("Informacion" + elemento.getTypename());
        VBox formulario = contenidoPrincipal(elemento);
        HBox contenedorAlarma = contenidoAlarma(elemento, ventana);
        contenedorAlarma.setAlignment(Pos.CENTER);
        var contenido = new BorderPane();
        contenido.setCenter(formulario);
        contenido.setBottom(contenedorAlarma);

        var escena = new Scene(contenido,280,450);
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.initOwner(ventanaPrincipal);
        ventana.setResizable(false);
        ventana.setScene(escena);
        ventana.showAndWait();
    }

    private void mostrarEventosEnLaInterfazMes(VBox casilla, int dia){
        LocalDateTime fechaHoraInicio = LocalDateTime.of(2023,6,12,14,30);
        LocalDateTime fechaHoraFin = LocalDateTime.of(2023,6,12,18,30);
        LocalDate fechaInicio = LocalDate.of(2023,6,12);
        var frecuencia = new FrecuenciaDiaria(fechaInicio, 2, 3);
        var evento1 = new Evento("Evento 1", "Este es el evento 1",
                fechaHoraInicio,fechaHoraFin,false,frecuencia);
        var labelEvento = new Label(evento1.getTitulo());

        labelEvento.textAlignmentProperty().set(TextAlignment.LEFT);
        labelEvento.setOnMouseClicked(a -> {
            informacionElemento(evento1);
        });
        /*êstoy entre dos cosas al usar el array, llamar a obtenerlementosenunlapsodedias, mandando fechaseleccionada.primerdiadelmes, y
        fechasleccionada.cantidaddiasmes, y despues iterar con la funcion de abajo.
        o directamente mandarle a esa funcion el parametro dia, convertido a un LocalDate, y cantidad de dias 1. esto ahorraria iteraciones.
         esto debido a como tengo implementado mi funcion de crear dias. Esta llama una vez por casilla de dia, entonces, no se si
         iterar 30 veces por cada vez que llamo a esta funcion, quizas mejor mandar el dia en el que estoy parado, y obtener un arraylist
         con los eventos y tareas de ese dia, e iterarlos.*/
        if (evento1.getFechaInicio().getDayOfYear() == dia){
            casilla.getChildren().add(labelEvento);
        }
    }

    private void crearDias(int[] auxiliares, HBox fila, int posicionDiaUno, int cantidadDiasMes, BorderStroke borde){
        int semana = 7;
        for (int i = 1; i <= semana; i++){
            String numero; //auxiliares 0 es el numero que se printea en la casilla, auxiliares 1 es un auxiliar para printear 00 hasta que se llegue a la posicion del dia uno.
            boolean aux = false;
            if (auxiliares[1] < posicionDiaUno || auxiliares[0] > cantidadDiasMes){
                numero = "00";
                auxiliares[1]++;
                aux = true; // esta solo para que no rompa mostrareventosenlainteraz, es para saber si se entró a este if.
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
            if (numero.equals("00")){
                dia.setStyle("-fx-background-color: #c22d2d;");
            }
            if (!aux && auxiliares[0] < cantidadDiasMes){
                mostrarEventosEnLaInterfazMes(dia, this.fechaSeleccionada.withMonth
                        (this.fechaSeleccionada.getMonth().getValue()).withDayOfMonth(auxiliares[0] - 1).getDayOfYear());//entero que representa el dia del año.
            }
            HBox.setHgrow(dia, Priority.ALWAYS);
            VBox.setVgrow(dia, Priority.ALWAYS);
            fila.getChildren().add(dia);
        }
    }

    //Contenido del centro
    private Node contenidoCentroMes() {
        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);

        var diasSemana = new HBox();
        for (int i = 0; i < 7; i++) {
            var diaLabel = new StackPane(new Label(nombreDiaDeLaSemana(i)));
            HBox.setHgrow(diaLabel,Priority.ALWAYS);
            diasSemana.getChildren().add(diaLabel);
        }
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

        if (auxiliares[0] <= diasMes){//si falta un fila, se agrega una mas...
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
    private String nombreDiaDeLaSemana(int n) {
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
    private void mostrarEventosEnLaInterfazSemana(VBox columnaDia, LocalDate fecha){
        //un evento de ejemplo:
        LocalDateTime fechaHoraInicio = LocalDateTime.of(2023,6,12,14,30);
        LocalDateTime fechaHoraFin = LocalDateTime.of(2023,6,12,18,30);
        var evento1 = new Evento("Evento 1", "Este es el evento 1",
                fechaHoraInicio,fechaHoraFin,true,null);
        //una Tarea de ejemplo:
        var tarea = new Tarea("tarea0", "xd", fechaHoraInicio.toLocalDate(),false,
                LocalTime.of(19,40),null);

        ArrayList<ElementoCalendario> array= new ArrayList<>();
        array.add(evento1); array.add(tarea);

        for (ElementoCalendario elemento : array) {
            var labelTitulo = new Label(elemento.getTitulo());
            var labelHora = new HBox();
            if (elemento.getTypename().equals("Evento")){
                Evento e = (Evento) elemento;
                if (e.esTodoElDia()){
                    labelHora.getChildren().add(new Label("Todo el dia: "));
                } else {
                    labelHora.getChildren().add(new Label(e.getHoraInicio().toString() + " : " + e.getHoraFin().toString()));
                }
                labelTitulo.setStyle("-fx-border-color: gray");
            }
            else {
                Tarea t = (Tarea) elemento;
                if (t.esTodoElDia()){
                    labelHora.getChildren().add(new Label("Todo el dia: "));
                } else {
                    labelHora.getChildren().add(new Label(elemento.getHoraInicio().toString() + " "));
                }
                labelTitulo.setStyle("-fx-border-color: red");
            }

            var hBox= new HBox(5, labelHora,labelTitulo);
            hBox.setAlignment(Pos.CENTER_LEFT);
            if (modalidad == Modalidades.DIA){
                hBox.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black;");
            }
            labelTitulo.setOnMouseClicked(e-> {
                informacionElemento(elemento);
            });
            if (elemento.ocurreEnFecha(fecha)) {
                columnaDia.getChildren().add(hBox);
            }
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
            String dia = nombreDiaDeLaSemana(i);//calcular dia

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
            mostrarEventosEnLaInterfazSemana(col,fechaSeleccionada.plusDays(i));
            HBox.setHgrow(col, Priority.ALWAYS);

            contenido.getChildren().add(col);
        }
        return contenido;
    }

    /**
     *
     * */
    private Node contenidoCentroDia(){
        //ver si poner que dia es cada dia
        var columna = new VBox(7);
        mostrarEventosEnLaInterfazSemana(columna, fechaSeleccionada);
        HBox.setHgrow(columna, Priority.ALWAYS);

        return columna;
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
            }
            else if (newValue.intValue() == 1) {
                modalidad = Modalidades.SEMANA;
                formatter = FORMATTER_FECHA;
                irAlLunes();
            }
            else if (newValue.intValue() == 2) {
                modalidad = Modalidades.MES;
                formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                irAlPrimerDiaDelMes();
            }
            labelFecha.setText(fechaSeleccionada.format(formatter));
            actualizarContenidoCentro();
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
        labelFecha = new Label(this.fechaSeleccionada.format(formatter));
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
        return new HBox(espacioIzquierda, espacioCentro, espacioDerecha);
    }



    //____________________________________________________________________________________________________
    //barra inferior...
    //

    /***
     * Crea un VBox qu contendrá en cada fila los campos recibidos
     */
    private VBox itemsDeFrecuencia(Spinner intervalo, Spinner ocurrencias, CheckBox infinitas) {
        var caja = new VBox();
        intervalo.setPrefWidth(70);
        ocurrencias.setPrefWidth(70);
        var label = new Label("intervalo:",intervalo);
        var label2 = new Label("Infinitas", infinitas);
        var label3 = new Label("ocurrencias", ocurrencias);
        caja.getChildren().addAll(label, label2, label3);
        infinitas.setOnAction(e -> {
            if (infinitas.isSelected())
                caja.getChildren().get(2).setVisible(false);
            else
                caja.getChildren().get(2).setVisible(true);
        });
        return caja;
    }
    /***
     * crea una ChoiceBox con las opciones de frecuencia:
     * "Ninguna": quita los items de Frecuencia de la caja
     * "Diaria": agrega los items de Frecuencia en la caja
     * "Semanal":...
     */
    private ChoiceBox choiceBoxDeFrecuencia(VBox contenedor, VBox frecuenciaItems){
        var choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Ninguna","Diaria");//lo que pide el enunciado
        choiceBox.setValue("Ninguna");

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0)
                contenedor.getChildren().remove(frecuenciaItems);
            else if (newValue.intValue() == 1)
                contenedor.getChildren().add(frecuenciaItems);
        });
        return choiceBox;
    }

    /**
     * crea un checkBox que
     * si está seleccionado oculta el elemento número 6 del formulario
     * si NO está seleccionado visibiliza el elemento numero 6 del formulario
     * */
    private CheckBox CheckBoxTodoElDia(VBox formulario) {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(actionEvent -> {
            if (checkBox.isSelected())
                formulario.getChildren().get(6).setVisible(false);
            else
                formulario.getChildren().get(6).setVisible(true);
        });
        return checkBox;
    }

    /**
     * Recibe la ventana (Stage) y todos los campos del formulario
     * Crea 2 botones:
     * Aceptar: lee todos los datos recibidos y si la fecha es válida
     *          crea la tarea en el calendario
     * Cancelar: cierra la ventana

     * Devuelve un HBox con los dos botones.
     * */
    private HBox crearBotonesVentanaTarea(Stage ventana, String elemento, List<Control> camposFormulario) {
        var botonAceptar = new Button("Aceptar");
        botonAceptar.setOnAction(e -> {//leer los datos ingresados por el usuario
            var titulo = ((TextField) camposFormulario.get(0)).getText();
            var descripcion = ((TextArea) camposFormulario.get(1)).getText();
            var fechaPicker = (DatePicker) camposFormulario.get(2);
            var horaString = ((Spinner) camposFormulario.get(3)).getValue().toString() + ":" +
                    ((Spinner) camposFormulario.get(4)).getValue().toString();
            boolean todoElDia = ((CheckBox) camposFormulario.get(5)).isSelected();
            var frecuenciaChoice = (ChoiceBox) camposFormulario.get(6);
            int intervalo = (int) ((Spinner) camposFormulario.get(7)).getValue();
            int ocurrencias = (int) ((Spinner) camposFormulario.get(8)).getValue();
            var infinitas = (CheckBox) camposFormulario.get(9);
            var fechaFinPicker = (DatePicker) camposFormulario.get(10);
            var horaFinString = ((Spinner) camposFormulario.get(11)).getValue().toString() + ":" +
                    ((Spinner) camposFormulario.get(12)).getValue().toString();
            var fecha = fechaPicker.getValue();
            if (fecha == null)
                return;

            Frecuencia frecuencia = null;
            switch (frecuenciaChoice.getValue().toString()){
                case "Ninguna"-> frecuencia = new FrecuenciaCero(fecha);
                case "Diaria" ->{
                    if (infinitas.isSelected())
                        frecuencia = new FrecuenciaDiaria(fecha, intervalo, null);
                    else
                        frecuencia = new FrecuenciaDiaria(fecha,intervalo, ocurrencias);
                }
            }
            if (elemento.equals("Evento")) {
                LocalDate fechaFin = fechaFinPicker.getValue();
                if (fechaFin == null || fechaFin.isBefore(fecha))
                    return;// hubo un error

                //this.calendario.crearEvento(titulo,descripcion,fecha.format(FORMATTER_FECHA),
                //                          fechaFin.format(FORMATTER_FECHA),horaString,
                //                        horaFinString,todoElDia,frecuencia);
                System.out.println("Se creo el Evento");
            }
            else {
                //this.calendario.crearTarea();
                System.out.println("Se creó la tarea");
            }
            ventana.close();
        });
        var botonCancelar = new Button("Cancelar");
        botonCancelar.setOnAction(actionEvent -> {
            ventana.close();
        });

        var botones = new HBox(5, botonAceptar, botonCancelar);
        botones.setAlignment(Pos.CENTER);
        return botones;
    }



    /***
     * Crea un formulario con todos los campos necesarios para
     * crear un Evento o Tarea.
     */
    private BorderPane contenidoVentanaEmergente(Stage ventana, String string) {
        VBox formulario = new VBox(5);//5px de espacio entre bloques
        var tituloField = new TextField();
        var descripcionField = new TextArea(); descripcionField.setPrefHeight(90);//ajusto el tamaño
        var fechaField = new DatePicker(); fechaField.setEditable(false);
        var fechaVBox = new VBox(new Label("Fecha:"), fechaField);
        var horaSpinner = new Spinner<>(0,23, 0); horaSpinner.setPrefWidth(60);
        var minSpinner = new Spinner<>(0,59,0); minSpinner.setPrefWidth(60);
        var hBoxHora = new HBox(new Label("Inicio:  Hora "), horaSpinner, new Label(" Minutos "), minSpinner);
        hBoxHora.setAlignment(Pos.CENTER_LEFT);
        var horarioVbox = new VBox(new Label("Horario:"), hBoxHora);
        var intervaloSpinner = new Spinner<>(1,31,0);
        var ocurrenciasSpinner = new Spinner<>(1,1000,0);
        var infinitasCheckBox = new CheckBox();
        var frecuenciaItems = itemsDeFrecuencia(intervaloSpinner,ocurrenciasSpinner,infinitasCheckBox);
        //para los eventos
        var fechaFinField = new DatePicker(); fechaFinField.setEditable(false);
        var horaFinSpinner = new Spinner<>(0,23, 0); horaFinSpinner.setPrefWidth(60);
        var minFinSpinner = new Spinner<>(0,59,0); minFinSpinner.setPrefWidth(60);
        var hBoxHoraFin = new HBox(new Label("Fin:      Hora "), horaFinSpinner, new Label(" Minutos "), minFinSpinner);
        hBoxHoraFin.setAlignment(Pos.CENTER_LEFT);
        if (string.equals("Evento")) {
            horarioVbox.getChildren().add(hBoxHoraFin);
            fechaVBox.getChildren().addAll(new Label("Fecha Fin: "), fechaFinField);
        }

        var frecuenciaChoice = choiceBoxDeFrecuencia(formulario, frecuenciaItems);
        var todoEldiaBox = CheckBoxTodoElDia(formulario);
        formulario.getChildren().addAll(
                new Label("Titulo: "), tituloField,
                new Label("Descripcion:"),descripcionField,
                fechaVBox,
                new Label("Todo el dia: ", todoEldiaBox),
                horarioVbox,
                new Label("Frecuencia: "),frecuenciaChoice);

        List<Control> camposFormulario = List.of(tituloField, descripcionField,fechaField,
                horaSpinner, minSpinner, todoEldiaBox,frecuenciaChoice,
                intervaloSpinner,ocurrenciasSpinner, infinitasCheckBox,
                fechaFinField, horaFinSpinner, minFinSpinner);

        var botones = crearBotonesVentanaTarea(ventana, string, camposFormulario);


        var contenido = new BorderPane();
        contenido.setCenter(formulario);
        contenido.setBottom(botones);

        return contenido;
    }


    /**
     * Es llamada únicamente por la funcion 'contenidoBarraInferior'.
     * Abre una ventana emergente que incluye el contenido indicado en el parametro
     * */
    private void abrirVentanaEmergente(String string){
        var ventana = new Stage();

        BorderPane contenido = contenidoVentanaEmergente(ventana, string);
        ventana.setTitle("Agregar" + string);

        var escena = new Scene(contenido,280,500);
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
            abrirVentanaEmergente("Tarea");
        });
        botonAgregarEvento.setOnAction(actionEvent -> {
            abrirVentanaEmergente("Evento");
        });
        var hbox = new HBox(1,botonAgregarTarea,botonAgregarEvento);
        hbox.setAlignment(Pos.CENTER);

        var barraInferior = new StackPane(hbox);

        return barraInferior;
    }





    private Stage ventanaPrincipal;
    @Override
    public void start(Stage stage) throws Exception {
        fechaActual = LocalDate.now();
        horaActual = LocalTime.now();
        fechaSeleccionada = fechaActual;
        ventanaPrincipal = stage;


        var contenidoCentro = contenidoCentroSemana();
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

        /*
          COSAS QUE FALTAN:
          Al hacer click en un evento mostrar la info

          Mejorar las proporciones, tamaños  (estetica)

          Usar los datos ingresados para crear un evento/tarea

          que se vean los eventos y tareas en las fechas correspondientes ordenados por hora

         */



        var sceneSemana = new Scene(contenedor, 800, 540);
        stage.setScene(sceneSemana);
        stage.show();
    }
}