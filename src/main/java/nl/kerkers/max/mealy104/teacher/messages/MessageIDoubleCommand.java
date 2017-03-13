package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.CauseOfTransmission;
import org.openmuc.j60870.IeDoubleCommand;
import org.openmuc.j60870.IeDoubleCommand.DoubleCommandState;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIDoubleCommand extends MessageI {

	private DoubleCommandState state;

	public MessageIDoubleCommand() {
		this(DoubleCommandState.ON);
	}

	public MessageIDoubleCommand(DoubleCommandState state) {
		super(TypeId.C_DC_NA_1);
		this.state = state;
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeDoubleCommand doubleCommand = new IeDoubleCommand(state, 0, false);

		if (state == DoubleCommandState.OFF) {
			return send(CauseOfTransmission.DEACTIVATION, doubleCommand);
		}

		return send(doubleCommand);
	}

}
