package el._main;

import el.chart._ui.Chart_Main;
import el.game._ui.Game_Main;
import el.clock._ui.Clock_Main;
import el.media._ui.Media_Main;
import el.note._ui.Note_Main;
public class Controller {
	MainUI main;

	public void toTitle() {
		main.changePane(new Title(this));
	}

	public void play() {
		main.changePane(new Game_Main(this));
	}
	public void media() {
		main.changePane(new Media_Main(this));
	}
	public void note() { main.changePane(new Note_Main(this)); }
	public void chart() {
		main.changePane(new Chart_Main(this));
	}
	public void clock() {
		main.changePane(new Clock_Main(this));
	}
	public Controller(MainUI main) {
		this.main=main;

	}

	public void save() {
		// TODO Auto-generated method stub
		
	}


}
