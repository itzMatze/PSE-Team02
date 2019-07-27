package kit.edu.mensameet.server.model;

/**
 * This class represents the mensadata which contains all meal lines
 *
 */
public class MensaData {
	private Line[] lines;

	public MensaData(Line[] lines) {
		this.lines = lines;
	}

	/**
	 * 
	 * @return an array of all lines
	 */
	public Line[] getLines() {
		return lines;
	}

	/**
	 * 
	 * @param lines is an array of all lines with all their food of the current day
	 */
	public void setLines(Line[] lines) {
		this.lines = lines;
	}
}