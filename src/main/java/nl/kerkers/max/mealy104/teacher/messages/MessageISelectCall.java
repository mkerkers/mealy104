package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_SC_NA_1;

/**
 * @author Max Kerkers
 */
public class MessageISelectCall extends MessageI {

	public MessageISelectCall() {
		super(F_SC_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(0);
		IeNameOfSection nameOfSection = new IeNameOfSection(0);
		IeSectionReadyQualifier qualifier = new IeSectionReadyQualifier(0, false);

		return send(FILE_TRANSFER, nameOfFile, nameOfSection, qualifier);
	}
}
