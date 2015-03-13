package org.rmaftei.gnugoparser;

import org.rmaftei.gnugoparser.lib.parsers.GnuGoParser;
import org.rmaftei.gnugoparser.lib.parsers.impl.AsciiModeParser;
import org.rmaftei.gnugoparser.lib.parsers.impl.GTPModeParser;

/**
 * Enum for Gnu GO modes parsers.
 * <hr>
 * 
 * @author rmaftei
 * 
 */
public enum GnuGOModes {
	ASCII {
		public GnuGoParser createParser() {
			return new AsciiModeParser();
		}
	}, 
	GTP {
		public GnuGoParser createParser() {
            return new GTPModeParser();
		}
	}, 
	GMP {
		public GnuGoParser createParser() {
			throw new UnsupportedOperationException("This mode is to old to support");
		}
	};

	public abstract GnuGoParser createParser();

}
