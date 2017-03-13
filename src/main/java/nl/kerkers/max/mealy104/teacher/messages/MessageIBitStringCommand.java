package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeBinaryStateInformation;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIBitStringCommand extends MessageI {

	public MessageIBitStringCommand() {
		super(TypeId.C_BO_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeBinaryStateInformation(5));
	}

}
