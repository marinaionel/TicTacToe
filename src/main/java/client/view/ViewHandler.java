package client.view;

import client.model.Model;
import client.view.game.GameView;
import client.view.lobby.LobbyView;
import client.view.results.ResultsView;
import client.viewModel.ViewModelProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewHandler {
    private ViewModelProvider viewModelProvider;
    private Stage mainStage;

    public ViewHandler(Stage stage, Model model) {
        mainStage = stage;
        mainStage.getIcons().add(new Image("file:icon/xo.png"));
        viewModelProvider = new ViewModelProvider(model, this);
    }

    public void start() {
        openLobby();
    }

    public void openLobby() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getClassLoader().getResource("lobby/lobby.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            return;
        }
        LobbyView view = loader.getController();
        view.init(viewModelProvider.getLobbyViewModel());
        mainStage.setTitle("Lobby");

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void openGame() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getClassLoader().getResource("game/game.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            return;
        }
        GameView view = loader.getController();
        view.init(viewModelProvider.getGameViewModel());
        mainStage.setTitle("Game");

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void openResults() {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getClassLoader().getResource("results/results.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            return;
        }
        ResultsView view = loader.getController();
        view.init(viewModelProvider.getResultsViewModel());
        mainStage.setTitle("Results");

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
