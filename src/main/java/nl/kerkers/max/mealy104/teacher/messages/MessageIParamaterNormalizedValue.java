package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeNormalizedValue;
import org.openmuc.j60870.IeQualifierOfParameterOfMeasuredValues;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIParamaterNormalizedValue extends MessageI {

	public MessageIParamaterNormalizedValue() {
		super(TypeId.P_ME_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeNormalizedValue(32767), new IeQualifierOfParameterOfMeasuredValues(1, true, false));
	}
}
