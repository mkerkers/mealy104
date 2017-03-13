package nl.kerkers.max.mealy104.checker;

import net.automatalib.automata.transout.impl.compact.CompactMealy;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.words.Alphabet;
import nl.kerkers.max.mealy104.learner.Iec104Words;

import java.util.ArrayList;
import java.util.List;

import static nl.kerkers.max.mealy104.checker.Iec104Standard.Outputs.*;
import static nl.kerkers.max.mealy104.checker.Iec104Standard.States.*;
import static nl.kerkers.max.mealy104.learner.Iec104Words.*;

/**
 * @author Max Kerkers
 */
public class Iec104Standard extends Checker<Iec104Words> {

	private static final Alphabet<Iec104Words> ALPHABET = Iec104Words.toAlphabet();

	public Iec104Standard(Alphabet<Iec104Words> alphabet) {
		super(buildMealy(), alphabet);
	}

	enum States {
		STOPPED,
		STARTED,
		UNCONFIRMED_STARTED,
		UNCONFIRMED_STOPPED,
		TERMINATED
	}

	class Outputs {
		static final String OUT_NONE = "-";
		static final String OUT_STARTDT_CON = "U[STARTDT]";
		static final String OUT_STOPDT_CON = "U[STOPDT]";
		static final String OUT_TESTFR_CON = "U[TESTFR]";
		static final String OUT_I = "I";
		static final String OUT_ERROR = "ERROR";
	}

	public static CompactMealy<Iec104Words, String> buildMealy() {
		return AutomatonBuilders.<Iec104Words, String>newMealy(ALPHABET)
				.from(STOPPED)
				.on(U_STARTDT)						.withOutput(OUT_STARTDT_CON).to(STARTED)
				.on(U_STOPDT)						.withOutput(OUT_STOPDT_CON)	.loop()
				.on(U_TESTFR)						.withOutput(OUT_TESTFR_CON)	.loop()
				.on(S)								.withOutput(OUT_ERROR)		.to(TERMINATED)
				.on(I_C_SC_NA_1, getOtherInputsI())	.withOutput(OUT_ERROR)		.to(TERMINATED)

				.from(STARTED)
				.on(U_STARTDT)						.withOutput(OUT_STARTDT_CON).loop()
				.on(U_STOPDT)						.withOutput(OUT_STOPDT_CON)	.to(STOPPED)
				.on(U_TESTFR)						.withOutput(OUT_TESTFR_CON)	.loop()
				.on(S)								.withOutput(OUT_NONE)		.loop()
				.on(I_C_SC_NA_1, getOtherInputsI())	.withOutput(OUT_I)			.to(UNCONFIRMED_STARTED)

				.from(UNCONFIRMED_STARTED)
				.on(U_STARTDT)						.withOutput(OUT_STARTDT_CON).loop()
				.on(U_STOPDT)						.withOutput(OUT_NONE)		.to(UNCONFIRMED_STOPPED)
				.on(U_TESTFR)						.withOutput(OUT_TESTFR_CON)	.loop()
				.on(S)								.withOutput(OUT_NONE)		.to(STARTED)
				.on(I_C_SC_NA_1, getOtherInputsI())	.withOutput(OUT_I)			.loop()

				.from(UNCONFIRMED_STOPPED)
				.on(U_STARTDT)						.withOutput(OUT_NONE)		.loop()
				.on(U_STOPDT)						.withOutput(OUT_NONE)		.loop()
				.on(U_TESTFR)						.withOutput(OUT_TESTFR_CON)	.loop()
				.on(S)								.withOutput(OUT_STOPDT_CON)	.to(STOPPED)
				.on(I_C_SC_NA_1, getOtherInputsI())	.withOutput(OUT_ERROR)		.to(TERMINATED)

				.from(TERMINATED)
				.on(U_STARTDT)						.withOutput(OUT_ERROR)		.loop()
				.on(U_STOPDT)						.withOutput(OUT_ERROR)		.loop()
				.on(U_TESTFR)						.withOutput(OUT_ERROR)		.loop()
				.on(S)								.withOutput(OUT_ERROR)		.loop()
				.on(I_C_SC_NA_1, getOtherInputsI())	.withOutput(OUT_ERROR)		.loop()

				.withInitial(STOPPED)
				.create();
	}

	private static Iec104Words[] getOtherInputsI() {
		return alphabetExcept(U_STARTDT, U_STOPDT, U_TESTFR, S, I_C_SC_NA_1);
	}

	private static Iec104Words[] alphabetExcept(Iec104Words... words) {
		List<Iec104Words> wordList = new ArrayList<>(ALPHABET);
		for (Iec104Words word : words) {
			wordList.remove(word);
		}
		return wordList.toArray(new Iec104Words[0]);
	}

}
