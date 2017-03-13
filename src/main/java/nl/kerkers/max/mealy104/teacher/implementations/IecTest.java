package nl.kerkers.max.mealy104.teacher.implementations;

import java.awt.*;
import java.io.IOException;

/**
 * @author Max Kerkers
 */
public class IecTest extends MouseRestarter implements StartStoppable {

	public IecTest(Integer... args) throws AWTException {
		super(args[0], args[1]);
	}

	@Override
	public void start() throws IOException {
		checkMouseLocation();
		if (robot.getPixelColor(posX, posY + 20).getRGB() != 0xFFF0F0F0) {
			throw new IOException("IEC-Test was not recognised");
		}
		leftClick(posX, posY);
		robot.delay(1000);
	}

	@Override
	public void stop() {
		checkMouseLocation();
		leftClick(posX + 65, posY);
		if (robot.getPixelColor(posX + 300, posY).getRGB() != 0xFFFF00FF) {
			robot.delay(1000);
			stop();
		}
	}

}
