package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_SR_NA_1;

/**
 * @author Max Kerkers
 */
public class MessageISectionReady extends MessageI {

	public MessageISectionReady() {
		super(F_SR_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(0);
		IeNameOfSection nameOfSection = new IeNameOfSection(0);
		IeLengthOfFileOrSection lengthOfSection = new IeLengthOfFileOrSection(16777215);
		IeSectionReadyQualifier qualifier = new IeSectionReadyQualifier(0, false);

		return send(FILE_TRANSFER, nameOfFile, nameOfSection, lengthOfSection, qualifier);
	}
}
