package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIParamaterScaledValue extends MessageI {

	public MessageIParamaterScaledValue() {
		super(TypeId.P_ME_NB_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeScaledValue(32767), new IeQualifierOfParameterOfMeasuredValues(1, true, false));
	}
}
