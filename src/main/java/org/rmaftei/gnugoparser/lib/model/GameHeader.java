package org.rmaftei.gnugoparser.lib.model;

public final class GameHeader {

	private final Color computerPlayer;
	
	private final Integer boardsize;

	private final Integer handicap;

	private final Double komi;
	
	private final Color toMove;
	
	private final Long moveNumber;
	
	public GameHeader(Color computerPlayer, Integer boardsize, Integer handicap,
                      Double komi, Color toMove, Long moveNumber) {
		this.computerPlayer = computerPlayer;
		this.boardsize = boardsize;
		this.handicap = handicap;
		this.komi = komi;
		this.toMove = toMove;
		this.moveNumber = moveNumber;
	}

	public Color getComputerPlayer() {
		return computerPlayer;
	}

	public Integer getBoardsize() {
		return boardsize;
	}

	public Integer getHandicap() {
		return handicap;
	}

	public Double getKomi() {
		return komi;
	}

	public Color getToMove() {
		return toMove;
	}

	public Long getMoveNumber() {
		return moveNumber;
	}

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GameHeader{");
        sb.append("computerPlayer=").append(computerPlayer);
        sb.append(", boardsize=").append(boardsize);
        sb.append(", handicap=").append(handicap);
        sb.append(", komi=").append(komi);
        sb.append(", toMove=").append(toMove);
        sb.append(", moveNumber=").append(moveNumber);
        sb.append('}');
        return sb.toString();
    }
}
