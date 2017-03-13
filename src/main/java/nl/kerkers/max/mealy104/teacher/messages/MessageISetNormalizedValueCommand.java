package nl.kerkers.max.mealy104.teacher.messages;

import de.learnlib.api.SULException;
import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageISetNormalizedValueCommand extends MessageI {

	public MessageISetNormalizedValueCommand() {
		super(TypeId.C_SE_NA_1);
	}

	@Override
	public List<APdu> execute() throws SULException, Exception {
		return send(new IeNormalizedValue(5), new IeQualifierOfSetPointCommand(0, false));
	}

}
