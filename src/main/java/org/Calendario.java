package org;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendario implements Serializable {
    @JsonProperty("Eventos")
    private ArrayList<Evento> eventos;

    @JsonProperty("Tareas")
    private ArrayList<Tarea> tareas;

    public Calendario(){
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }

    public Evento crearEvento(String titulo, String descripcion, String fechaInicio, String fechaFin, String horarioIni, String horarioFin, boolean todoElDia, Frecuencia frecuencia) {
        LocalDate fechaIni = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate fechaFinal = LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalTime horarioInicio = LocalTime.parse(horarioIni, DateTimeFormatter.ofPattern("kk:mm"));
        LocalTime horarioFinal = LocalTime.parse(horarioFin, DateTimeFormatter.ofPattern("kk:mm"));

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaIni, horarioInicio);
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaFinal, horarioFinal);

        var evento = new Evento(titulo, descripcion, fechaHoraInicio, fechaHoraFin, todoElDia, frecuencia);
        this.eventos.add(evento);

        return evento;
    }

    public void eliminarEvento(Evento evento) {
        if (existeEvento(evento)){
            this.eventos.remove(evento);
        }
    }

    public boolean existeEvento(Evento evento){
        return this.eventos.contains(evento);
    }

    /**
     * Devuelve la tarea creada o null en caso de que no se cree
     * el formato de fecha debe ser "d/M/yyyy", por ej. "/10/0"
     * el formato de la hora debe ser "kk:mm", por ej. "0:05"
     * */
    public Tarea crearTarea(String titulo, String descripcion, String fecha, boolean todoElDia, String hora, Frecuencia frecuencia) {
        LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));

        Tarea tarea;
        if (hora.equals("")) {
            tarea = new Tarea(titulo,descripcion, fechaDate, todoElDia, null,frecuencia);
        }
        else {
            LocalTime horaTime = LocalTime.parse(hora,DateTimeFormatter.ofPattern("kk:mm"));
            tarea = new Tarea(titulo,descripcion, fechaDate, todoElDia, horaTime,frecuencia);
        }

        this.tareas.add(tarea);
        return tarea;
    }

    public boolean existeTarea(Tarea tarea) {
        return this.tareas.contains(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        if (existeTarea(tarea)) {
            this.tareas.remove(tarea);
        }
    }

    public int cantidadEventos() {
        return eventos.size();
    }
    public int cantidadTareas() {
        return tareas.size();
    }
    public Evento obtenerEvento(int n) {
        return eventos.get(n);
    }
    public Tarea obtenerTarea(int n) {
        return tareas.get(n);
    }



    public void serializar(ObjectMapper objectMapper, String nombreArchivo) throws IOException{
        objectMapper.registerModule(new JavaTimeModule());//para poder serializar los LocalDateTime
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);  // Habilitar formato legible (que no aparezca todo en una linea)

        String leido = objectMapper.writeValueAsString(this);//copia la información en un string

        // Guardar el string en un archivo de texto formato json
        BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo));
        writer.write(leido);
        writer.close();
    }

    public static Calendario deserializar(ObjectMapper objectMapper, String nombreArchivo) throws IOException{
        objectMapper.registerModule(new JavaTimeModule());//para poder escribir LocalDateTime

        File archivo = new File(nombreArchivo);
        return objectMapper.readValue(archivo, Calendario.class);
    }

    public ArrayList<ElementoCalendario> mostrarEventosYTareasMes(LocalDate fecha){
        LocalDate primerDiaMes = fecha.withDayOfMonth(1);
        for (int i = 0; i < fecha.lengthOfMonth(); i++){
            for (int j = 0; j < cantidadEventos() + cantidadTareas(); j++){

            }
        }
    }
}