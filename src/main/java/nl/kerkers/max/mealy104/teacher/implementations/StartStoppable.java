package nl.kerkers.max.mealy104.teacher.implementations;

import java.io.IOException;

/**
 * @author Max Kerkers
 */
public interface StartStoppable {

	class Default implements StartStoppable {
		public void start() {}
		public void stop() {}
	}

	void start() throws IOException;

	void stop();

	default void restart() throws IOException {
		stop();
		start();
	}

}
