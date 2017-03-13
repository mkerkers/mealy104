package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageISingleCommand extends MessageI {

	private boolean stateOn;

	public MessageISingleCommand() {
		this(true);
	}

	public MessageISingleCommand(boolean stateOn) {
		super(TypeId.C_SC_NA_1);
		this.stateOn = stateOn;
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeSingleCommand singleCommand = new IeSingleCommand(stateOn, 0, false);

		if (stateOn) {
			return send(CauseOfTransmission.ACTIVATION, singleCommand);
		} else {
			return send(CauseOfTransmission.DEACTIVATION, singleCommand);
		}
	}

}
