package behaviour;

import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;

public interface Clickable {

	public boolean checkClick(Point2D aim, GameContainer gc);

}
