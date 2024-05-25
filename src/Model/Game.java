package Model;

import exception.InvalidBotCountException;
import exception.InvalidPlayerSizeException;
import exception.InvalidSymbolSetupException;
import service.winningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Game {
    private Board currentBoard;
    private List<Player> players;
    private Player currentPlayer;
    private GameStatus gameStatus;
    private List<Move> moves;
    private List<Board> boardStates;
    private WinningStrategy winningStrategy;
    private int numberOfSymbols;

    private Game(Board currentBoard, List<Player> players, WinningStrategy winningStrategy){
        this.currentBoard = currentBoard;
        this.players = players;
        this.winningStrategy = winningStrategy;
        this.currentPlayer = null;
        this.boardStates = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.numberOfSymbols = players.size();
    }
    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public static Builder builder(){
        return new Builder();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Board> getBoardStates() {
        return boardStates;
    }

    public void setBoardStates(List<Board> boardStates) {
        this.boardStates = boardStates;
    }

    public WinningStrategy getWinningStrategy() {
        return winningStrategy;
    }

    public void setWinningStrategy(WinningStrategy winningStrategy) {
        this.winningStrategy = winningStrategy;
    }

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public void setNumberOfSymbols(int numberOfSymbols) {
        this.numberOfSymbols = numberOfSymbols;
    }

    public static class Builder {
        private int dimension;
        private Board currentBoard;
        private List<Player> players;
        private WinningStrategy winningStrategy;

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setCurrentBoard(Board currentBoard) {
            this.currentBoard = currentBoard;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }

        private void validateNumberOfPlayers(){
            if(players.size() < dimension-2 || players.size() >= dimension){
                throw new InvalidPlayerSizeException("Player size should be N-2 or N-1 as per board size");
            }
        }

        private void validatePlayerSymbols(){
            HashSet<Character> symbols = new HashSet<>();
            for(Player player: players){
                symbols.add(player.getSymbol());
            }
            if(symbols.size() != players.size()){
                throw new InvalidSymbolSetupException("There should be unique symbols for all the players");
            }
        }

        private void validateBotCount(){
            int botCount = 0;
            for(Player player: players){
                if(player.getPlayerType().equals(PlayerType.BOT)) botCount++;
            }
            if(botCount > 1 || botCount < 0){
                throw new InvalidBotCountException("We can have maximum of 1 bot per game");
            }
        }

        private void validate(){
            validateBotCount();
            validateNumberOfPlayers();
            validatePlayerSymbols();
        }

        public Game build(){
            validate();
            return new Game(new Board(dimension), players, winningStrategy);
        }

    }

}
