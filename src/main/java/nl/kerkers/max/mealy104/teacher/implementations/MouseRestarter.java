package nl.kerkers.max.mealy104.teacher.implementations;

import java.awt.*;
import java.awt.event.InputEvent;

import static java.awt.MouseInfo.getPointerInfo;
import static java.lang.Math.abs;

/**
 * @author Max Kerkers
 */
public class MouseRestarter {
	protected Robot robot;

	protected int posX, posY;

	public MouseRestarter(int x, int y) throws AWTException {
		robot = new Robot();
		robot.setAutoWaitForIdle(true);
		robot.setAutoDelay(50);
		posX = x;
		posY = y;
	}

	protected void leftClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	protected void rightClick(int x, int y) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}

	protected void checkMouseLocation() {
		Point mouse = getMouseLocation();
		while (abs(posX - mouse.x) > 1000) {
			robot.delay(500);
			mouse = getMouseLocation();
		}
	}

	private Point getMouseLocation() {
		Point location = new Point(posX, posY);
		PointerInfo pointerInfo = getPointerInfo();
		if (pointerInfo != null) {
			location = pointerInfo.getLocation();
		}
		return location;
	}

}
