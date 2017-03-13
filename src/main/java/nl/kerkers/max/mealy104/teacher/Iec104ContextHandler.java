package nl.kerkers.max.mealy104.teacher;

import de.learnlib.mapper.ContextExecutableInputSUL;
import nl.kerkers.max.mealy104.Configuration;
import nl.kerkers.max.mealy104.teacher.implementations.StartStoppable;
import nl.kerkers.max.mealy104.teacher.messages.MessageS;
import nl.kerkers.max.mealy104.teacher.messages.MessageU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Max Kerkers
 */
public class Iec104ContextHandler implements ContextExecutableInputSUL.ContextHandler<Iec104Teacher> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private StartStoppable impl = Configuration.SIMULATOR;

	@Override
	public Iec104Teacher createContext() {
		while (true) {
			try {
				impl.start();

				return new Iec104Teacher();
			} catch (IOException e) {
				logger.warn("Could not set up SUT. Press enter to retry..");
				new Scanner(System.in).nextLine();
				impl.stop();
				logger.info("Continuing..");
			}
		}
	}

	@Override
	public void disposeContext(Iec104Teacher context) {
		context.close();
		impl.stop();
		if (Configuration.FLUSH) {
			flushEventBuffer();
		}
	}

	private void flushEventBuffer() {
		Iec104Teacher sut = null;
		try {
			impl.start();
			sut = new Iec104Teacher();
			if (new MessageU(MessageU.TYPE.STARTDT).execute(sut).size() > 1) {
				do {} while (new MessageS().execute(sut).size() > 0);
			}
		} catch (Exception e) {
			logger.warn("Exception during reset (flushing event buffer potentially failed)");
		} finally {
			if (sut != null) {
				sut.close();
			}
			impl.stop();
		}
	}
}
