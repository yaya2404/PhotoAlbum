package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NonAdminController {
	@FXML Button create;
	@FXML Button delete;
	@FXML Button rename;
	@FXML Button open;
	@FXML Button search;
	@FXML Button logout;
	@FXML TextField album;
	@FXML TextField tagtype;
	@FXML TextField tagvalue;
	@FXML ListView listView;
	
	private Stage stage;
	/**
	 * Load albums from database
	 */
	public void start(Stage stage){
		this.stage = stage;
	}
	
	public void cdro(){
		
	}
}
