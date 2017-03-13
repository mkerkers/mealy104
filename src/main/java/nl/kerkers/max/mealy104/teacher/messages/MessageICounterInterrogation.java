package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeQualifierOfCounterInterrogation;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageICounterInterrogation extends MessageI {

	public MessageICounterInterrogation() {
		super(TypeId.C_CI_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeQualifierOfCounterInterrogation(5, 0));
	}

}
