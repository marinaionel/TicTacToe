package server;

import shared.model.Game;
import shared.model.RequestCodes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket socket;
    private boolean isInGame;
    private Game.Sign sign;
    ObjectInputStream socketIn = null;
    ObjectOutputStream socketOut = null;
    private String playerName;
    private boolean tryPlayResult;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    public void joinGame(Game.Sign sign) {
        this.sign = sign;
        isInGame = true;
        try {
            socketOut.writeUTF(RequestCodes.JOINED_GAME + "|" + (sign == Game.Sign.ZERO ? "O" : "X"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String request = "";
        try {
            socketIn = new ObjectInputStream(socket.getInputStream());
            socketOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                request = socketIn.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(request);

            if (request.contains(RequestCodes.PLAY)) {
                playerName = request.split("|")[1];
                tryPlayResult = GameCreator.tryPlay(this);
                try {
                    socketOut.writeUTF(tryPlayResult ? RequestCodes.OPPONENT_FOUND : RequestCodes.WAITING_FOR_OPPONENT);
                    socketOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if(request.contains(RequestCodes.TRY_PLACE))
            {

            }

            if(request.contains(RequestCodes.WIN))
            {

            }




        }
    }

    public String getPlayerName() {
        return playerName;
    }


}
