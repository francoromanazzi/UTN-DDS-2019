package modelo.evento;

public class EventoDiario implements FrecuenciaEvento {
    @Override
    public long getPerido() {
        return 60L * 60L * 24L;
    }
}
