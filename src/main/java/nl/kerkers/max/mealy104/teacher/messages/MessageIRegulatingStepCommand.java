package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeRegulatingStepCommand;
import org.openmuc.j60870.IeRegulatingStepCommand.StepCommandState;
import org.openmuc.j60870.TypeId;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageIRegulatingStepCommand extends MessageI {

	private StepCommandState state;

	public MessageIRegulatingStepCommand() {
		this(StepCommandState.NEXT_STEP_HIGHER);
	}

	public MessageIRegulatingStepCommand(StepCommandState state) {
		super(TypeId.C_RC_NA_1);
		this.state = state;
	}

	@Override
	public List<APdu> execute() throws Exception {
		return send(new IeRegulatingStepCommand(state, 0, false));
	}

}
