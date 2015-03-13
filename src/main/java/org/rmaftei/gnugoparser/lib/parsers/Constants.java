package org.rmaftei.gnugoparser.lib.parsers;

/**
 * Created by rmaftei
 */
public interface Constants {

    String WHITE_CORE_REGEX = "W+";

    String BLACK_SCORE_REGEX = "B+";

    String GAME_HEADER = "(Board Size:|Handicap|Komi:|Move Number:|To Move:|Computer player:)\\s*\\w+(\\.[0-9]*)?";

    String CAPTURED_STONES_REGEX = "(White|Black)( \\(X\\)| \\(O\\)) has captured \\d+ pieces";

    String CAPTURED_STONES_REGEX_2 = "(White|Black)( \\(X\\)| \\(O\\)) has captured ";

    String LAST_MOVE_REGEX = "Last move: (Black|White) (\\w[0-9]+|PASS)";

    String PASS = "PASS";

    String RESULT_REGEX = "Result:\\s\\w\\+([0-9]+.[0-9]+|Resign)";

    String BOARD_SIZE = "Board Size:";

    String HANDICAP = "Handicap";

    String KOMI = "Komi:";

    String MOVE_NUMBER = "Move Number:";

    String TO_MOVE = "To Move:";

    String COMPUTER_PLAYER = "Computer player:";

    String SPACE_REGEX = "\\s";

    String PLUS_REGEX = "\\+";

    String BLACK_MARK = "X";

    String WHITE_MARK = "O";

    String LINE_WITH_STONES = "(\\s?.+X\\s?.+|\\s?.+O\\s?.+)";

    String WHITE = "White";

    String BLACK = "Black";

    String INVALID_COMMAND = "Invalid command:\\s(\\w+)";

    String ILLEGAL_MOVE = "Illegal move:\\s(\\w+)";

    String NOTHING = "";

    String SPACE = " ";

    String CAPTURES_STONES = "(BLACK|WHITE) (\\(X\\)|\\(O\\)) has captured \\d+ stones";

    String CAPTURES_STONES_2 = "(BLACK|WHITE) (\\(X\\)|\\(O\\)) has captured ";

    String EMPTY_INPUT_ERR = "The input is empty";
}
