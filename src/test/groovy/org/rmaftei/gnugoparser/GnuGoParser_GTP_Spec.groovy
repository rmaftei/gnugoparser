package org.rmaftei.gnugoparser

import org.rmaftei.gnugoparser.lib.model.Color
import org.rmaftei.gnugoparser.lib.model.Game
import org.rmaftei.gnugoparser.lib.model.Position
import org.rmaftei.gnugoparser.lib.model.Stone
import org.rmaftei.gnugoparser.lib.parsers.GnuGOParseException
import org.rmaftei.gnugoparser.lib.parsers.GnuGoParser
import spock.lang.Specification

class GnuGoParser_GTP_Spec extends Specification {
    void "spaces input"() {
        given:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            gnuParser.parse('  ')
        then:
            GnuGOParseException ex = thrown()
            ex.message == 'The input is empty'
    }

    void "null input"() {
        given:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            gnuParser.parse(null)
        then:
            GnuGOParseException ex = thrown()
            ex.message == 'The input is empty'
    }

    void "show board"() {
        given:
            String text = new File("src/test/resources/gtp/showboard").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            def game = gnuParser.parse(text)
        then:
            game.board.isEmpty()
    }

    void "show board with stone"() {
        given:
            String text = new File("src/test/resources/gtp/showboard02").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            def game = gnuParser.parse(text)
        then:
            !game.board.isEmpty()
            game.stonePlayed(new Stone(Color.BLACK, new Position(5, 6)))
            game.stonePlayed(new Stone(Color.WHITE, new Position(5, 4)))
            game.blackCapture == 41
            game.whiteCapture == 3
    }

    void "parse invalid command"() {
        given:
            String text = new File("src/test/resources/gtp/invalid_command").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then: "This should remain the same  if a command is invalid"
            game.gameHeader == null
            game.board.isEmpty()
            game.invalidCommand
            game.invalidMessage == 'Unknown command'
    }

    void "parse invalid command2"() {
        given:
            String text = new File("src/test/resources/gtp/invalid_command2").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then: "This should remain the same  if a command is invalid"
            game.gameHeader == null
            game.board.isEmpty()
            game.invalidCommand
            game.invalidMessage == 'Unknown command'
    }

    void "parse illegal move"() {
        given:
            String text = new File("src/test/resources/gtp/illegal_move").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then: "This should remain the same  if an illegal move was played"
            game.gameHeader == null
            game.board.isEmpty()
            game.invalidCommand
            game.invalidMessage == 'Illegal move'
    }

    void "parse last move"() {
        given:
            String text = new File("src/test/resources/gtp/last_move").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            game.board.isEmpty()
            game.lastMove == new Stone(Color.WHITE, new Position(6,3))
    }

    void "parse last move with board"() {
        given:
            String text = new File("src/test/resources/gtp/last_move2").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            !game.board.isEmpty()
            game.lastMove == new Stone(Color.WHITE, new Position(6,3))
    }

    void "parse pass command"() {
        given:
            String text = new File("src/test/resources/gtp/pass_command").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            game.board.isEmpty()
            game.lastMove == new Stone(Color.BLACK, new Position(0,0))
    }

    void "parse winner and score"() {
        given:
            String text = new File("src/test/resources/gtp/end_game").text
        and:
            GnuGoParser gnuParser = GnuGOModes.GTP.createParser()
        when:
            Game game = gnuParser.parse(text)
        then:
            game.gameHeader == null
            game.board.isEmpty()
            game.gameOver
            game.winner == Color.WHITE
            game.score == "10.0"
    }
}
