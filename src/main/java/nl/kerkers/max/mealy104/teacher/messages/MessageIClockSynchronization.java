package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeTime56;
import org.openmuc.j60870.TypeId;

import java.util.Date;
import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIClockSynchronization extends MessageI {

	public MessageIClockSynchronization() {
		super(TypeId.C_CS_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeTime56(new Date().getTime()));
	}

}
