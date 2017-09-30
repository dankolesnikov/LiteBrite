/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package litebrite;


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Danil Kolesnikov danil.kolesnikov@sjsu.edu
 * CS 151 HW3 Fall 2017
 */

public class LiteBrite extends Application {

    int rows = 50; // Number of rows in the grid
    int columns = 50; // Number of columns in the grid
    private GridPane grid = new GridPane();
    final ColorPicker colorPicker = new ColorPicker();
    Paint color = colorPicker.getValue(); // Hold the color value

    public void setColor(Paint color){
        this.color = color;
    }
    public void setColorPicker(Color color){
        colorPicker.setValue(color);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        grid.getStyleClass().add("game-grid");
        Text about = new Text("Danil Kolesnikov\nCS 151 Fall 2017 - SJSU\ndanil.kolesnikov@sjsu.edu");
        VBox mainBox = new VBox(); // Create vertical main box that will contain the grid and bottom box
        HBox bottomBox = new HBox(10); // Create the box that contains Buttons and Text field. Spacing = 10
        bottomBox.getChildren().add(colorPicker); // Add Color Picker to the Bottom Box
        bottomBox.getStyleClass().add("bottom-box");
        mainBox.getStyleClass().add("main-box");

        this.setColorPicker(Color.WHITE); // Set color to while when the program starts
        colorPicker.getStyleClass().add("button");
        colorPicker.setOnAction(e ->{
            this.setColor(colorPicker.getValue());
        });

        // add 50 columns
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(10);
            grid.getColumnConstraints().add(column);
        }

        // add 50 rows
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(10);
            grid.getRowConstraints().add(row);
        }

        // initialize the grid
        newGridPane(grid);

        // Create reset button that refreshes the pane
        Button resetButton = new Button("Reset");
        resetButton.getStyleClass().add("button");
        bottomBox.getChildren().add(resetButton);// add Reset Button to the Bottom Box
        resetButton.setOnAction(e ->{
            grid = newGridPane(grid);
            this.setColorPicker(Color.WHITE); // Reset the color picker to white
            color = Color.WHITE; // Set the value of color to white
        });

        bottomBox.getChildren().add(about); // Add the Text field to the Bottom Box
        mainBox.getChildren().addAll(grid, bottomBox); // Add the grid and Bottom Box(Buttons, Text field) to Main Box
        Scene scene = new Scene(mainBox);
        scene.getStylesheets().add(LiteBrite.class.getResource("resources/game.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("LiteBrite");
        stage.show();
    }

    public static class Anims {

        public static Node getAtoms(Paint color) {
            // 9 because our Cell has Row and Column of 10
            Node filledRectangle = new Rectangle(9,9,color);
            return filledRectangle;
        }
    }

    public GridPane newGridPane(GridPane grid){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                Pane pane = new Pane();
                pane.setOnMouseReleased(e -> {
                    // when the user presses on filled cell -> cell gets cleared
                    boolean cellEmpty = pane.getChildren().isEmpty();
                    if (cellEmpty){
                        pane.getChildren().add(Anims.getAtoms(color));
                    }
                    else{
                        pane.getChildren().clear();
                    }
                });
                pane.getStyleClass().add("game-grid-cell");
                if (i == 0) {
                    pane.getStyleClass().add("first-column");
                }
                if (j == 0) {
                    pane.getStyleClass().add("first-row");
                }
                grid.add(pane, i, j);
            }
        }
        return grid;
    }

    public static void main(final String[] arguments) {
        Application.launch(arguments); // Launch JavaFX application
    }
    
}
