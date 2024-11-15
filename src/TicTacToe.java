import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class TicTacToe extends Application {
    //A character variable to indicate which player's turn it is
    private char whoseTurn = 'X';

    //An inner class for a cell that will make up the playing grid
    public class Cell extends Pane {
    //The token used to fill a cell
    private char token = ' ';

    public Cell() { //
        setStyle("-fx-border-color: blue"); 
        this.setPrefSize(800, 800);
        this.setOnMouseClicked(e -> handleMouseClick()); //Uses a lambda expression to handle the mouse click event
    }

    //Gets the token
    public char getToken() {
        return token;
    }

    //Sets the token
    public void setToken(char c) {
        token = c;
      
        if (token == 'X') { //Draws the two lines that make up the X
            Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10); //The line starts at 10, 10 and ends at the width and height of the cell minus 10
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
            line2.startYProperty().bind(this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));
            
            // Add the lines to the pane
            this.getChildren().addAll(line1, line2); 
      }
        else if (token == 'O') { //Draws the ellipse that makes the O
            Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10); //Draws the ellipse at the center of the cell it is placed in
            ellipse.centerXProperty().bind(this.widthProperty().divide(2));
            ellipse.centerYProperty().bind(this.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));        
            ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));   
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.GRAY);
            
            getChildren().add(ellipse); // Add the ellipse to the pane
      }
    }

    //Handles the mouse click event
        private void handleMouseClick() {
            //Checks if the cell is empty and the game isn't over
            if (token == ' ' && whoseTurn != ' ') {
                setToken(whoseTurn); //Sets token in the cell

                // Check game status
                if (isWon(whoseTurn)) { //Checks to see if one of the players won
                    lblStatus.setText(whoseTurn + " won! The game is over");
                    whoseTurn = ' '; //Indicates the game is over
                }
                else if (isFull()) { //Checks to see if the board is full
                    lblStatus.setText("Draw! The game is over");
                    whoseTurn = ' ';
                }
                else {
                    //Changes the turn
                    whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
                    //Displays whose turn it is
                    lblStatus.setText(whoseTurn + "'s turn");
                }
            }
        }
    }

    //Create and initialize cell
    private Cell[][] cell =  new Cell[5][5];

    //Create and initialize a status label to indicate whose turn it is
    private Label lblStatus = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage) {
        //Creates GridPane to hold cells
        GridPane pane = new GridPane(); 
        for (int i = 0; i < 5; i++){ //Sets the gridpane to be 5x5, creates each cell
            for (int j = 0; j < 5; j++){
                pane.add(cell[i][j] = new Cell(), j, i);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(lblStatus);
        
        //Creates a scene and place it in the stage
        Scene scene = new Scene(borderPane, 450, 450); //Creates the scene with the root node borderPane, with dimensions 450 by 450
        primaryStage.setTitle("TicTacToe"); //Sets the stage title
        primaryStage.setScene(scene); //Places the scene in the stage
        primaryStage.show(); //Displays the stage   
    }

    //Checks to see if all the cells in the gridpane are filled
    public boolean isFull() {
    for (int i = 0; i < 5; i++){
        for (int j = 0; j < 5; j++){
            if (cell[i][j].getToken() == ' '){
                return false;
            }
        }
    }
    return true;
  }

    //Checks every combination of ways the player could win
    public boolean isWon(char token) {
    for (int i = 0; i < 5; i++) //The first two check all the columns and rows for winning lines
        if (cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token && cell[i][3].getToken() == token && cell[i][4].getToken() == token) {
            return true;
        }

    for (int j = 0; j < 5; j++)
        if (cell[0][j].getToken() ==  token && cell[1][j].getToken() == token && cell[2][j].getToken() == token && cell[3][j].getToken() == token && cell[4][j].getToken() == token) {
            return true;
        }
    //The second two check the two diagonals for winning lines
    if (cell[0][0].getToken() == token  && cell[1][1].getToken() == token && cell[2][2].getToken() == token && cell[3][3].getToken() == token && cell[4][4].getToken() == token) {
        return true;
    }

    if (cell[0][4].getToken() == token && cell[1][3].getToken() == token && cell[2][2].getToken() == token && cell[3][1].getToken() == token && cell[4][0].getToken() == token) {
        return true;
    }

    return false;
    }

  
  
    public static void main(String[] args) {
        launch(args);
    }
}

