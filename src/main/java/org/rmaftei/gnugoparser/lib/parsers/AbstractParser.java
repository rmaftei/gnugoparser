package org.rmaftei.gnugoparser.lib.parsers;

import org.rmaftei.gnugoparser.lib.model.Color;
import org.rmaftei.gnugoparser.lib.model.Game;
import org.rmaftei.gnugoparser.lib.model.Position;
import org.rmaftei.gnugoparser.lib.model.Stone;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractParser {
    protected final static Map<String, Integer> letterMapping = new HashMap<String, Integer>();

    static {
        letterMapping.put("A", 1);
        letterMapping.put("B", 2);
        letterMapping.put("C", 3);
        letterMapping.put("D", 4);
        letterMapping.put("E", 5);
        letterMapping.put("F", 6);
        letterMapping.put("G", 7);
        letterMapping.put("H", 8);
        letterMapping.put("J", 9);
        letterMapping.put("K", 10);
        letterMapping.put("L", 11);
        letterMapping.put("M", 12);
        letterMapping.put("N", 13);
        letterMapping.put("O", 14);
        letterMapping.put("P", 15);
        letterMapping.put("Q", 16);
        letterMapping.put("R", 17);
        letterMapping.put("S", 18);
        letterMapping.put("T", 19);
    }

    protected Game game = null;

    protected void validateInputAndCreateGame(String text) throws GnuGOParseException {
        if(text == null || text.trim().length() == 0) {
            throw new GnuGOParseException(Constants.EMPTY_INPUT_ERR);
        }

        game = new Game();
    }

    protected void parseBoard(String text) {
        parsingAction(text, Constants.LINE_WITH_STONES, new ParseAction() {
            @Override
            public void execute(Matcher match) {
                String line = match.group(0).trim();

                try {
                    Integer.parseInt(String.valueOf(line.charAt(0)));
                }
                catch(NumberFormatException ex) {
                    return;
                }

                line = line.replace("(", Constants.SPACE).replace(")", Constants.SPACE);

                String[] stones = line.split(Constants.SPACE_REGEX);
                int lineNumber = Integer.parseInt(stones[0]);
                for (int columnNumber = 1; columnNumber < stones.length; columnNumber++) {
                    if (stones[columnNumber].equals(Constants.BLACK_MARK)) {
                        game.playStone(new Stone(Color.BLACK, new Position(lineNumber, columnNumber)));
                    } else if (stones[columnNumber].equals(Constants.WHITE_MARK)) {
                        game.playStone(new Stone(Color.WHITE, new Position(lineNumber, columnNumber)));
                    }
                }
            }
        });
    }

    protected void parsingAction(String text, String regex, ParseAction parseAction) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(text);

        while (match.find()) {
            parseAction.execute(match);
        }
    }

    abstract protected boolean parseInvalidCommand(String text);

    abstract protected boolean parseIllegalMove(String text);

    abstract protected void parseTheResult(String text);

    protected void checkAndSetInvalidMove(String text) {
        if(!parseInvalidCommand(text)) {
            parseIllegalMove(text);
        }
    }
}
