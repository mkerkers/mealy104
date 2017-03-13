package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_LS_NA_1;

/**
 * @author Max Kerkers
 */
public class MessageILastSection extends MessageI {

	public MessageILastSection() {
		super(F_LS_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(0);
		IeNameOfSection nameOfSection = new IeNameOfSection(0);
		IeLastSectionOrSegmentQualifier qualifier = new IeLastSectionOrSegmentQualifier(1);
		IeChecksum checksum = new IeChecksum(255);

		return send(FILE_TRANSFER, nameOfFile, nameOfSection, qualifier, checksum);
	}
}
