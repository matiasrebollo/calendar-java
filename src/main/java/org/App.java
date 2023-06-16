package org;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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

import java.io.File;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    enum Modalidades {DIA, SEMANA, MES};
    private static final DateTimeFormatter FORMATTER_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATTER_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String NOMBRE_ARCHIVO = "datosCalendario.json";

    private Calendario calendario;
    private ElementoCalendario elementoSeleccionado;
    private ArrayList<Alarma> alarmasDeHoy;

    private LocalDate fechaSeleccionada;
    private LocalDate fechaActual;
    private LocalTime horaActual;

    private Modalidades modalidad = Modalidades.DIA;
    private DateTimeFormatter formatter = FORMATTER_FECHA;
    private Label labelFecha;
    private BorderPane contenedor;
    private Stage ventanaPrincipal;
    private Stage ventana2;


    private void irAlLunes(){
        while (!fechaSeleccionada.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            fechaSeleccionada = fechaSeleccionada.minusDays(1);
        }
    }
    private void irAlPrimerDiaDelMes(){
        while (fechaSeleccionada.getDayOfMonth() != 1) {
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
     * Crea un nuevo Stage en la variable ventana2 con el titulo recibido,
     * y una nueva Scene que tendrá el contenido y el tamaño recibidos
     * Y luego muestra la ventana emergente en la interfaz
     */
    private void abrirVentanaEmergente(int ancho, int alto, Node contenido, String tituloVentana) {
        ventana2 = new Stage();
        ventana2.setTitle(tituloVentana);

        BorderPane contenido2 = (BorderPane) contenido;

        var escena = new Scene(contenido2,ancho,alto);
        ventana2.initModality(Modality.APPLICATION_MODAL);
        ventana2.initOwner(ventanaPrincipal);
        ventana2.setResizable(false);
        ventana2.setScene(escena);
        ventana2.showAndWait();
        actualizarContenidoCentro();
    }

    /**
     * Revisa en el array de alarmasDeHoy el horario de cada una,
     * y en caso de coincidir con la hora actual lanza la notificación
     */
    public void chequearAlarmas() {
        var hora = LocalTime.of(horaActual.getHour(), horaActual.getMinute());
        for (var a : alarmasDeHoy){
            if (a.getFechaHoraAlarma().toLocalTime().equals(hora)){
                Platform.runLater(()-> mostrarNotifificacion(a));
            }
        }
    }

    /***
     * Lanza la notificacion de la alarma en la interfaz
     */
    private void mostrarNotifificacion(Alarma alarma) {
        var label =new Label(" Faltan "+ alarma.getIntervalo() +" "+ alarma.getUnidad().toString());
        var label2 = new Label(" para " + alarma.getTituloEvento());
        var vBox = new VBox(label,label2);
        var contenido = new BorderPane();
        contenido.setCenter(vBox);
        abrirVentanaEmergente(200,50, contenido, "Notificacion");
    }

    /**
     * busca en los eventos y tareas que ocurren en la fecha recibida sus alarmas,
     * y devuelve un array con las alarmas que aun no suenan
     */
    public ArrayList<Alarma> obtenerAlarmasDeLaFecha(LocalDate fecha) {
        ArrayList<Alarma> array = new ArrayList<>();
        var elementos = calendario.obtenerElementosDeLaFecha(fecha);
        for(var e : elementos) {
            if (e.getHoraInicio().isAfter(horaActual)) {
                var alarmas = e.getAlarmas();
                if(!alarmas.isEmpty()) {
                    for (Alarma alarma : alarmas) {
                        array.add(alarma);
                    }
                }
            }
        }
        return array;
    }


    //Contenido centro...

    private VBox contenidoInformacionElemento(ElementoCalendario elemento, LocalDate fecha){
        var titulo = new Label("Titulo:  " + elemento.getTitulo());
        var descripcion = new VBox(new Label("Descripcion:   "), new Label(elemento.getDescripcion()));
        var fechaYHoraInicio = new VBox();
        var fechaYHoraFin = new VBox();
        var duracion = new VBox();
        var completada = new VBox();
        if (elemento.getTypename().equals("Evento")){
            Evento evento = (Evento) elemento;
            if (evento.esTodoElDia()){
                fechaYHoraInicio.getChildren().addAll(new Label("Fecha inicio:   "),
                        new Label(evento.getFechaInicioRepeticion(fecha).format(FORMATTER_FECHA))
                );
                fechaYHoraFin.getChildren().addAll(new Label("Fecha fin:   "),
                        new Label(evento.getFechaFinRepeticion(fecha).format(FORMATTER_FECHA))
                );
                duracion.getChildren().addAll(new Label("Duracion:"), new Label("Todo el día"));
            } else {
                fechaYHoraInicio.getChildren().addAll(
                        new Label("Fecha y hora inicio:   "),
                        new Label(evento.getFechaInicioRepeticion(fecha).format(FORMATTER_FECHA)),
                        new Label(evento.getHoraInicio().toString())
                );
                fechaYHoraFin.getChildren().addAll(
                        new Label("Fecha y hora fin:   "),
                        new Label(evento.getFechaFinRepeticion(fecha).format(FORMATTER_FECHA)),
                        new Label(evento.getHoraFin().toString()));
            }
        } else {
            Tarea tarea = (Tarea) elemento;
            if (tarea.esTodoElDia()){
                fechaYHoraInicio.getChildren().addAll(
                        new Label("Fecha inicio:   "),
                        new Label(tarea.getFechaInicioRepeticion(fecha).format(FORMATTER_FECHA))
                );
                duracion.getChildren().addAll(new Label("Duracion:"), new Label("Todo el día"));
            } else {
                fechaYHoraInicio.getChildren().addAll(
                        new Label("Fecha y hora inicio:   "),
                        new Label(tarea.getFechaInicioRepeticion(fecha).format(FORMATTER_FECHA)),
                        new Label(tarea.getHoraInicio().toString())
                );
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
            enumeracion.getChildren().add(new Label(alarmas.get(i).getFechaHoraAlarma().format(DateTimeFormatter.ofPattern("d/M/yyyy kk:mm"))));
        }
        var alarma = new HBox(new Label("Alarmas:   "), enumeracion);

        VBox formulario = new VBox(20);
        formulario.getChildren().addAll(titulo, descripcion, fechaYHoraInicio, fechaYHoraFin, duracion, completada, frecuencia, alarma);

        return formulario;
    }

    /**
     * Crea una alarma con los datos seleccionados.
     */
    private Button botonAceptarAlarma(Spinner intervaloSpinner, ChoiceBox unidades) {
        var botonAceptar = new Button("Aceptar");
        botonAceptar.setOnAction(actionEvent -> {
            int intervalo = (int) intervaloSpinner.getValue();
            Alarma.UnidadesDeTiempo unidad = null;
            switch (unidades.getValue().toString()){
                case "minutos" -> unidad = Alarma.UnidadesDeTiempo.MINUTOS;
                case "horas" -> unidad = Alarma.UnidadesDeTiempo.HORAS;
                case "dias" -> unidad = Alarma.UnidadesDeTiempo.DIAS;
                case "semanas" -> unidad = Alarma.UnidadesDeTiempo.SEMANAS;
            }
            var a = elementoSeleccionado.agregarAlarma(null, intervalo, unidad, Alarma.EfectosAlarma.NOTIFICACION);
            ventana2.close();
            if (a.getFechaHoraAlarma().toLocalDate().equals(fechaActual))
                alarmasDeHoy.add(a);
            System.out.println("Se agregó la alarma. hoy hay: " + alarmasDeHoy.size());
            try {
                calendario.serializar(new ObjectMapper(), NOMBRE_ARCHIVO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return botonAceptar;
    }

    /**
     * Muestra un cuadro para poder crear una alarma.
     */
    private BorderPane contenidoAlarma() {
        var intervaloCasilla = new Spinner<>(1,60,0);
        intervaloCasilla.setPrefWidth(70);

        var unidadCasilla = new ChoiceBox<>();
        unidadCasilla.getItems().addAll("minutos","horas", "dias", "semanas");
        unidadCasilla.setValue("minutos");

        var alarmaCasilla = new HBox(5,new Label("Agregar alarma"), intervaloCasilla, unidadCasilla, new Label("antes."));
        alarmaCasilla.setAlignment(Pos.CENTER);

        var mensaje = new HBox(new Label("La alarma será una notificación por defecto."));
        mensaje.setAlignment(Pos.CENTER);

        var contenidoAlarma = new VBox(10,alarmaCasilla, mensaje);
        contenidoAlarma.setAlignment(Pos.CENTER);

        var botonAceptar = botonAceptarAlarma(intervaloCasilla,unidadCasilla);
        var contenedorAceptar = new HBox(botonAceptar);
        contenedorAceptar.setAlignment(Pos.CENTER);
        var contenido = new BorderPane();
        contenido.setCenter(contenidoAlarma);
        contenido.setBottom(contenedorAceptar);

        return contenido;
    }

    /**
     * Muestra la informacion del elemento recibido, permitiendo agregar una alarma o
     * eliminar el elemento.
     */
    private void mostrarInformacionElemento(ElementoCalendario elemento, LocalDate fecha){
        elementoSeleccionado = elemento;
        var formulario = contenidoInformacionElemento(elemento, fecha);
        var botonAgregarAlarma = new Button("Agregar alarma");
        botonAgregarAlarma.setOnAction(e->{
            var aux = ventana2;
            abrirVentanaEmergente(335, 100, contenidoAlarma(), "Agregar Alarma");
            aux.close();
        });
        formulario.getChildren().add(botonAgregarAlarma);

        var botonEliminar = new Button("Eliminar " + elemento.getTypename());
        botonEliminar.setStyle("-fx-background-color: #943333; -fx-text-fill: white");
        botonEliminar.setOnAction(e->{
            calendario.eliminarElemento(elemento);
            ventana2.close();
            try {
                calendario.serializar(new ObjectMapper(), NOMBRE_ARCHIVO);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        var hboxBotonEliminar = new HBox(botonEliminar);
        hboxBotonEliminar.setAlignment(Pos.CENTER);

        var contenido = new BorderPane();
        contenido.setCenter(formulario);
        contenido.setBottom(hboxBotonEliminar);
        abrirVentanaEmergente(280,450, contenido, "Informacion "+ elemento.getTypename());
    }

    /**
     * Crea la casilla de cada dia del mes.
     */
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
            if (numero.equals("00")){
                dia.setStyle("-fx-background-color: #484848;");
            }
            if (!aux && auxiliares[0] < cantidadDiasMes){
                mostrarEventosEnLaInterfaz(dia, fechaSeleccionada.withDayOfMonth(auxiliares[0] - 1));
            }
            HBox.setHgrow(dia, Priority.ALWAYS);
            VBox.setVgrow(dia, Priority.ALWAYS);
            fila.getChildren().add(dia);
        }
    }


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

        if (auxiliares[0] <= diasMes){
            HBox fila6 = new HBox();
            crearDias(auxiliares, fila6, posicion, diasMes, borde);
            VBox.setVgrow(fila6, Priority.ALWAYS);
            centroMes.getChildren().add(fila6);
        }
        return centroMes;
    }

    /***
     * Vuelve a cargar el contenido centro de la interfaz
     */
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


    /**
     * Recibe un VBox y una fecha, y
     * agrega todos los eventos y tareas que ocurren en ese dia al VBox recibido
     * */
    private void mostrarEventosEnLaInterfaz(VBox columnaDia, LocalDate fecha){
        var elementosDelDia = calendario.obtenerElementosDeLaFecha(fecha);
        for (ElementoCalendario elemento : elementosDelDia) {
            var labelTitulo = new Label (elemento.getTitulo());
            var hBoxTitulo = new HBox(labelTitulo);
            hBoxTitulo.setAlignment(Pos.CENTER_LEFT);

            Label labelHora = null;
            if (elemento.getTypename().equals("Evento")){
                Evento e = (Evento) elemento;
                labelHora = new Label(e.getHoraInicio().toString() + " : " + e.getHoraFin().toString());
                labelTitulo.setStyle("-fx-border-color: gray");
            }
            else if (elemento.getTypename().equals("Tarea")){
                Tarea t = (Tarea) elemento;
                var completadaCheck = new CheckBox();
                completadaCheck.setSelected(t.estaCompletada());
                completadaCheck.setOnAction(e-> t.marcarTareaCompletada());
                hBoxTitulo.getChildren().add(completadaCheck);
                labelHora = new Label(elemento.getHoraInicio().toString() + " ");
                labelTitulo.setStyle("-fx-border-color: black");
            }
            if (elemento.esTodoElDia()) {
                labelHora = new Label("Todo el dia");
            }
            var hBox= new HBox(5, labelHora,hBoxTitulo); hBox.setAlignment(Pos.CENTER_LEFT);

            switch (modalidad) {
                case DIA -> hBox.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black;");
                case MES -> hBox.getChildren().remove(labelHora);
            }
            labelTitulo.setOnMouseClicked(e-> mostrarInformacionElemento(elemento, fecha));
            columnaDia.getChildren().add(hBox);
        }
    }

    private Node contenidoCentroSemana() {
        int n = fechaSeleccionada.getDayOfMonth();
        BorderStroke borde = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
        var contenido = new HBox();
        contenido.setAlignment(Pos.TOP_CENTER);

        int j = 1;
        for (int i = 0; i < 7; i++) {
            var col = new VBox();
            var stackPane = new StackPane();
            stackPane.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black;");
            var label = new Label();
            label.setTextAlignment(TextAlignment.CENTER);
            String dia = nombreDiaDeLaSemana(i);

            if (n+i > fechaSeleccionada.lengthOfMonth()) {
                label.setText(dia+j);
                j++;
            }
            else{
                label.setText(dia+(n+i));
            }
            stackPane.getChildren().add(label);
            col.getChildren().add(stackPane);

            col.setBorder(new Border(borde));
            mostrarEventosEnLaInterfaz(col,fechaSeleccionada.plusDays(i));
            HBox.setHgrow(col, Priority.ALWAYS);

            contenido.getChildren().add(col);
        }
        return contenido;
    }

    private Node contenidoCentroDia(){
        var columna = new VBox(7);
        mostrarEventosEnLaInterfaz(columna, fechaSeleccionada);
        HBox.setHgrow(columna, Priority.ALWAYS);
        return columna;
    }


    //Barra superior

    /**
     * Crea una caja de opciones (Dia, Semana, Mes),
     * y se configuran las aciones a realizar en caso de qué modalidad se seleccione
     */
    private Node choiceBoxModalidad() {
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Dia", "Semana", "Mes");
        choiceBox.setValue("Dia");

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
        labelFechaHora.setTextAlignment(TextAlignment.CENTER);
        labelFechaHora.setFont(Font.font(15));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            fechaActual = LocalDate.now();
            horaActual = LocalTime.now();
            labelFechaHora.setText(horaActual.format(FORMATTER_HORA) +"\n"+ fechaActual.format(FORMATTER_FECHA));
            if (horaActual.getSecond() == 0)
                chequearAlarmas();
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



    //Barra inferior...
    /***
     * Crea un VBox que contendrá en cada fila los campos recibidos
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
        choiceBox.getItems().addAll("Ninguna","Diaria");
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
    private HBox crearBotonesVentanaElemento(String elemento, List<Control> camposFormulario) {
        var botonAceptar = new Button("Aceptar");
        botonAceptar.setOnAction(e -> {
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
                var horaInicio = LocalTime.parse(horaString, DateTimeFormatter.ofPattern("k:m"));
                var horaFin = LocalTime.parse(horaFinString, DateTimeFormatter.ofPattern("k:m"));

                LocalDate fechaFin = fechaFinPicker.getValue();
                if (fechaFin == null || fechaFin.isBefore(fecha))
                    return;
                if (horaInicio.isAfter(horaFin) && fecha.equals(fechaFin))
                    return;
                Evento ev = calendario.crearEvento(titulo, descripcion, fecha, fechaFin, horaString, horaFinString, todoElDia, frecuencia);
                if (ev != null)
                    System.out.println("Se creo el Evento");
            }
            else {
                calendario.crearTarea(titulo,descripcion,fecha,todoElDia,horaString,frecuencia);
                System.out.println("Se creó la tarea");
            }
            try {
                calendario.serializar(new ObjectMapper(), NOMBRE_ARCHIVO);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            actualizarContenidoCentro();
            ventana2.close();
        });
        var botonCancelar = new Button("Cancelar");
        botonCancelar.setOnAction(actionEvent -> {
            ventana2.close();
        });

        var botones = new HBox(5, botonAceptar, botonCancelar);
        botones.setAlignment(Pos.CENTER);
        return botones;
    }


    /***
     * Crea un formulario con todos los campos necesarios para
     * crear un Evento o Tarea.
     */
    private BorderPane contenidoVentanaEmergente(String string) {
        VBox formulario = new VBox(5);
        var tituloField = new TextField();
        var descripcionField = new TextArea(); descripcionField.setPrefHeight(90);
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
                new Label("Frecuencia: "),frecuenciaChoice
        );

        List<Control> camposFormulario = List.of(tituloField, descripcionField,fechaField,
                horaSpinner, minSpinner, todoEldiaBox,frecuenciaChoice,
                intervaloSpinner,ocurrenciasSpinner, infinitasCheckBox,
                fechaFinField, horaFinSpinner, minFinSpinner);

        var botones = crearBotonesVentanaElemento(string, camposFormulario);


        var contenido = new BorderPane();
        contenido.setCenter(formulario);
        contenido.setBottom(botones);

        return contenido;
    }

    /**
     * Contiene un boton para agregar Tareas al Calendario y otro para agregar Eventos
     * */
    public Node contenidoBarraInferior() {
        var botonAgregarEvento = new Button("agregar Evento");
        var botonAgregarTarea = new Button("agregar Tarea");
        botonAgregarTarea.setOnAction(actionEvent -> {
            abrirVentanaEmergente(280,450,contenidoVentanaEmergente("Tarea"),"Agregar Tarea");
        });
        botonAgregarEvento.setOnAction(actionEvent -> {
            abrirVentanaEmergente(280, 500, contenidoVentanaEmergente("Evento"), "Agregar Evento");
        });
        var hbox = new HBox(1,botonAgregarTarea,botonAgregarEvento);
        hbox.setAlignment(Pos.CENTER);

        return new StackPane(hbox);
    }


    @Override
    public void start(Stage stage) throws Exception {
        fechaActual = LocalDate.now();
        horaActual = LocalTime.now();
        fechaSeleccionada = fechaActual;
        ventanaPrincipal = stage;

        File archivo = new File(NOMBRE_ARCHIVO);
        if (archivo.exists()){
            calendario = Calendario.deserializar(new ObjectMapper(),NOMBRE_ARCHIVO);
        } else {
            calendario = new Calendario();
        }
        alarmasDeHoy = obtenerAlarmasDeLaFecha(fechaActual);

        var contenidoCentro = contenidoCentroDia();
        var barraSuperior = contenidoBarraSuperior();
        barraSuperior.setStyle("-fx-background-color: #34a9c9;");
        var barraIzquierda = new StackPane(new Label("  "));
        var barraDerecha = new StackPane(new Label("  "));
        var barraInferior = contenidoBarraInferior();

        contenedor = new BorderPane();
        contenedor.setTop(barraSuperior);
        contenedor.setCenter(contenidoCentro);
        contenedor.setBottom(barraInferior);
        contenedor.setLeft(barraIzquierda);
        contenedor.setRight(barraDerecha);

        var sceneSemana = new Scene(contenedor, 800, 540);
        stage.setScene(sceneSemana);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}