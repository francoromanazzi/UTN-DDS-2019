package arena.views;

import java.awt.Color;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;
import arena.viewModels.EventoViewModel;

public class EventsWindow extends MainWindow<EventoViewModel>{
	public EventsWindow() {
		super(new EventoViewModel());
	}

	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Eventos");
		mainPanel.setLayout(new VerticalLayout());

		new Label(mainPanel).setText("Ingrese su nombre");

		new TextBox(mainPanel)
	    	.setWidth(250)
	    	.bindValueToProperty("nombre");
		
		new Label(mainPanel).setText("Te saludo:");
		
		new Label(mainPanel) //
			//.setText("Hola ")
			.setBackground(Color.ORANGE)
			.bindValueToProperty("nombre");
	}

	public static void main(String[] args) {
		new EventsWindow().startApplication();
	}
}
