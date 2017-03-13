package nl.kerkers.max.mealy104.teacher;

import nl.kerkers.max.mealy104.Configuration;
import org.openmuc.j60870.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.openmuc.j60870.APdu.APCI_TYPE.I_FORMAT;
import static org.openmuc.j60870.APdu.APCI_TYPE.S_FORMAT;

/**
 * @author Max Kerkers
 */
public class Iec104Teacher implements Closeable, ConnectionEventListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private volatile Connection clientConnection;

	private volatile int sendSeqNum = 0;
	private volatile int receiveSeqNum = 0;

	private BlockingQueue<APdu> buffer;
	private IOException sendingException;

	public Iec104Teacher() throws IOException {
		ClientConnectionBuilder clientConnectionBuilder = new ClientConnectionBuilder(Configuration.IPADDRESS).setPort(Configuration.PORT);

		clientConnection = clientConnectionBuilder.connect();
	}

	public List<APdu> send(APdu aPdu) throws IOException, InterruptedException {
		buffer = new LinkedBlockingQueue<>();
		sendingException = null;
		clientConnection.setASduListener(this);

		aPdu.setSendSeqNum(sendSeqNum);
		aPdu.setReceiveSeqNum(receiveSeqNum);
		clientConnection.send(aPdu);

		logger.debug("< APdu sent (" + aPdu.getApciType().toString() + ")");

		List<APdu> response = waitForResponse();

		if (sendingException == null) {
			return response;
		}

		throw sendingException;
	}

	private List<APdu> waitForResponse() throws InterruptedException {
		List<APdu> response = new LinkedList<>();

		APdu aPdu = buffer.poll(Configuration.RESPONSETIME, TimeUnit.MILLISECONDS);

		while (aPdu != null) {
			response.add(aPdu);
			try {
				aPdu = buffer.poll(Configuration.RESPONSETIME, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				aPdu = null;
				logger.info("Polling buffer was interrupted");
			}
		}

		return response;
	}

	@Override
	public void close() {
		clientConnection.close();
		if (buffer.size() > 0) {
			throw new RuntimeException("Not all messages were processed.");
		}
	}

	@Override
	public void newASdu(ASdu aSdu) {
		logger.debug("> ASdu received (" + aSdu.getTypeIdentification().toString() + ")");
	}

	@Override
	public void newAPdu(APdu aPdu) {
		logger.debug("> APdu received (" + aPdu.getApciType().toString() + ")");
		try {
			buffer.put(aPdu);
		} catch (InterruptedException e) {
			logger.warn(e.getMessage());
		}
		if (aPdu.getApciType() == I_FORMAT || aPdu.getApciType() == S_FORMAT) {
			sendSeqNum = aPdu.getReceiveSeqNumber();

			if (aPdu.getApciType() == I_FORMAT) {
				receiveSeqNum = (aPdu.getSendSeqNumber() >= receiveSeqNum ? aPdu.getSendSeqNumber() + 1 : receiveSeqNum);
			}
		}
	}

	@Override
	public void connectionClosed(IOException e) {
		sendingException = e;
	}

}
