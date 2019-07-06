package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;

import javax.mail.MessagingException;

public class Notificar implements AccionAnteAlertaMeteorologica {

    private MedioNotificacion medio;
    AlertaMeteorologica alerta;

    @Override
    public void anteLluvia(Usuario usuario) throws MessagingException {
        alerta = AlertaMeteorologica.LLUVIA;
        medio.send(usuario, alerta);
    }

    @Override
    public void anteGranizo(Usuario usuario) throws MessagingException {
        alerta = AlertaMeteorologica.GRANIZO;
        medio.send(usuario, alerta);
    }
}
