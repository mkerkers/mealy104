package nl.kerkers.max.mealy104.teacher.implementations;

import java.awt.*;
import java.io.IOException;

/**
 * @author Max Kerkers
 */
public class AxonTest extends MouseRestarter implements StartStoppable {

	public AxonTest(Integer... args) throws AWTException {
		super(args[0], args[1]);
	}

	@Override
	public void start() throws IOException {
		checkMouseLocation();
		if (robot.getPixelColor(posX + 100, posY - 5).getRGB() != 0xFFFFFFFF) {
			throw new IOException("Axon Test was not recognised");
		}
		rightClick(posX, posY);
		leftClick(posX + 35, posY + 10);
		robot.delay(500);
	}

	@Override
	public void stop() {
		checkMouseLocation();
		rightClick(posX, posY);
		leftClick(posX + 35, posY + 35);
		robot.mouseMove(posX + 35, posY + 10);
		if (robot.getPixelColor(posX + 70, posY + 10).getRGB() != 0xFFC4E1FF) {
			robot.delay(1000);
			stop();
		}
	}
}
