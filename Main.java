package NicoleZarch_StasLibman;

import NicoleZarch_StasLibman.controller.Controller;
import NicoleZarch_StasLibman.model.Company;
import NicoleZarch_StasLibman.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Company model = new Company();
		View view = new View(primaryStage);
		Controller controller = new Controller(model, view);
	}

}