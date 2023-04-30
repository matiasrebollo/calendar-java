import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Calendario {
    enum Modificar {TITULO, DESCRIPCION, FECHA, HORARIO, FRECUENCIA, DIACOMPLETO}
    enum Opcion {INICIO, FIN}

    private ArrayList<Evento2> eventos;
    private ArrayList<Tarea2> tareas;
    private ArrayList<ElementoCalendario> elementosCalendario;

    public Calendario(){
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.elementosCalendario = new ArrayList<>();
    }

    public Evento2 crearEvento(String titulo, String descripcion, String fechaInicio, String fechaFin, String horarioIni, String horarioFin, boolean todoElDia, Frecuencia frecuencia) {
        LocalDate fechaIni = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate fechaFinal = LocalDate.parse(fechaFin, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalTime horarioInicio = LocalTime.parse(horarioIni, DateTimeFormatter.ofPattern("kk:mm"));
        LocalTime horarioFinal = LocalTime.parse(horarioFin, DateTimeFormatter.ofPattern("kk:mm"));

        LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaIni, horarioInicio);
        LocalDateTime fechaHoraFin = LocalDateTime.of(fechaFinal, horarioFinal);

        var evento = new Evento2(titulo, descripcion, fechaHoraInicio, fechaHoraFin, todoElDia, frecuencia);
        this.eventos.add(evento);

        return evento;
    }
    public Alarma agregarAlarmaEvento(Evento2 evento, String fecha, String horario, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        Alarma alarma;
        if (fecha.equals("") || horario.equals("")){
            alarma = evento.agregarAlarma(null, intervalo, unidad, efecto);
        } else {
            LocalDate fechaAlarma = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalTime horarioAlarma = LocalTime.parse(horario, DateTimeFormatter.ofPattern("kk:mm"));
            LocalDateTime fechaHoraAlarma = LocalDateTime.of(fechaAlarma, horarioAlarma);
            alarma = evento.agregarAlarma(fechaHoraAlarma, intervalo, unidad, efecto);
        }
        return alarma;
    }
    public void destruirAlarmaEvento(Evento2 evento, Alarma alarma){
        evento.destruirAlarma(alarma);
    }

    /*public void modificarEvento(Evento evento, Modificar e, String nuevoValor1, String nuevoValor2, Frecuencia frecuencia) {
        if(!existeEvento(evento)){
            return;
        }
        switch (e) {
            case TITULO -> {
                evento.setTitulo(nuevoValor1);
            }
            case DESCRIPCION -> {
                evento.setDescripcion(nuevoValor1);
            }
            case FECHA -> {
                if (nuevoValor1.equals("")){
                    LocalDate nuevaFechaFin = LocalDate.parse(nuevoValor2, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    evento.modificarFecha(null, nuevaFechaFin);
                } else if (nuevoValor2.equals("")){
                    LocalDate nuevaFechaInicio = LocalDate.parse(nuevoValor1, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    evento.modificarFecha(nuevaFechaInicio, null);
                } else {
                    LocalDate nuevaFechaInicio = LocalDate.parse(nuevoValor1, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    LocalDate nuevaFechaFin = LocalDate.parse(nuevoValor2, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    evento.modificarFecha(nuevaFechaInicio, nuevaFechaFin);
                }
            }
            case HORARIO -> {
                if (nuevoValor1.equals("")){
                    LocalTime nuevoHorarioFin = LocalTime.parse(nuevoValor2, DateTimeFormatter.ofPattern("kk:mm"));
                    evento.modificarHorario(null, nuevoHorarioFin);
                } else if (nuevoValor2.equals("")){
                    LocalTime nuevoHorarioInicio = LocalTime.parse(nuevoValor1, DateTimeFormatter.ofPattern("kk:mm"));
                    evento.modificarHorario(nuevoHorarioInicio, null);
                } else {
                    LocalTime nuevoHorarioInicio = LocalTime.parse(nuevoValor1, DateTimeFormatter.ofPattern("kk:mm"));
                    LocalTime nuevoHorarioFin = LocalTime.parse(nuevoValor2, DateTimeFormatter.ofPattern("kk:mm"));
                    evento.modificarHorario(nuevoHorarioInicio, nuevoHorarioFin);
                }
            }
            case FRECUENCIA -> {
                if (frecuencia != null){
                    evento.setFrecuencia(frecuencia);
                }
            }
            case DIACOMPLETO -> {
                evento.marcarTodoElDia();
            }
        }
    }*/
    public void modificarEvento(Evento2 evento, Modificar e, Opcion opcion, String nuevoValor,  Frecuencia frecuencia) {
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

    public void eliminarEvento(Evento2 evento) {
        if (existeEvento(evento)){
            this.eventos.remove(evento);
        }
    }

    public boolean existeEvento(Evento2 evento){
        return this.eventos.contains(evento);
    }



    /**
     * Devuelve la tarea creada o null en caso de que no se cree
     * el formato de fecha debe ser "d/M/yyyy", por ej. "2/10/2022"
     * el formato de la hora debe ser "kk:mm", por ej. "20:05"
     * */
    public Tarea2 crearTarea(String titulo, String descripcion, String fecha, boolean todoElDia, String hora, Frecuencia frecuencia) {
        LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));

        Tarea2 tarea;
        if (hora.equals("")) {
            tarea = new Tarea2(titulo,descripcion, fechaDate, todoElDia, null,frecuencia);
        }
        else {
            LocalTime horaTime = LocalTime.parse(hora,DateTimeFormatter.ofPattern("kk:mm"));
            tarea = new Tarea2(titulo,descripcion, fechaDate, todoElDia, horaTime,frecuencia);
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
    public void modificarTarea(Tarea2 tarea, Modificar e, String nuevoValor, Frecuencia frecuencia) {
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

    public Alarma agregarAlarmaTarea(Tarea2 tarea, String fecha, String horario, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        Alarma alarma;
        if (fecha.equals("") || horario.equals("")){
            alarma = tarea.agregarAlarma(null, intervalo, unidad, efecto);
        } else {
            LocalDate fechaAlarma = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));
            LocalTime horarioAlarma = LocalTime.parse(horario, DateTimeFormatter.ofPattern("kk:mm"));
            LocalDateTime fechaHoraAlarma = LocalDateTime.of(fechaAlarma, horarioAlarma);
            alarma = tarea.agregarAlarma(fechaHoraAlarma, intervalo, unidad, efecto);
        }
        return alarma;
    }

    public boolean existeTarea(Tarea2 tarea) {
        return this.tareas.contains(tarea);
    }

    public void eliminarTarea(Tarea2 tarea) {
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
