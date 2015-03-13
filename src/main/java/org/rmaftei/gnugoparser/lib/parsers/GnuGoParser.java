package org.rmaftei.gnugoparser.lib.parsers;

import org.rmaftei.gnugoparser.lib.model.Game;

/**
 * The interface for the GnuGO parsers.
 * <hr>
 * 
 * @author rmaftei
 * 
 */
public interface GnuGoParser {

    Game parse(String text) throws GnuGOParseException;

}
