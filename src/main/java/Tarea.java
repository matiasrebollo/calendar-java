import java.time.LocalDate;
import java.util.Date;

public class Tarea {
    private String titulo;
    private String descripcion;
    private boolean deDiaCompleto;
    private LocalDate vencimiento;
    private boolean completada;

    public Tarea(String titulo, String descripcion, boolean deDiaCompleto, LocalDate vencimiento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = false;
        if (deDiaCompleto) {
            this.deDiaCompleto = true;
        }
        else {
            this.deDiaCompleto = false;
            this.vencimiento = vencimiento;
        }
    }


}
