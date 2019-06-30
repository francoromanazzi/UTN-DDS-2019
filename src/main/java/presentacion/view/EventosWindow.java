package presentacion.view;

import java.awt.Color;

import modelo.evento.Evento;
import modelo.usuario.Usuario;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import presentacion.view_model.EventoObservable;
import presentacion.view_model.EventosViewModel;

// IMPORTANTE: correr con -Djava.system.class.loader=com.uqbar.apo.APOClassLoader
public class EventosWindow extends MainWindow<EventosViewModel>{
	private EventosWindow() {
		super(new EventosViewModel(new Usuario("Pepe", "pepe@gmail.com")));
	}

	@Override
	public void createContents(Panel mainPanel) {
		init(mainPanel);

		Table<EventoObservable> tabla = new Table<>(mainPanel, EventoObservable.class);
		tabla.bindItemsToProperty("eventos");

		new Column<>(tabla)
				.setTitle("Fecha")
				.setFixedSize(200)
				.bindContentsToProperty("fechaInicio");

		new Column<>(tabla)
				.setTitle("Título")
				.setFixedSize(200)
				.bindContentsToProperty("titulo");

		new Column<>(tabla)
				.setTitle("Sugerencias")
				.setFixedSize(150)
				.bindContentsToProperty("sugerenciasFueronGeneradas");
	}

	public static void main(String[] args) {
		new EventosWindow().startApplication();
	}

	private void init(Panel mainPanel) {
		this.setTitle("Eventos");
		this.setMinHeight(600);
		this.setMinWidth(800);
		mainPanel.setLayout(new VerticalLayout());
	}
}
