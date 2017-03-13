package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.*;

import java.util.List;

import static org.openmuc.j60870.CauseOfTransmission.FILE_TRANSFER;
import static org.openmuc.j60870.TypeId.F_DR_TA_1;

/**
 * @author Max Kerkers
 */
public class MessageIDirectory extends MessageI {

	public MessageIDirectory() {
		super(F_DR_TA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		InformationElement[][] directory = new InformationElement[][]{};
		InformationObject object = new InformationObject(1, directory);

		return send(FILE_TRANSFER, new InformationObject[]{object});
	}
}
