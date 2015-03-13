package org.rmaftei.gnugoparser.lib.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contains the information<br>
 * about the current game.
 * <hr>
 * 
 * @author rmaftei
 */
public class Game {

	private GameHeader gameHeader;

	private Set<Stone> board = new HashSet<Stone>();

	private int whiteCapture = 0;

	private int blackCapture = 0;

	private Stone lastMove;

	private Color winner;
	
	private String score;

    private boolean invalidCommand = false;

    private String invalidMessage;

	private boolean gameOver = false;

    public Set<Stone> getBoard() {
        return board;
	}

    public void playStone(Stone stone) {
        this.board.add(stone);
    }

    public boolean stonePlayed(Stone searchStone) {
        for(Stone stone : board) {
            if(stone.equals(searchStone)) {
                return true;
            }
        }

        return false;
    }

	public int getWhiteCapture() {
		return whiteCapture;
	}

	public void setWhiteCapture(int whiteCapture) {
		this.whiteCapture = whiteCapture;
	}

	public int getBlackCapture() {
		return blackCapture;
	}

	public void setBlackCapture(int blackCapture) {
		this.blackCapture = blackCapture;
	}

	public Stone getLastMove() {
		return lastMove;
	}

	public void setLastMove(Stone lastMove) {
		this.lastMove = lastMove;
	}

	public Color getWinner() {
		return winner;
	}

	public void setWinner(Color winner) {
		this.winner = winner;
		this.gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public GameHeader getGameHeader() {
		return gameHeader;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

    public boolean isInvalidCommand() {
        return invalidCommand;
    }

    public void setInvalidCommand(boolean invalidCommand) {
        this.invalidCommand = invalidCommand;
    }

    public String getInvalidMessage() {
        return invalidMessage;
    }

    public void setInvalidMessage(String invalidMessage) {
        this.invalidMessage = invalidMessage;
    }

    public void setGameHeader(GameHeader gameHeader) {
        this.gameHeader = gameHeader;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Game{");
        sb.append("gameHeader=").append(gameHeader);
        sb.append(", board=").append(board);
        sb.append(", whiteCapture=").append(whiteCapture);
        sb.append(", blackCapture=").append(blackCapture);
        sb.append(", lastMove=").append(lastMove);
        sb.append(", winner=").append(winner);
        sb.append(", score='").append(score).append('\'');
        sb.append(", invalidCommand=").append(invalidCommand);
        sb.append(", invalidMessage='").append(invalidMessage).append('\'');
        sb.append(", gameOver=").append(gameOver);
        sb.append('}');
        return sb.toString();
    }
}
