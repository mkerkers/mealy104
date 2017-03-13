package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeQualifierOfSetPointCommand;
import org.openmuc.j60870.IeScaledValue;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageISetScaledValueCommand extends MessageI {

	public MessageISetScaledValueCommand() {
		super(TypeId.C_SE_NB_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeScaledValue(5), new IeQualifierOfSetPointCommand(0, false));
	}

}
