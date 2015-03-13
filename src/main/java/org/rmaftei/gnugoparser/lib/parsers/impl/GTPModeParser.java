package org.rmaftei.gnugoparser.lib.parsers.impl;

import org.rmaftei.gnugoparser.lib.model.Color;
import org.rmaftei.gnugoparser.lib.model.Game;
import org.rmaftei.gnugoparser.lib.model.Position;
import org.rmaftei.gnugoparser.lib.model.Stone;
import org.rmaftei.gnugoparser.lib.parsers.*;

import java.util.regex.Matcher;

public class GTPModeParser  extends AbstractParser implements GnuGoParser {
    @Override
    public Game parse(String text) throws GnuGOParseException {
        validateInputAndCreateGame(text);

        checkAndSetInvalidMove(text);

        parseTheResult(text);

        parseCapturedStones(text);

        parseLastMove(text);

        parseBoard(removeCapturedStones(text));

        return game;
    }

    private void parseLastMove(String text) {
        parsingAction(text, "= (white|black) (\\w[0-9]+|PASS)", new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String line = match.group(0);

                String[] split = line.split(Constants.SPACE_REGEX);

                if(line.contains(Constants.PASS)) {
                    game.setLastMove(new Stone(Color.getColor(split[1]), new Position(0, 0)));
                }
                else {
                    String lineLetter = split[2].substring(0,1);

                    int columnNumber = letterMapping.get(lineLetter);
                    int lineNumber = Integer.parseInt(split[2].substring(1));

                    game.setLastMove(new Stone(Color.getColor(split[1]), new Position(lineNumber, columnNumber)));
                }
            }
        });
    }

    private void parseCapturedStones(String text) {
        parsingAction(text, Constants.CAPTURES_STONES, new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String strFound = match.group(0);
                String stones = strFound.replaceAll(Constants.CAPTURES_STONES_2, Constants.NOTHING).replaceAll(" stones", Constants.NOTHING);

                if (strFound.contains(Constants.WHITE.toUpperCase())) {
                    game.setWhiteCapture(Integer.parseInt(stones));
                } else if (strFound.contains(Constants.BLACK.toUpperCase())) {
                    game.setBlackCapture(Integer.parseInt(stones));
                }
            }
        });
    }

    private String removeCapturedStones(String text) {
        text = text.replaceAll(Constants.CAPTURES_STONES,"");
        return text;
    }

    @Override
    protected boolean parseInvalidCommand(String text) {
        if(text.contains("? unknown command") || text.contains("? invalid color or coordinate") ) {
            game.setInvalidCommand(true);
            game.setInvalidMessage("Unknown command");

            return true;
        }

        game.setInvalidCommand(false);
        return false;
    }

    @Override
    protected boolean parseIllegalMove(String text) {
        if(text.contains("? illegal move")) {
            game.setInvalidCommand(true);
            game.setInvalidMessage("Illegal move");

            return true;
        }

        game.setInvalidCommand(false);
        return false;
    }

    @Override
    protected void parseTheResult(String text) {
        if(text.contains(Constants.WHITE_CORE_REGEX)) {
            String[] s = text.split(Constants.PLUS_REGEX);
            game.setScore(s[1]);
            game.setWinner(Color.WHITE);
        }

        if(text.contains(Constants.BLACK_SCORE_REGEX)) {
            String[] s = text.split(Constants.PLUS_REGEX);
            game.setScore(s[1]);
            game.setWinner(Color.BLACK);
        }
    }

}