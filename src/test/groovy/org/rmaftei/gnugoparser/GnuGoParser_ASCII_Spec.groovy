package org.rmaftei.gnugoparser

import org.rmaftei.gnugoparser.lib.model.Color
import org.rmaftei.gnugoparser.lib.model.Game
import org.rmaftei.gnugoparser.lib.model.Position
import org.rmaftei.gnugoparser.lib.model.Stone
import org.rmaftei.gnugoparser.lib.parsers.GnuGOParseException
import org.rmaftei.gnugoparser.lib.parsers.GnuGoParser
import spock.lang.Specification;

class GnuGoParser_ASCII_Spec extends Specification {
    void "empty input"() {
        given:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            gnuParser.parse('')
        then:
            GnuGOParseException ex = thrown()
            ex.message == 'The input is empty'
    }

    void "spaces input"() {
        given:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            gnuParser.parse('  ')
        then:
            GnuGOParseException ex = thrown()
            ex.message == 'The input is empty'
    }

    void "null input"() {
        given:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            gnuParser.parse(null)
        then:
            GnuGOParseException ex = thrown()
            ex.message == 'The input is empty'
    }

	void "start game"() {
		given:
			String text = new File("src/test/resources/start_game").text
		and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
		when:
			Game game = gnuParser.parse(text)
		then:
			game.gameHeader.computerPlayer == Color.WHITE
			game.gameHeader.boardsize == 13
			game.gameHeader.handicap == 0
			game.gameHeader.komi == 0.0
			game.gameHeader.toMove == Color.BLACK
            game.board.empty
	}
	
	void "parse move 01"() {
		given:
			String text = new File("src/test/resources/move_01").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
		when:
            Game game = gnuParser.parse(text)
		then:
            game.gameHeader == null
			game.board != null
			game.lastMove == new Stone(Color.BLACK, new Position(4, 4))
			game.whiteCapture == 0
			game.blackCapture == 0
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
			
	}
	
	void "parse move 02"() {
		given:
			String text = new File("src/test/resources/move_02").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then:
            game.gameHeader == null
			game.board != null
            game.board.size() == 2
            game.lastMove == new Stone(Color.WHITE, new Position(11, 10))
			game.whiteCapture == 0
			game.blackCapture == 0
			game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
	}
	
	void "parse captures stones"() {
		given:
			String text = new File("src/test/resources/captured").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then:
            game.gameHeader == null
			game.board != null
            game.lastMove == new Stone(Color.BLACK, new Position(10, 4))
			game.whiteCapture == 13
			game.blackCapture == 3
			game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
	}

	void "parse invalid command"() {
		given:
			String text = new File("src/test/resources/invalid_command").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then: "This should remain the same  if a command is invalid"
            game.gameHeader == null
			game.board != null
            game.lastMove == new Stone(Color.BLACK, new Position(10, 4))
			game.whiteCapture == 13
			game.blackCapture == 3
            game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.invalidCommand
            game.invalidMessage == 'Invalid command: xz'
	}

	void "parse illegal move"() {
		given:
			String text = new File("src/test/resources/illegal_move").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then: "This should remain the same  if an illegal move was played"
            game.gameHeader == null
			game.board != null
            game.lastMove == new Stone(Color.BLACK, new Position(10, 4))
			game.whiteCapture == 13
			game.blackCapture == 3
            game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.invalidCommand
            game.invalidMessage == 'Illegal move: l10'
	}

	void "parse pass command"() {
		given:
			String text = new File("src/test/resources/pass_command").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then:
            game.gameHeader == null
			game.board != null
            game.lastMove == new Stone(Color.WHITE, new Position(0, 0))
			game.whiteCapture == 13
			game.blackCapture == 3
            game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
	}

    void "parse resign command"() {
        given:
            String text = new File("src/test/resources/resign_command").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
        then: "This should remain the same  if an illegal move was played"
            game.gameHeader == null
            game.board != null
            game.lastMove == new Stone(Color.BLACK, new Position(10, 4))
            game.whiteCapture == 13
            game.blackCapture == 3
            game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.gameOver
            game.winner == Color.WHITE
            game.score == "Resign"
    }

	void "parse winner and score"() {
		given:
			String text = new File("src/test/resources/end_game").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
		then:
            game.gameHeader == null
			game.board != null
            game.lastMove == new Stone(Color.BLACK, new Position(0, 0))
			game.whiteCapture == 23
			game.blackCapture == 1
            game.stonePlayed(new Stone(Color.WHITE, new Position(11, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
			game.gameOver
			game.winner == Color.WHITE
            game.score == "10.0"
	}

    void "parse game with handicap"() {
        given: "Special case"
            String text = new File("src/test/resources/start_game_handicap").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader.computerPlayer == Color.WHITE
            game.gameHeader.boardsize == 13
            game.gameHeader.handicap == 4
            game.gameHeader.komi == 0.0
            game.gameHeader.toMove == Color.WHITE
            !game.board.empty
            game.lastMove == null
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(10, 10)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 10)))
    }

    void "parse move to capture1"() {
        given:
            String text = new File("src/test/resources/to_capture1").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            game.board != null
            game.board.size() == 5
            game.lastMove == new Stone(Color.WHITE, new Position(3, 6))
            game.whiteCapture == 0
            game.blackCapture == 0
            game.stonePlayed(new Stone(Color.WHITE, new Position(3, 6)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(3, 5)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(3, 7)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 6)))
    }

    void "parse move captured2"() {
        given:
            String text = new File("src/test/resources/to_capture2").text
        and:
            GnuGoParser gnuParser = GnuGOModes.ASCII.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            game.board != null
            game.board.size() == 5
            game.lastMove == new Stone(Color.BLACK, new Position(2, 6))
            game.whiteCapture == 0
            game.blackCapture == 1
            !game.stonePlayed(new Stone(Color.WHITE, new Position(3, 6)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 4)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(3, 5)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(3, 7)))
            game.stonePlayed(new Stone(Color.BLACK, new Position(4, 6)))
    }
}
