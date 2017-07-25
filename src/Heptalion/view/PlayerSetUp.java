package hw5.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerSetUp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		Button createButton = new Button("Create Game");
		
        createButton.setOnAction(new EventHandler<ActionEvent>() {       
            @Override
            public void handle(ActionEvent event) {
            	String player1Name = null, player2Name = null;
            	if(text1.getText()!= null && !text1.getText().trim().isEmpty()) {
            		player1Name = text1.getText();
            	}
            	System.out.println(player1Name);
            	
            	if(text2.getText()!= null && !text2.getText().trim().isEmpty()) {
            		player2Name = text2.getText();
            	}
            	System.out.println(player2Name);
            }
        });
		
		VBox root = new VBox();

		
		root.getChildren().add(text1);
		root.getChildren().add(text2);
		root.getChildren().add(createButton);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
