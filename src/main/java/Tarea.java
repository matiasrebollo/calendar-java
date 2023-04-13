import java.time.LocalDate;
import java.util.Date;

public class Tarea {
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private LocalDate vencimiento;
    private boolean completada;

    public Tarea(String titulo, String descripcion, boolean todoElDia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
        if (todoElDia) {
            this.todoElDia = true;
        }
        else {
            this.todoElDia = false;
            //this.vencimiento = vencimiento;
        }
    }


}
