package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIInterrogation extends MessageI {

	public MessageIInterrogation() {
		super(TypeId.C_IC_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeQualifierOfInterrogation(20));
	}

}
