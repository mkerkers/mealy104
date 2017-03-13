package nl.kerkers.max.mealy104.checker;

import net.automatalib.automata.transout.impl.compact.CompactMealy;
import net.automatalib.util.automata.Automata;
import net.automatalib.words.Alphabet;

/**
 * @author Max Kerkers
 */
public class Checker<T> {

	private CompactMealy<T, String> standard;
	private Alphabet<T> alphabet;

	public Checker(CompactMealy<T, String> standard, Alphabet<T> alphabet) {
		this.standard = standard;
		this.alphabet = alphabet;
	}

	public boolean testEquivalence(CompactMealy<T, String> mealy) {
		return Automata.testEquivalence(standard, mealy, alphabet);
	}

}
