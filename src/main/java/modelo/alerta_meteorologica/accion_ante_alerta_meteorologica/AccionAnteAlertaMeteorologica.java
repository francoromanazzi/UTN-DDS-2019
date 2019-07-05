package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.usuario.Usuario;

public interface AccionAnteAlertaMeteorologica {
	void anteLluvia(Usuario usuario);
	void anteGranizo(Usuario usuario);
}
