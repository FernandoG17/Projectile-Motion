package application;
	
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	
	private GridPane grid;
	private Scene scene;
	Canvas canva; 
	Button button1; 
	TextField initialVel;
	TextField angle;
	Label V0;
	Label Angle;
	int count =0;
	Label display;
	double x,y;
	double totalTime;
	double highest;
	double g=9.81;
	
	
	public void settingScene() {
		grid = new GridPane(); 
		grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5));
        scene = new Scene(grid, 750, 500);
        grid.setStyle("-fx-background-color: WHITE");
        addControls();
	}
	
	
	public void addControls() {
		
		grid.getColumnConstraints().add(new ColumnConstraints(100)); // add new column
		grid.getColumnConstraints().add(new ColumnConstraints(100));
		
		Canvas canvas = new Canvas(740,300);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0,740,300);
		grid.add(canvas, 0, 0);
		
		button1 = new Button("Button");
		button1.setFont(new Font("Arial",10));
		grid.add(button1, 1,4,4,4);
		
		V0 = new Label("Initial Velocity");
		V0.setFont(new Font("Arial",15));
		grid.add(V0, 0, 1,4,1);
		
		Angle = new Label("Angle");
		Angle.setFont(new Font("Arial", 15));
		grid.add(Angle, 0, 2);
				
		
		initialVel = new TextField();
		initialVel.setFont(new Font("Arial",15));
		initialVel.setPrefWidth(100);; 
		grid.add(initialVel, 1,1,4,1);
		
		angle = new TextField();
		angle.setFont(new Font("Arial",15));
		angle.setPrefColumnCount(10); 
		grid.add(angle, 1,2,4,1);
		
		display = new Label();
		display.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		//display.setPrefSize(100, 100);
		grid.add(display,5,1);
		
		button1.setOnAction(e -> {
			double totalD;
			String initial = initialVel.getText();
			String ang = angle.getText();
			Double inV = Double.parseDouble(initial);
			Double Ang = Double.parseDouble(ang);
			
		    List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ALICEBLUE, Color.ANTIQUEWHITE, Color.CORNFLOWERBLUE);

		    // Select a random color from the list
			
		    gc.setStroke(colors.get(count));
		    gc.setLineWidth(2);

		    double xPrev = 0;
		    double yPrev = 300;

		    for (double i = 0.5; i < 200; i = i+0.5) {
		    	double angle = Ang * Math.PI/180; 
                x = inV * i * Math.cos(angle);
                y = inV * i * Math.sin(angle) - 0.5 * g * Math.pow(i, 2);
                
                gc.strokeLine(xPrev, yPrev, x, 300 - y);
		        
		        xPrev = x;
		        yPrev = 300 - y;
		        
		        
		   }
		    
		    count++;
		    if (count == colors.size()) {
		    	count =0;
		    }
		    
		    
		    double angle2 = Ang * Math.PI / 180;
		    totalTime = 2 * inV * Math.sin(angle2)/g;
		    totalD = Math.pow(inV, 2) * Math.sin(2*angle2)/g;
		    highest = Math.pow(inV, 2) * (Math.pow(Math.sin(angle2),2)/(2*g));
		    
		    display.setText("The total time is " + String.format("%.2f", totalTime) + "\n Total Distance: " + String.format("%.2f", totalD) + "\n Highest point: " + String.format("%.2f", highest));
		   
		});
		
	}
	
	
	@Override
	public void start(Stage stage) {
		try {
			settingScene();
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("Projectile Motion");
            stage.setResizable(false);
            stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
