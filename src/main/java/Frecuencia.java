import javax.swing.plaf.PanelUI;

public interface Frecuencia {
    enum TipoFrecuencia {CERO, DIARIA, SEMANAL, MENSUAL, ANUAL};
    enum FrecuenciaMensual {MISMONUMERO, MISMODIA};
    //ej. todos los meses, el dia 7 o todos los meses, el 3er lunes
}
