package nl.kerkers.max.mealy104.teacher.messages;

import nl.kerkers.max.mealy104.Configuration;
import org.openmuc.j60870.*;
import org.openmuc.j60870.APdu.APCI_TYPE;

import java.io.IOException;
import java.util.List;

/**
 * @author Max Kerkers
 */
public abstract class MessageI extends Message {

	private TypeId aSduType;
	private int ioa = 0;

	protected MessageI(TypeId aSduType) {
		this.aSduType = aSduType;
	}

	protected MessageI(TypeId aSduType, int ioa) {
		this.aSduType = aSduType;
		this.ioa = ioa;
	}

	public abstract List<APdu> execute() throws Exception;

	protected List<APdu> send(InformationElement... elements) throws IOException, InterruptedException {
		return send(CauseOfTransmission.ACTIVATION, elements);
	}

	protected List<APdu> send(InformationObject[] objects) throws IOException, InterruptedException {
		return send(CauseOfTransmission.ACTIVATION, objects);
	}

	protected List<APdu> send(CauseOfTransmission cot, InformationElement... elements) throws IOException, InterruptedException {
		InformationObject object = new InformationObject(ioa, new InformationElement[][]{elements});
		return send(cot, new InformationObject[]{object});
	}

	protected List<APdu> send(CauseOfTransmission cot, InformationObject[] objects) throws IOException, InterruptedException {
		ASdu aSdu = new ASdu(aSduType, false, cot, false, false, 0, Configuration.COMMONADDRESS, objects);
		return send(new APdu(sendSeqNum, receiveSeqNum, APCI_TYPE.I_FORMAT, aSdu));
	}

	@Override
	public String toString() {
		return "I[" + aSduType.toString().replace("_1", "") + "]";
	}

}
