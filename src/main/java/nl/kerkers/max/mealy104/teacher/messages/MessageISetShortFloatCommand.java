package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeQualifierOfSetPointCommand;
import org.openmuc.j60870.IeShortFloat;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageISetShortFloatCommand extends MessageI {

	public MessageISetShortFloatCommand() {
		super(TypeId.C_SE_NC_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeShortFloat(5), new IeQualifierOfSetPointCommand(0, false));
	}

}
