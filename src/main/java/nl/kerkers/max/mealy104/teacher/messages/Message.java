package nl.kerkers.max.mealy104.teacher.messages;

import de.learnlib.mapper.api.ContextExecutableInput;
import nl.kerkers.max.mealy104.teacher.Iec104Teacher;
import org.openmuc.j60870.APdu;
import org.openmuc.j60870.APdu.APCI_TYPE;
import org.openmuc.j60870.ASdu;

import java.io.IOException;
import java.util.List;

/**
 * @author Max Kerkers
 */
public abstract class Message implements ContextExecutableInput<List<APdu>, Iec104Teacher> {

	protected int receiveSeqNum = 0;
	protected int sendSeqNum = 0;

	private Iec104Teacher sut;

	public void setReceiveSeqNum(int receiveSeqNum) {
		this.receiveSeqNum = receiveSeqNum;
	}

	public void setSendSeqNum(int sendSeqNum) {
		this.sendSeqNum = sendSeqNum;
	}

	public Message withReceiveSeqNum(int receiveSeqNum) {
		this.receiveSeqNum = receiveSeqNum;
		return this;
	}

	public Message withSendSeqNum(int sendSeqNum) {
		this.sendSeqNum = sendSeqNum;
		return this;
	}

	public List<APdu> execute(Iec104Teacher sut) throws Exception {
		this.sut = sut;
		return execute();
	}

	protected abstract List<APdu> execute() throws Exception;

	protected List<APdu> send(APCI_TYPE apciType) throws IOException, InterruptedException {
		return send(new APdu(receiveSeqNum, sendSeqNum, apciType, null));
	}

	protected List<APdu> send(APCI_TYPE apciType, ASdu aSdu) throws IOException, InterruptedException {
		return send(new APdu(receiveSeqNum, sendSeqNum, apciType, aSdu));
	}

	protected List<APdu> send(APdu aPdu) throws IOException, InterruptedException {
		return sut.send(aPdu);
	}

}
