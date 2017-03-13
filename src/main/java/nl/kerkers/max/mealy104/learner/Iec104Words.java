package nl.kerkers.max.mealy104.learner;

import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.Alphabets;
import net.automatalib.words.impl.EnumAlphabet;
import nl.kerkers.max.mealy104.teacher.messages.*;
import org.openmuc.j60870.APdu;
import org.openmuc.j60870.TypeId;

import java.util.List;

import static nl.kerkers.max.mealy104.teacher.messages.MessageU.TYPE.*;

/**
 * @author Max Kerkers
 */
public enum Iec104Words {

	/* = U-Format messages = */
	U_STARTDT (new MessageU(STARTDT)),
	U_STOPDT (new MessageU(STOPDT)),
	U_TESTFR (new MessageU(TESTFR)),

	/* = S-Format message  = */
	S (new MessageS()),

	/* = I-Format messages = */
	/*  Process information  */
	I_C_SC_NA_1 (new MessageISingleCommand()),
	I_C_DC_NA_1 (new MessageIDoubleCommand()),
	I_C_RC_NA_1 (new MessageIRegulatingStepCommand()),
	I_C_SE_NA_1 (new MessageISetNormalizedValueCommand()),
	I_C_SE_NB_1 (new MessageISetScaledValueCommand()),
	I_C_SE_NC_1 (new MessageISetShortFloatCommand()),
	I_C_BO_NA_1 (new MessageIBitStringCommand()),

	/*  System information   */
	I_C_IC_NA_1 (new MessageIInterrogation()),
	I_C_CI_NA_1 (new MessageICounterInterrogation()),
	I_C_RD_NA_1 (new MessageIRead()),
	I_C_CS_NA_1 (new MessageIClockSynchronization()),
	I_C_RP_NA_1 (new MessageIResetProcess()),
	I_C_TS_TA_1 (new MessageITimedTest()),

	/*  Parameter            */
	I_P_ME_NA_1 (new MessageIParamaterNormalizedValue()),
	I_P_ME_NB_1 (new MessageIParamaterScaledValue()),
	I_P_ME_NC_1 (new MessageIParamaterShortFloat()),
	I_P_AC_NA_1 (new MessageIParameterActivation()),

	/*  File Transfer        */
	I_F_FR_NA_1 (new MessageIFileReady()),
	I_F_SR_NA_1 (new MessageISectionReady()),
	I_F_SC_NA_1 (new MessageISelectCall()),
	I_F_LS_NA_1 (new MessageILastSection()),
	I_F_AF_NA_1 (new MessageIAckFile()),
	I_F_SG_NA_1 (new MessageISegment()),
	I_F_DR_NA_1 (new MessageIDirectory()),
	I_F_SC_NB_1 (new MessageQueryLog()),

	/* Undefined */
	I_NULL (new MessageI(TypeId.UNDEFINED_0) {
		@Override
		public List<APdu> execute() throws Exception {
			return send();
		}

		@Override
		public String toString() {
			return "I[0]";
		}
	}),

	L_MAX (new Message() {
		@Override
		protected List<APdu> execute() throws Exception {
			return send(APdu.APCI_TYPE.L_MAX);
		}

		@Override
		public String toString() {
			return "L_MAX";
		}
	});

	private Message mapping;

	Iec104Words(Message mapping) {
		this.mapping = mapping;
	}

	public Message getMapping() {
		return mapping;
	}

	@Override
	public String toString() {
		return mapping.toString();
	}

	public static Alphabet<Iec104Words> toAlphabet() {
		return new EnumAlphabet<>(Iec104Words.class, false);
	}

	public static Alphabet<Iec104Words> toAlphabet(Iec104Words... words) {
		return Alphabets.fromArray(words);
	}

}
