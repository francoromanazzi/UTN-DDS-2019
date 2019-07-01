package modelo.evento;

public class EventoMensual implements FrecuenciaEvento {
    @Override
    public long getPerido() {
        return 60L * 60L * 24L * 30L;
    }
}
