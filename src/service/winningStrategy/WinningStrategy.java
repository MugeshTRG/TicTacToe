package service.winningStrategy;

import Model.Board;
import Model.Move;
import Model.Player;

public interface WinningStrategy {
    Player checkWinner(Board board, Move lastMove);
}
