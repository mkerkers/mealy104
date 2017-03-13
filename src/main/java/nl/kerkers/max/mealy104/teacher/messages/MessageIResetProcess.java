package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeQualifierOfResetProcessCommand;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIResetProcess extends MessageI {

	public MessageIResetProcess() {
		super(TypeId.C_RP_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeQualifierOfResetProcessCommand(1));
	}

}
