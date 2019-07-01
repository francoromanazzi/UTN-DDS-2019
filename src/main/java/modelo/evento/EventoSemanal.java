package modelo.evento;

public class EventoSemanal implements FrecuenciaEvento{

    @Override
    public long getPerido() {
        return 60L * 60L * 24L * 7L;
    }
}
