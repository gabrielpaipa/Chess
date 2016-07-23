

public class Chess {

	public static void main(String[] args) {
		new Chess().run();
	}

	/** Logical model of the game. */
	private ChessModel model;

	public Chess() {
		model = new ChessModel();
	}


	/** Plays the game. */
	public void run() {
		StdDraw.setScale(-0.5,  8.5);
		while (!model.kingdied()) {
			draw();
			handleCommand();
		}
		draw();
	}

	/** Draws the current state of the game. */
	public void draw() {
		StdDraw.clear(StdDraw.GRAY);
		int w = 9;
		// Draw board
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0, 8, 0,8);
		// Draw lines
		StdDraw.setPenColor();
		for (int i = 0; i < w; i++) {
			StdDraw.line(0, i, w - 1, i);
			StdDraw.line(i, 0, i, w - 1);
		}
		// Draw colored squares
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if ((i+j)%2==0) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledSquare(i+0.5, j+0.5 , 0.5);
				}
			}
		}
		// What's up
		StdDraw.setPenColor(StdDraw.BLACK);
		if (model.kingdied()) {
			StdDraw.text((w / 2.0) - 0.8, w, "Game over.");
		} else if (model.getTurn()==-1) {
			if(model.getActivated()){StdDraw.text((w / 2.0) - 0.5, w-0.8, "White move. Click on a piece you want to move.");}
			else{StdDraw.text((w / 2.0) - 0.5, w-0.8, "White move. Click on a legal destination.");}
		} else {
			if(model.getActivated()){StdDraw.text((w / 2.0) - 0.5, w-0.8, "Red move. Click on a piece you want to move.");}
			else{StdDraw.text((w / 2.0) - 0.5, w-0.8, "Red move. Click on a legal destination.");}
		}
		//Draw the piece itself
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if ((i+j)%2==0) {
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledSquare(i+0.5, j+0.5 , 0.5);
				}
			}
		}

		//Draw the pieces now!
		String s = "";
		StdDraw.setPenColor(StdDraw.WHITE);
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if(model.whatSide(new Coordinates(i,j))==1){
					StdDraw.setPenColor(StdDraw.WHITE);
					s = model.getState()[i][j];
					StdDraw.text(i+0.5, j+0.5, s);}
				else if(model.whatSide(new Coordinates(i,j))==-1)
				{
					StdDraw.setPenColor(StdDraw.RED);
					s = model.getState()[i][j];
					StdDraw.text(i+0.5, j+0.5, s);}
				s = model.getState()[i][j];
				if(s==null){s="";}
				StdDraw.text(i+0.5, j+0.5, s);

			}
		}


	}

	/** Handles a mouse click. */
	public void handleCommand() {
		while(!StdDraw.mousePressed()){}
		if (StdDraw.mousePressed()) {}
	}

}
