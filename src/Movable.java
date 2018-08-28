import java.awt.*;

public abstract class Movable {

	public Color c;
	public String s;

	public Movable(Color c, String s) {
		this.c = c;
		this.s = s;
	}

	public String getName() {
		return s;
	}

}
