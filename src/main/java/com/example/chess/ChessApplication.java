package com.example.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessApplication extends Application {

    public static Board chessBoard = new Board();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Board testBoard = new Board();
        testBoard.resetBoard();
        System.out.println(testBoard);
        testBoard.movePiece(new Coordinates(0,7),new Coordinates(0,4));
        System.out.println(testBoard);
        testBoard.resetBoard();
        System.out.println(testBoard);
        launch();
    }
}