package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIParameterActivation extends MessageI {

	public MessageIParameterActivation() {
		super(TypeId.P_AC_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeQualifierOfParameterActivation(20));
	}
}
