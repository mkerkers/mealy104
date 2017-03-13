package nl.kerkers.max.mealy104.teacher.implementations;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static java.lang.Thread.sleep;

/**
 * @author Max Kerkers
 */
public class Simulator implements StartStoppable {

	private static final String EXECUTABLE = "C:\\Program Files (x86)\\Mitra Software\\IEC 870-5-104 Simulator\\104sim.exe";

	private long startTime;
	private ProcessBuilder processBuilder;
	private Process process;
	private Robot robot;

	public Simulator() throws AWTException, IOException {
		processBuilder = new ProcessBuilder(EXECUTABLE);

		robot = new Robot();
		robot.setAutoWaitForIdle(true);
		robot.setAutoDelay(50);
	}

	@Override
	public void start() throws IOException {
		if (process == null) {
			process = processBuilder.start();
			try {
				sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			startTime = System.currentTimeMillis();
		}
	}

	@Override
	public void stop() {
		if (System.currentTimeMillis() - startTime > (4.5 * 60 * 1000)) {
			process.destroy();
			process = null;
		}
	}
}
