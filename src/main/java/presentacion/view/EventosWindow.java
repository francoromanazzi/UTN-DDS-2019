package presentacion.view;

import java.awt.Color;

import modelo.usuario.Usuario;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import presentacion.view_model.EventoObservable;
import presentacion.view_model.EventosViewModel;

// IMPORTANTE: correr con -Djava.system.class.loader=com.uqbar.apo.APOClassLoader
public class EventosWindow extends MainWindow<EventosViewModel>{
	private static final long serialVersionUID = -1142663776268286620L;

	private EventosWindow() {
		super(new EventosViewModel(new Usuario("Pepe", "pepe@gmail.com","")));
	}

	@Override
	public void createContents(Panel mainPanel) {
		init(mainPanel);

		Table<EventoObservable> tabla = new Table<>(mainPanel, EventoObservable.class).setNumberVisibleRows(15);
		tabla.bindItemsToProperty("eventos");

		new Column<>(tabla)
				.setTitle("Fecha")
				.setFixedSize(200)
				.bindContentsToProperty("evento.fechaInicio");

		new Column<>(tabla)
				.setTitle("Título")
				.setFixedSize(400)
				.bindContentsToProperty("evento.titulo");

		Column<EventoObservable> columnaSugerenciaGenerada = new Column<>(tabla)
				.setTitle("Sugerencias")
				.setFixedSize(100);

		columnaSugerenciaGenerada
				.bindContentsToProperty("sugerenciasFueronGeneradas")
				.setTransformer(bool -> (boolean) bool ? "SI" : "NO");

		columnaSugerenciaGenerada
				.bindBackground("sugerenciasFueronGeneradas")
				.setTransformer(bool -> (boolean) bool ? Color.GREEN : Color.RED);
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
