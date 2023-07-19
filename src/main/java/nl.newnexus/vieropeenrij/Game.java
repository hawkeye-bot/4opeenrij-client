package nl.newnexus.vieropeenrij;

import nl.newnexus.model.BoardStatus;
import nl.newnexus.model.GameStatus;
import nl.newnexus.model.Move;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;

public class Game {

    static final String baseUrl = "http://localhost:8080/";
    static final int maxCharsCellText = 6;
    static final Move.PlayerEnum playColor = Move.PlayerEnum.YELLOW;


    private String getCellText(List<List<BoardStatus.BoardEnum>> board, int column, int row) {
        String text = board.get(column).get(row).toString();
        return StringUtils.rightPad(text, maxCharsCellText);
    }

    public void presentGameStatus() {
        try {
            final String uri = baseUrl + "status";
            RestTemplate restTemplate = new RestTemplateBuilder()
                    .basicAuthentication("piet", "niet")
                    .build();
            GameStatus gameStatus = restTemplate.getForObject(uri, GameStatus.class);
            BoardStatus board = gameStatus.getBoardStatus();

            int rowSize = board.getBoard().get(0).size();
            int columnSize = board.getBoard().size();

            for (int row = (rowSize - 1); row >= 0; row--) {
                StringBuilder rowText = new StringBuilder();
                for (int column = 0; column <= (columnSize - 1); column++) {
                    rowText.append("|").append(getCellText(board.getBoard(), column, row));
                    if (column == (columnSize - 1)) {
                        rowText.append("|");
                    }
                }
                System.out.println(rowText);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            return getUserInput();
        }
    }

    public void doMove(int column) {
        Move move = new Move();
        move.setMoveColumn(column);
        move.setPlayer(playColor);
        try {
            final String uri = baseUrl + "move";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(uri, move, Move.class);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
