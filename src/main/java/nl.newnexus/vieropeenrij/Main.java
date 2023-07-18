package nl.newnexus.vieropeenrij;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.presentGameStatus();
        int column = game.getUserInput();
        game.doMove(column);
    }
}
