package presentacion.view;

import java.awt.Color;
import modelo.usuario.Usuario;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Action;

import presentacion.view_model.ButtonClickAction;
import presentacion.view_model.EventoObservable;
import presentacion.view_model.EventosViewModel;

// IMPORTANTE: correr con -Djava.system.class.loader=org.uqbar.apo.APOClassLoader
public class EventosWindow extends MainWindow<EventosViewModel>{
	private static final long serialVersionUID = -1142663776268286620L;

	private EventosWindow() {
		super(new EventosViewModel(new Usuario("Pepe", "pepe@gmail.com","123456789")));
	}

	@Override
	public void createContents(Panel mainPanel) {
		init(mainPanel);
		Panel panelLabels = new Panel(mainPanel).setLayout(new HorizontalLayout());
		Panel panelFechaInicio = new Panel(panelLabels).setLayout(new VerticalLayout());
		Panel panelSeparador = new Panel(panelLabels).setLayout(new VerticalLayout());
		Panel panelFechaFin = new Panel(panelLabels).setLayout(new VerticalLayout());
		
		//Seleccionar fechas
		new Label(panelFechaInicio).setText("Initial date\n ");
		
		new Label(panelFechaInicio).setText("Day");
		new NumericField(panelFechaInicio, false).bindValueToProperty("diaInicio");
		new Label(panelFechaInicio).setText("Month");
		new NumericField(panelFechaInicio, false).bindValueToProperty("mesInicio");
		new Label(panelFechaInicio).setText("Year");
		new NumericField(panelFechaInicio, false).bindValueToProperty("anioInicio");
		
		//Bien croto
		new Label(panelSeparador).setText("                                                                                                                 ");
		
		new Label(panelFechaFin).setText("Final date\n ");
		
		new Label(panelFechaFin).setText("Day");
		new NumericField(panelFechaFin, false).bindValueToProperty("diaFin");
		new Label(panelFechaFin).setText("Month");
		new NumericField(panelFechaFin, false).bindValueToProperty("mesFin");
		new Label(panelFechaFin).setText("Year");
		new NumericField(panelFechaFin, false).bindValueToProperty("anioFin");
		
		new Button(mainPanel)
	    	.setCaption("Filter events")
	    	.onClick(new ButtonClickAction(this.getModelObject()))
	    	.setAsDefault()
	    	.disableOnError();
		
		//Tabla
		Table<EventoObservable> tabla = new Table<>(mainPanel, EventoObservable.class).setNumberVisibleRows(5);
		tabla.bindItemsToProperty("eventosFiltrados");

		new Column<>(tabla)
				.setTitle("Date")
				.setFixedSize(200)
				.bindContentsToProperty("evento.fechaInicio");

		new Column<>(tabla)
				.setTitle("Title")
				.setFixedSize(300)
				.bindContentsToProperty("evento.titulo");

		Column<EventoObservable> columnaSugerenciaGenerada = new Column<>(tabla)
				.setTitle("Recommendation?")
				.setFixedSize(200);

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
