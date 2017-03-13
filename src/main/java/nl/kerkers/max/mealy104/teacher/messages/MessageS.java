package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.APdu.APCI_TYPE;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageS extends Message {

	public MessageS() {
		this.receiveSeqNum = 0;
	}

	public MessageS(int receiveSeqNum) {
		this.receiveSeqNum = receiveSeqNum;
	}

	@Override
	public List<APdu> execute() throws Exception {
		APdu aPdu = new APdu(0, receiveSeqNum, APCI_TYPE.S_FORMAT, null);

		return send(aPdu);
	}

	@Override
	public String toString() {
//		return "S(" + receiveSeqNum + ")";
		return "S";
	}
}
