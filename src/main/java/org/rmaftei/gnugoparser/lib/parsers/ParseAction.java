package org.rmaftei.gnugoparser.lib.parsers;

import java.util.regex.Matcher;

/**
 * Created by rmaftei
 */
public interface ParseAction {

    void execute(Matcher match);

}
