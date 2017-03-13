package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeFileReadyQualifier;
import org.openmuc.j60870.IeLengthOfFileOrSection;
import org.openmuc.j60870.IeNameOfFile;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_FR_NA_1;

/**
 * @author Max Kerkers
 */
public class MessageIFileReady extends MessageI {

	public MessageIFileReady() {
		super(F_FR_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(0);
		IeLengthOfFileOrSection lengthOfFile = new IeLengthOfFileOrSection(16777215);
		IeFileReadyQualifier qualifier = new IeFileReadyQualifier(0, false);

		return send(FILE_TRANSFER, nameOfFile, lengthOfFile, qualifier);
	}
}
