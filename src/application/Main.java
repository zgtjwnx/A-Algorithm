package application;
	
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.RadioButton;
import java.lang.String;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.stage.FileChooser;


import java.lang.Runtime;

public class Main extends Application {
	
	private ScrollPane root = new ScrollPane();
	private GraphicsContext gc;
	private MazeMap maze_map;
	public double optimum_cost;
	public LinkedList<Vertex> last_result;
	@Override
	public void start(Stage primaryStage) {
		try {
			maze_map = new MazeMap(160, 120);
			GridPane grid = new GridPane();
			root.setContent(grid);
			
			
			
			Button import_map = new Button(); 
			import_map.setText("import map");
			import_map.setTextAlignment(TextAlignment.CENTER);
			import_map.setPrefSize(150, 35);
			grid.setMargin(import_map, new Insets(20));
			grid.add(import_map, 10, 0);
			import_map.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 FileChooser fileChooser = new FileChooser();
					 fileChooser.setTitle("Open Map File");
					 maze_map.import_file(fileChooser.showOpenDialog(primaryStage));
					 showMap();
				 }
				 }); 
			
			Button import_start_goal = new Button(); 
			import_start_goal.setText("import start and goal");
			import_start_goal.setTextAlignment(TextAlignment.CENTER);
			import_start_goal.setPrefSize(150, 35);
			grid.setMargin(import_start_goal, new Insets(20));
			grid.add(import_start_goal, 10, 1);
			import_start_goal.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 FileChooser fileChooser = new FileChooser();
					 fileChooser.setTitle("Open Start and goal File");
					 maze_map.import_start_goal(fileChooser.showOpenDialog(primaryStage));
					 showStartAndGoal();
				 }
				 }); 
			
//			Button test = new Button();
//			test.setText("test");
//			test.setTextAlignment(TextAlignment.CENTER);
//			test.setPrefSize(150, 35);
//			grid.setMargin(test, new Insets(20));
//			grid.add(test, 9, 8);
//			test.setOnAction(new EventHandler<ActionEvent>() {
//				 public void handle(ActionEvent e) {
//					 FileChooser fileChooser = new FileChooser();
//					 fileChooser.setTitle("Open Start and goal File");
//					 maze_map.import_start_goal(fileChooser.showOpenDialog(primaryStage));
//					 showStartAndGoal();
//				 }
//				 }); 
			
			Button generate_map = new Button();
			generate_map.setText("generate map");
			generate_map.setTextAlignment(TextAlignment.CENTER);
			generate_map.setPrefSize(150, 35);
			grid.setMargin(generate_map, new Insets(20));
			grid.add(generate_map, 10 , 2);
			generate_map.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 clearMap();
					 maze_map.generate_file();
					 showMap();
				 }
				 }); 
			
			Button generate_start_goal = new Button();
			generate_start_goal.setText("generate start and end");
			generate_start_goal.setTextAlignment(TextAlignment.CENTER);
			generate_start_goal.setPrefSize(150, 35);
			grid.setMargin(generate_start_goal, new Insets(20));
			grid.add(generate_start_goal, 10 , 3);
			generate_start_goal.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 
					 maze_map.generate_sng();
					 maze_map.print_start_goal_point();
					 showStartAndGoal();
				 }
				 }); 
//			GridPane solve_choice = new GridPane();
//			grid.add(solve_choice, 5, 2);
//			RadioButton s1 = new RadioButton("Uniform-cost");
//			solve_choice.add(s1, 0, 0);
//			RadioButton s2 = new RadioButton("A*");
//			solve_choice.add(s2, 0, 1);
//			RadioButton s3 = new RadioButton("weighted A*");
//			solve_choice.add(s3, 0, 2);
			Button clear_map = new Button();
			clear_map.setText("clear map");
			clear_map.setTextAlignment(TextAlignment.CENTER);
			clear_map.setPrefSize(150, 35);
			grid.setMargin(clear_map, new Insets(20));
			grid.add(clear_map, 10 , 7);
			clear_map.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 clearMap();
					 
				 }
				 }); 
			ObservableList<String> results = FXCollections.observableArrayList();
			
			TextArea show_value = new TextArea();
			show_value.setPrefSize(350, 100);
			grid.add(show_value, 10, 8);
			
			ComboBox result_box = new ComboBox(results);
			result_box.setPrefSize(150, 35);
			grid.setMargin(result_box, new Insets(20));
			grid.add(result_box, 9, 9);
			
			Button btn_value = new Button("Show value");
			btn_value.setPrefSize(150, 35);
			grid.setMargin(btn_value, new Insets(20));
			grid.add(btn_value, 10, 9);
			btn_value.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					String s = result_box.getValue().toString();
					String[] s1 = s.split(",");
					int y = Integer.parseInt(s1[0]);
					int x = Integer.parseInt(s1[1]);
					
					
					for(int i = 0;i < last_result.size(); i++){
						Vertex v = last_result.get(i);
						if(v.x == x && v.y == y){
							show_value.clear();
							show_value.appendText("g="+v.g+",h="+v.h+",f="+v.f);
						}
							
						
					}
					
				}
			});
			
			TextArea show_result = new TextArea();
		    show_result.setPrefSize(350, 100);
	//	    show_result.setMaxSize(400, 150);
			grid.setMargin(show_result, new Insets(20));
			grid.add(show_result, 10,  6	);
			
			
			ObservableList<String> solvers = FXCollections.observableArrayList( 
			         new String("uniform-cost"),new String("weighted A*(1.5)"), new String("A*"), new String("weighted A*(2)"));
			ComboBox solver_choice = new ComboBox(solvers);		
			solver_choice.setPrefSize(150, 35);
			grid.setMargin(solver_choice, new Insets(20));
			grid.add(solver_choice, 10, 4);
			Button solve_btn = new Button();
			solve_btn.setText("Begin");
			solve_btn.setTextAlignment(TextAlignment.CENTER);
			solve_btn.setPrefSize(150, 35);
			grid.setMargin(solve_btn, new Insets(20));
			grid.add(solve_btn, 10,  5);
			solve_btn.setOnAction(new EventHandler<ActionEvent>() {
				 public void handle(ActionEvent e) {
					 if(solver_choice.getValue()==null){
						 Alert alert = new Alert(AlertType.ERROR);
						 alert.initOwner(primaryStage);
						 alert.setTitle("Select solver");
						 alert.setHeaderText("Solver not selected");
						 alert.setContentText("Please select a solver");
						 alert.showAndWait();
					 }else{
						 if(!maze_map.is_loaded()){
							 Alert alert = new Alert(AlertType.ERROR);
							 alert.initOwner(primaryStage);
							 alert.setTitle("Load map");
							 alert.setHeaderText("Need to have a valid map");
							 alert.setContentText("Please import a map or generate a map first");
							 alert.showAndWait();
						 }
						 
						 long startTime;
						 long stopTime;
					     long elapsedTime;
					     long used_memory;
						 switch(solver_choice.getValue().toString()){
						 	case "uniform-cost":
						 		Astar solver_1 = new Astar(maze_map, 0);
						 	    
						 	    startTime = System.currentTimeMillis();
						 	    
						 	    used_memory = solver_1.solve();
						 	    stopTime = System.currentTimeMillis();
						 	    elapsedTime = stopTime - startTime;
						 	    gc.setFill(Color.RED);
								int size = solver_1.result_path.size();
								results.remove(0, results.size());
								for(int i = 0;i < size; i++){
									Vertex v = solver_1.result_path.get(i);
									results.add(v.y+","+ v.x);
									gc.fillOval(v.y * 20 + 11, v.x * 20+11, 5, 5);
								}
								optimum_cost = solver_1.total_cost;
								show_result.clear();
								show_result.appendText("Total cost:"+ solver_1.total_cost);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Expanded number:"+ solver_1.expanded_num);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Running time(mill):"+ elapsedTime);
								show_result.appendText(System.lineSeparator());
								show_result.appendText(""+used_memory);
								//show_expanded_num(solver_1.expanded_list);
								last_result = solver_1.result_path;
								
								break;
						 	case "A*":
						 		Astar solver_2 = new Astar(maze_map, 1);
						 	    solver_2.weight = 1;
						 	    startTime = System.currentTimeMillis();
						 	    used_memory = solver_2.solve();
						 	    stopTime = System.currentTimeMillis();
						 	    elapsedTime = stopTime - startTime;
						 	    gc.setFill(Color.RED);
								int size_2 = solver_2.result_path.size();
								results.remove(0, results.size());
								
								for(int i = 0;i < size_2; i++){
									Vertex v = solver_2.result_path.get(i);
									results.add(v.y+","+ v.x);
									gc.fillOval(v.y * 20 + 11, v.x * 20+11, 5, 5);
								}
								show_result.clear();
								show_result.appendText("Total cost:"+ solver_2.total_cost / optimum_cost);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Expanded number:"+ solver_2.expanded_num);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Running time(mill):"+ elapsedTime);
								show_result.appendText(System.lineSeparator());
								show_result.appendText(""+ used_memory);
								last_result = solver_2.result_path;
								//show_expanded_num(solver_2.expanded_list);
						 		break;
						 	case "weighted A*(1.5)":
						 		Astar solver_3 = new Astar(maze_map, 1.5);
						 		solver_3.weight = 1.5;
						 		startTime = System.currentTimeMillis();
						 		used_memory = solver_3.solve();
						 	    stopTime = System.currentTimeMillis();
						 	    elapsedTime = stopTime - startTime;
						 	    gc.setFill(Color.RED);
								int size_3 = solver_3.result_path.size();
								results.remove(0, results.size());
								for(int i = 0;i < size_3; i++){
									Vertex v = solver_3.result_path.get(i);
									results.add(v.y+","+ v.x);
									gc.fillOval(v.y * 20 + 11, v.x * 20+11, 5, 5);
								}
								show_result.clear();
								show_result.appendText("Total cost:"+ solver_3.total_cost/ optimum_cost);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Expanded number:"+ solver_3.expanded_num);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Running time(mill):"+ elapsedTime);
								show_result.appendText(System.lineSeparator());
								show_result.appendText(""+used_memory);
								//show_expanded_num(solver_3.expanded_list);
								last_result = solver_3.result_path;
						 		break;
							case "weighted A*(2)":
								Astar solver_4 = new Astar(maze_map, 2);
						 		solver_4.weight = 2;
						 		startTime = System.currentTimeMillis();
						 		used_memory = solver_4.solve();
						 	    stopTime = System.currentTimeMillis();
						 	    elapsedTime = stopTime - startTime;
						 	    gc.setFill(Color.RED);
								int size_4 = solver_4.result_path.size();
								results.remove(0, results.size());
								for(int i = 0;i < size_4; i++){
									Vertex v = solver_4.result_path.get(i);
									results.add(v.y+","+ v.x);
									gc.fillOval(v.y * 20 + 11, v.x * 20+11, 5, 5);
								}
								show_result.clear();
								show_result.appendText("Total cost:"+ solver_4.total_cost/ optimum_cost);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Expanded number:"+ solver_4.expanded_num);
								show_result.appendText(System.lineSeparator());
								show_result.appendText("Running time(mill):"+ elapsedTime);
								show_result.appendText(System.lineSeparator());
								show_result.appendText(""+used_memory);
								last_result = solver_4.result_path;
								//show_expanded_num(solver_4.expanded_list);
						 		break;
								
						 }
					 }
				 }
				 }); 
			
		
			Canvas canvas = new Canvas(3202, 2402);
			gc = canvas.getGraphicsContext2D();
			
			
			grid.add(canvas, 0, 0, 8, 8);
			grid.setMargin(canvas, new Insets(50));
			Scene scene = new Scene(root,1900,1400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void clearMap(){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 3202, 2402);
	}
	
	private void show_expanded_num(HashSet<String> list){
		gc.setFill(Color.YELLOW);
		gc.setGlobalAlpha(0.4);
		int x, y;
		Iterator<String> iterator=list.iterator();
			while(iterator.hasNext()){
				System.out.println(iterator.next());
				String read=iterator.next();
				String tmp[]=read.split(",");
				x=Integer.parseInt(tmp[0]);
				y=Integer.parseInt(tmp[1]);
				gc.fillRect(y*20+1, x*20+1, 20, 20);
			}
			gc.setGlobalAlpha(1);
		
	}

	private void showMap(){
		
		gc.setLineWidth(0.3);
		gc.setStroke(Color.BLACK);
		for(int y = 0; y <= 160; y++){
			gc.strokeLine(y*20+1, 0, y*20+1, 2400);
		}
		for(int x = 0; x <= 120; x++){
			gc.strokeLine(0, x*20+1, 3200, x*20+1);
		}
		for(int y = 0; y < 160; y++){
			for(int x = 0; x < 120; x++){
				switch(maze_map.node[x][y]){
					case '2':
						gc.setFill(Color.GREY);
						gc.fillRect(y*20+1, x*20+1, 20, 20);
						break;
					case '0':
						gc.setFill(Color.BLACK);
						gc.fillRect(y*20+1, x*20+1, 20, 20);
						break;
					case 'a':
						gc.setStroke(Color.BLUE);
						gc.setLineWidth(2);
						gc.strokeLine(y*20+11, x*20+1, y*20+11, x*20+21);
						gc.strokeLine(y*20+1,x*20+11,y*20+21,x*20+11);
						break;
					case 'b':
						gc.setFill(Color.GREY);
						gc.fillRect(y*20+1, x*20+1, 20, 20);
						gc.setStroke(Color.BLUE);
						gc.setLineWidth(2);
						gc.strokeLine(y*20+11, x*20+1, y*20+11, x*20+21);
						gc.strokeLine(y*20+1,x*20+11,y*20+21,x*20+11);
						break;
				}
			}
		}
		
		//gc.fillRect(x*10, y*10, 10, 10);
	}
	
	public void showStartAndGoal(){
		gc.setFill(Color.GREEN);
       
		gc.fillOval(maze_map.y1 * 20 + 11, maze_map.x1 * 20+11, 5, 5);
		gc.fillOval(maze_map.y2 * 20 + 11, maze_map.x2 * 20+11, 5, 5);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
