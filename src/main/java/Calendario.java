import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendario {
    enum Modificar {TITULO, DESCRIPCION, FECHA, HORARIO, FRECUENCIA, DIACOMPLETO}
    enum Opcion {INICIO, FIN}

    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    private ArrayList<ElementoCalendario> elementosCalendario;

    public Calendario(){
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.elementosCalendario = new ArrayList<>();
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

    public void modificarEvento(Evento evento, Modificar e, Opcion opcion, String nuevoValor,  Frecuencia frecuencia) {
        if(!existeEvento(evento)){
            return;
        }
        switch (e) {
            case TITULO -> {
                evento.setTitulo(nuevoValor);
            }
            case DESCRIPCION -> {
                evento.setDescripcion(nuevoValor);
            }
            case FECHA -> {
                LocalDate nuevaFecha = LocalDate.parse(nuevoValor, DateTimeFormatter.ofPattern("d/M/yyyy"));
                switch (opcion) {
                    case INICIO -> {
                        evento.setFechaInicio(nuevaFecha);
                    }
                    case FIN -> {
                        evento.setFechaFin(nuevaFecha);
                    }
                }
            }
            case HORARIO -> {
                LocalTime nuevaHora = LocalTime.parse(nuevoValor, DateTimeFormatter.ofPattern("kk:mm"));
                switch (opcion) {
                    case INICIO -> {
                        evento.setHoraInicio(nuevaHora);
                    }
                    case FIN -> {
                        evento.setHoraFin(nuevaHora);
                    }
                }
            }
            case FRECUENCIA -> {
                    evento.setFrecuencia(frecuencia);
            }
            case DIACOMPLETO -> {
                evento.marcarTodoElDia();
            }
        }
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

    /**
     * Recibe la tarea a modificar, el elemento que desea modificar
     * y el nuevo valor de ese elemento
     * En el caso de la fecha el formato debe ser "d/M/yyyy"
     * En el caso de la hora el formato debe ser "kk:mm"
     * */
    public void modificarTarea(Tarea tarea, Modificar e, String nuevoValor, Frecuencia frecuencia) {
        if (!existeTarea(tarea)) {
            return;//La tarea no existe
        }
        switch (e) {
            case TITULO -> {
                tarea.setTitulo(nuevoValor);
            }
            case DESCRIPCION -> {
                tarea.setDescripcion(nuevoValor);
            }
            case FECHA -> {
                LocalDate nuevaFecha = LocalDate.parse(nuevoValor, DateTimeFormatter.ofPattern("d/M/yyyy"));
                tarea.setFechaInicio(nuevaFecha);
            }
            case HORARIO -> {
                LocalTime nuevoHorario = LocalTime.parse(nuevoValor, DateTimeFormatter.ofPattern("kk:mm"));
                tarea.setHoraInicio(nuevoHorario);
            }
            case FRECUENCIA -> {
                if (frecuencia != null){
                    tarea.setFrecuencia(frecuencia);
                }
            }
            case DIACOMPLETO -> {
                tarea.marcarTodoElDia();
            }
        }
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
}
