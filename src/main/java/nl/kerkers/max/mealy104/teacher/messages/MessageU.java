package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.APdu.APCI_TYPE;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageU extends Message {

	public enum TYPE {
		STARTDT (APCI_TYPE.STARTDT_ACT),
		STOPDT (APCI_TYPE.STOPDT_ACT),
		TESTFR (APCI_TYPE.TESTFR_ACT);

		private final APCI_TYPE apciType;

		TYPE(APCI_TYPE apciType) {
			this.apciType = apciType;
		}

		public APCI_TYPE toApciType() {
			return this.apciType;
		}
	}

	private TYPE type;

	public MessageU(TYPE type) {
		this.type = type;
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(type.toApciType());
	}

	@Override
	public String toString() {
		return "U[" + type.toString() + "]";
	}
}
