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

    public Evento crearEvento(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String horarioIni, String horarioFin, boolean todoElDia, Frecuencia frecuencia) {
        LocalTime horarioInicio = LocalTime.parse(horarioIni, DateTimeFormatter.ofPattern("k:m"));
        LocalTime horarioFinal = LocalTime.parse(horarioFin, DateTimeFormatter.ofPattern("k:m"));

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaInicio, horarioInicio);
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaFin, horarioFinal);

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
     * el formato de la hora debe ser "kk:mm", por ej. "0:05"
     * */
    public Tarea crearTarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, String hora, Frecuencia frecuencia) {
        Tarea tarea;
        if (hora.equals("")) {
            tarea = new Tarea(titulo,descripcion, fecha, todoElDia, null,frecuencia);
        }
        else {
            LocalTime horaTime = LocalTime.parse(hora,DateTimeFormatter.ofPattern("kk:mm"));
            tarea = new Tarea(titulo,descripcion, fecha, todoElDia, horaTime,frecuencia);
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

    /**
     * Devuelve una lista de los eventos y Tareas que ocurren en un lapso de n dias
     */
    public ArrayList<ElementoCalendario> obtenerElementosDeUnLapsoDeDias(LocalDate fechaInicio, int cantDias) {
        ArrayList<ElementoCalendario> array = new ArrayList<>();
        LocalDate fechaAux = fechaInicio;
        int tope = Math.max(eventos.size(),tareas.size());
        for (int j = 0; j < cantDias; j++) {
            for (int i = 0; i < tope; i++) {
                if (i < eventos.size()) {
                    Evento evento = eventos.get(i);
                    if (evento.ocurreEnFecha(fechaAux) && !array.contains(evento)) {
                        array.add(evento);
                    }
                }
                if (i < tareas.size()){
                    Tarea tarea = tareas.get(i);
                    if (tarea.ocurreEnFecha(fechaAux) && !array.contains(tarea)) {
                        array.add(tarea);
                    }
                }
            }
            fechaAux = fechaAux.plusDays(1);
            ordenarArrayPorHora(array);
        }
        return array;
    }
    private void ordenarArrayPorHora(ArrayList<ElementoCalendario> array) {
        int tope = array.size();
        for (int i = 0; i < tope - 1; i++) {
            for (int j = 0; j < tope-1 - i; j++) {
                var elemento = array.get(j);
                var siguiente = array.get(j+1);
                if (elemento.getHoraInicio().isAfter(siguiente.getHoraInicio())) {
                    var aux = array.get(j);
                    array.set(j,array.get(j+1));
                    array.set(j+1, aux);
                }
            }
        }
    }
}