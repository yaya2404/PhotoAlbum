package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController {
	
	@FXML private Button add;
	@FXML private Button edit;
	@FXML private Button logout;
	
	private Stage stage;
	public void start(Stage mainstage){
		this.stage = mainstage;
	}
	
}
