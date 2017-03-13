package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;

/**
 * @author Max Kerkers
 */
public class MessageISegment extends MessageI {

	public MessageISegment() {
		super(TypeId.F_SG_NA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(5);
		IeNameOfSection nameOfSection = new IeNameOfSection(4);
		IeFileSegment segment = new IeFileSegment(new byte[] {(byte)0xAB, (byte)0xCD}, 0, 2);

		return send(FILE_TRANSFER, nameOfFile, nameOfSection, segment);
	}

}
