package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.TypeId;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.REQUEST;

/**
 * @author Max Kerkers
 */
public class MessageIRead extends MessageI {

	public MessageIRead() {
		super(TypeId.C_RD_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(REQUEST);
	}

}
