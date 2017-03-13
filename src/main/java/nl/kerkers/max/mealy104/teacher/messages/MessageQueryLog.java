package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeNameOfFile;
import org.openmuc.j60870.IeTime56;

import java.util.Date;
import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_SC_NB_1;

/**
 * @author Max Kerkers
 */
public class MessageQueryLog extends MessageI {

	public MessageQueryLog() {
		super(F_SC_NB_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		IeNameOfFile nameOfFile = new IeNameOfFile(0);
		IeTime56 rangeStartTime = new IeTime56(new Date(0).getTime());
		IeTime56 rangeEndTime = new IeTime56(new Date().getTime());

		return send(FILE_TRANSFER, nameOfFile, rangeStartTime, rangeEndTime);
	}
}
