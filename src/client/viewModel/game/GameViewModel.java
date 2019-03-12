package client.viewModel.game;

import client.model.Model;
import client.view.ViewHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.model.Game;

import java.beans.PropertyChangeEvent;

public class GameViewModel {
    private Model model;
    private ViewHandler viewHandler;
    private StringProperty errorLabel;

    private StringProperty r1c1;
    private StringProperty r1c2;
    private StringProperty r1c3;
    private StringProperty r2c1;
    private StringProperty r2c2;
    private StringProperty r2c3;
    private StringProperty r3c1;
    private StringProperty r3c2;
    private StringProperty r3c3;

    public GameViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;
        errorLabel = new SimpleStringProperty();
        model.addListener("Join game", this::joinGame);
        model.addListener("can't place", this::setErrorLabelStatus);
        model.addListener("placed", this::validPlacing);
        r1c1 = new SimpleStringProperty();
        r1c2 = new SimpleStringProperty();
        r1c3 = new SimpleStringProperty();
        r2c1 = new SimpleStringProperty();
        r2c2 = new SimpleStringProperty();
        r2c3 = new SimpleStringProperty();
        r3c1 = new SimpleStringProperty();
        r3c2 = new SimpleStringProperty();
        r3c3 = new SimpleStringProperty();

    }

    public StringProperty r1c1Property() {
        return r1c1;
    }

    public StringProperty r1c2Property() {
        return r1c2;
    }

    public StringProperty r1c3Property() {
        return r1c3;
    }

    public StringProperty r2c1Property() {
        return r2c1;
    }

    public StringProperty r2c2Property() {
        return r2c2;
    }

    public StringProperty r2c3Property() {
        return r2c3;
    }

    public StringProperty r3c1Property() {
        return r3c1;
    }

    public StringProperty r3c2Property() {
        return r3c2;
    }

    public StringProperty r3c3Property() {
        return r3c3;
    }

    private void validPlacing(PropertyChangeEvent event) {
        Game game = (Game) event.getNewValue();
        Platform.runLater(() -> {
            r1c1.setValue(signToString(game.getPlace(0, 0)));
            r1c2.setValue(signToString(game.getPlace(0, 1)));
        });
        System.out.println(signToString(game.getPlace(0, 0)) + "::r1c1");
        System.out.println(signToString(game.getPlace(0, 1)) + "::r1c2");
    }

    private String signToString(Game.Sign sign) {
        if (sign == Game.Sign.ZERO)
            return "O";
        if (sign == Game.Sign.CROSS)
            return "X";
        return "";
    }

    //when an opponent is found this method will be called
    private void joinGame(PropertyChangeEvent evt) {
        //TODO get sign from evt and display it in GUI
        Platform.runLater(() -> viewHandler.openGame());
    }

    public void place(int row, int column) {
        model.getiClient().place(row - 1, column - 1, model.getMySign());
    }

    public Game.Sign getMySign() {
        return model.getMySign();
    }

    public StringProperty errorLabelProperty() {
        return errorLabel;
    }

    public String getPlayerName() {
        return model.getPlayer().getPlayerName();
    }

    private void setErrorLabelStatus(PropertyChangeEvent event) {
        Platform.runLater(() -> errorLabel.setValue((String) event.getNewValue()));
    }
}
