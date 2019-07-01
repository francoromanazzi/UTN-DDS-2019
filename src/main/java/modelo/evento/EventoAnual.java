package modelo.evento;

public class EventoAnual implements FrecuenciaEvento{
    @Override
    public long getPerido() {
        return 60L * 60L * 24L * 365L;
    }
}