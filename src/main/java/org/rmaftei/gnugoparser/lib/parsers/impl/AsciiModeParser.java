package org.rmaftei.gnugoparser.lib.parsers.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rmaftei.gnugoparser.lib.model.*;
import org.rmaftei.gnugoparser.lib.parsers.*;

/**
 * This class is parsing the game of GO in ASCII mode.<br>
 * 
 * This is useful to parse gnu go games<br>
 * from telnet(For example from Panda IGS).
 * 
 * @author rmaftei
 */
public final class AsciiModeParser extends AbstractParser implements GnuGoParser {

    public Game parse(String text) throws GnuGOParseException {
        validateInputAndCreateGame(text);

        parsingGameHeader(text);

        checkAndSetInvalidMove(text);
        parseCapturedStones(text);
        parseLastMove(text);
        parseTheResult(text);
        parseBoard(text);

        return game;
    }

    private void parsingGameHeader(String text) {
        boolean hasHeader = false;

        Integer boardsize = null;
        Integer handicap = null;
        Double komi = null;
        Long moveNumber = null;
        Color toMove = null;
        Color computerPlayer = null;

        Pattern pattern = Pattern.compile(Constants.GAME_HEADER);
        Matcher match = pattern.matcher(text);

        while(match.find()) {
            hasHeader = true;

            String[] split = match.group(0).split(Constants.SPACE_REGEX + "{2,}");

            switch(split[0]) {
                case Constants.BOARD_SIZE: {
                    boardsize = Integer.valueOf(split[1]);
                    break;
                }
                case Constants.HANDICAP: {
                    handicap = Integer.valueOf(split[1]);
                    break;
                }
                case Constants.KOMI: {
                    komi = Double.valueOf(split[1]);
                    break;
                }
                case Constants.MOVE_NUMBER: {
                    moveNumber = Long.valueOf(split[1]);
                    break;
                }
                case Constants.TO_MOVE: {
                    toMove = Color.getColor(split[1]);
                    break;
                }
            }

            if(split[0].contains(Constants.COMPUTER_PLAYER)) {
                String[] localSplit = split[0].split(Constants.SPACE);
                computerPlayer = Color.getColor(localSplit[2]);
            }
        }

        if(hasHeader) {
            game.setGameHeader(new GameHeader(computerPlayer, boardsize, handicap, komi, toMove, moveNumber));
        }
    }

    protected boolean parseInvalidCommand(String text) {
        return parseIllegalAndInvalid(text, Constants.INVALID_COMMAND);
    }

    protected boolean parseIllegalMove(String text) {
        return parseIllegalAndInvalid(text, Constants.ILLEGAL_MOVE);
    }

    private boolean parseIllegalAndInvalid(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(text);

        if(match.find()) {
            game.setInvalidCommand(true);
            game.setInvalidMessage(match.group());

            return true;
        }

        game.setInvalidCommand(false);
        return false;
    }

	private void parseCapturedStones(String text) {
        parsingAction(text, Constants.CAPTURED_STONES_REGEX, new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String strFound = match.group(0);
                String stones = strFound.replaceAll(Constants.CAPTURED_STONES_REGEX_2, Constants.NOTHING).replaceAll(" pieces", Constants.NOTHING);

                if (strFound.contains(Constants.WHITE)) {
                    game.setWhiteCapture(Integer.parseInt(stones));
                } else if (strFound.contains(Constants.BLACK)) {
                    game.setBlackCapture(Integer.parseInt(stones));
                }
            }
        });
	}

	private void parseLastMove(String text) {
        parsingAction(text, Constants.LAST_MOVE_REGEX, new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String line = match.group(0);

                line = line.replace("Last move: ", Constants.NOTHING);
                line = line.trim();

                String[] split = line.split(Constants.SPACE_REGEX);

                if(line.contains(Constants.PASS)) {
                    game.setLastMove(new Stone(Color.getColor(split[0]), new Position(0, 0)));
                }
                else {
                    String lineLetter = split[1].substring(0,1);

                    int columnNumber = letterMapping.get(lineLetter);
                    int lineNumber = Integer.parseInt(split[1].substring(1));

                    game.setLastMove(new Stone(Color.getColor(split[0]), new Position(lineNumber, columnNumber)));
                }
            }
        });
	}

    protected void parseTheResult(String text) {
        parsingAction(text, Constants.RESULT_REGEX, new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String[] score = match.group(0).split(Constants.SPACE_REGEX);

                if(score[1].contains(Constants.WHITE_CORE_REGEX)) {
                    String[] s = score[1].split(Constants.PLUS_REGEX);
                    game.setScore(s[1]);
                    game.setWinner(Color.WHITE);
                }

                if(score[1].contains(Constants.BLACK_SCORE_REGEX)) {
                    String[] s = score[1].split(Constants.PLUS_REGEX);
                    game.setScore(s[1]);
                    game.setWinner(Color.BLACK);
                }
            }
        });
    }
}
