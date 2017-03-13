package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIParamaterShortFloat extends MessageI {

	public MessageIParamaterShortFloat() {
		super(TypeId.P_ME_NC_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeShortFloat((float) 1.001), new IeQualifierOfParameterOfMeasuredValues(1, true, false));
	}
}
