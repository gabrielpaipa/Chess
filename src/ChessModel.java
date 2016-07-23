
/** The object that contains all the technical parts of the game. */
public class ChessModel {

	/** How does the board look now? */
	private String[][] state;

	/**
	 * An instance variable (constantly changing) for the possible moves of a
	 * given player!
	 */
	private Boolean[][] moves = new Boolean[8][8];

	/**
	 * Variable storing who's turn is next. White is true and black is false 1
	 * if white (lowercase) and -1 if black (UPPERCASE)
	 */
	private int turn;

	/**Bureaucratic purposes*/
	private boolean activated=true;

	/** Queue of moves. Changes with each "isLegal" method. */
	private Queue<Coordinates> queue;

	/** Queue of moves. Only used when trying to check for checkmate. */
	private Queue<Coordinates> check;

	/** Coordinates of the currently clicked coordinates (the coordinates "in question") */
	private Coordinates inquestion;

	/** Returns the current queue of Coordinates. */
	public Queue<Coordinates> getQueue() {
		return this.queue;
	}

	/**
	 * Sets up a board with pieces in their respective place and sets turn to
	 * white.
	 */
	public ChessModel() {
		this.state = new String[8][8];
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				this.state[i][j] = null;
			}
		}
		turn = -1;
		queue = new Queue<Coordinates>();
		check = new Queue<Coordinates>();
		// Setting Pawns
		for (int i = 0; i < state.length; i++) {
			state[i][1] = "P";
			state[i][6] = "p";
		}
		// Instituting the Monarchy (Queen)
		state[4][0] = "Q";
		state[3][7] = "q";
		// Setting Horses (also known as the mighty Knights-but since they're
		// actually horses we use the letter N)
		state[1][0] = "N";
		state[6][0] = "N";
		state[1][7] = "n";
		state[6][7] = "n";
		// Setting Rooks
		state[0][0] = "R";
		state[0][7] = "r";
		state[7][0] = "R";
		state[7][7] = "r";
		// Instituting the Church State (Bishops)
		state[2][0] = "B";
		state[5][0] = "B";
		state[2][7] = "b";
		state[5][7] = "b";
		// Instituting the Monarchy (King)
		state[4][7] = "k";
		state[3][0] = "K";

	}

	/** Returns the String of the baord in the current state. */
	public String[][] getState() {
		return this.state;
	}

	/**
	 * If the other is not even a piece, but a null, return that they are not
	 * enemies!
	 */
	public boolean areEnemies(Coordinates one, Coordinates other) {
		// If the other is not even a piece, but a null, return that they are
		// not enemies!
		if (isOutofBounds(one) || isOutofBounds(other)) {
			return false;
		}
		if ((whatSide(one) == 0) || (whatSide(other) == 0)) {
			return false;
		}
		int sideofone = whatSide(one);
		int sideofother = whatSide(other);
		if (sideofone != sideofother) {
			return true;
		}
		return false;
	}

	/**
	 * Returns 1 if white (lowercase) and -1 if black (UPPERCASE). Return 0 if
	 * null or out of bounds.
	 */
	public int whatSide(Coordinates loc) {
		if (isOutofBounds(loc)) {
			return 0;
		}
		int x = loc.getX();
		int y = loc.getY();
		if(state[x][y]==null){return 0;}
		if (state[x][y] == "p") {
			return 1;
		}
		if (state[x][y] == "n") {
			return 1;
		}
		if (state[x][y] == "b") {
			return 1;
		}
		if (state[x][y] == "r") {
			return 1;
		}
		if (state[x][y] == "q") {
			return 1;
		}
		if (state[x][y] == "k") {
			return 1;
		}
		if (state[x][y] == "P") {
			return -1;
		}
		if (state[x][y] == "N") {
			return -1;
		}
		if (state[x][y] == "B") {
			return -1;
		}
		if (state[x][y] == "Q") {
			return -1;
		}
		if (state[x][y] == "K") {
			return -1;
		}
		if(state[x][y]=="R"){
			return -1;
		}
		return 0;
	}

	/** Returns true if it has moved. Returns false if it hasn't. */
	public boolean haspawnmoved(Coordinates loc) {
		int y = loc.getY();
		if (isOutofBounds(loc)) {
			return true;
		}
		if (((whatSide(loc) == 1) && y == 6) || ((whatSide(loc) == -1) && y == 1)) {
			return false;
		}
		return true;
	}

	/** Hard move. Regardless of legality */
	public void move(Coordinates loc, Coordinates newloc) {
		String locstring = state[loc.getX()][loc.getY()];
		state[newloc.getX()][newloc.getY()] = locstring;
		state[loc.getX()][loc.getY()] = null;
	}

	/** Legal move. Has to be legal */
	public void legalmove(Coordinates loc, Coordinates newloc) {
		if (isLegal(loc, newloc)) {
			while (!queue.isEmpty()) {
				queue.dequeue();
			}
			String locstring = state[loc.getX()][loc.getY()];
			state[newloc.getX()][newloc.getY()] = locstring;
			state[loc.getX()][loc.getY()] = null;
		}
	}

	/**
	 * Returns whether a particular piece can move to another particular place.
	 */
	public boolean isLegal(Coordinates mypiece, Coordinates clickfield) {
		whatLegal(mypiece);
		Coordinates temp;
		while (!queue.isEmpty()) {
			temp = this.queue.dequeue();
			if (temp.equals(clickfield)) {
				while (!queue.isEmpty()) {
					queue.dequeue();
				}
				return true;
			}
		}
		while (!queue.isEmpty()) {
			queue.dequeue();
		}
		return false;
	}

	/** Returns the string of the currently selected piece. */
	public String whatPiece(Coordinates loc) {
		return this.state[loc.getX()][loc.getY()];
	}

	/** Checks whether a particular square is empty.*/
	public boolean isEmpty(Coordinates loc) {
		if ((state[loc.getX()][loc.getY()] == null) || isOutofBounds(loc)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a string with the current state of the board. Figures represented
	 * by letters and spaces by * signs.
	 */
	public String toString() {
		String s = "";
		for (int j = 0; j < state.length; j++) {
			for (int i = 0; i < this.state.length; i++) {
				if (state[i][j] == null) {
					s += "*";
				} else {
					s += state[i][j].toString();
				}
			}
			s += "\n";
		}
		return s;
	}

	/** Switch the board's turn! */
	public void switchTurn() {
		if (this.turn == -1) {
			System.out.println("TURN OF BLACK BUT TURNED TO WHITE");this.turn = 1;
		}
		else {
			System.out.println("TURN OF WHIT BUT TURNED TO BLACK");this.turn = -1;
		}
	}

	/** Queue of moves. Changes with each "isLegal" method. */
	public Boolean[][] getMoves() {
		return this.moves;
	}

	/** Queue of moves. Changes with each "isLegal" method. */
	public int getTurn() {
		return this.turn;
	}

	/** Hub method for all the other "isLegal" methods. */
	public void whatLegal(Coordinates realloc) {
		String piece = state[realloc.getX()][realloc.getY()];
		String string = piece;
		if (string == "p") {
			whatpawnlegal(realloc);
		}
		if (string == "P") {
			whatPawnlegal(realloc);
		}
		if (string == "k" || string == "K") {
			whatkinglegal(realloc);
		}
		if (string == "q" || string == "Q") {
			whatqueenlegal(realloc);
		}
		if (string == "n" || string == "N") {
			whatknightlegal(realloc);
		}
		if (string == "b" || string == "B") {
			whatqueenlegal(realloc);
		}
	}

	/** True if it's out of bounds. False if not. */
	public boolean isOutofBounds(Coordinates loc) {
		int x = loc.getX();
		int y = loc.getY();
		if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
			return true;
		}
		return false;
	}

	/** Checks for a pawn (lowercase). */
	public void whatpawnlegal(Coordinates loc) {
		int x = loc.getX();
		int y = loc.getY();
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// If the pawn has not moved and there's nothing in the next 2 squares,
		// add those coordinates to the queue.
		if (isEmpty(new Coordinates(x, y - 1))) {
			this.queue.enqueue(loc.north());
			if (!haspawnmoved(loc) && isEmpty(new Coordinates(x, y - 2))) {
				this.queue.enqueue(loc.north().north());
			}
		}
		// If there's an enemy in the northeast corner, add those coordinates to
		// the queue with the 'enemy' boolean indicator.
		if (areEnemies(loc, loc.northeast()) && (!isOutofBounds(loc))) {
			this.queue.enqueue(loc.northeast().addBoolean());
		}
		// If there's an enemy in the northwest corner, add those coordinates to
		// the queue with the 'enemy' boolean indicator.
		if (areEnemies(loc, loc.northwest()) && (!isOutofBounds(loc))) {
			this.queue.enqueue(loc.northwest().addBoolean());
		}
	}

	/** Checks for a pawn (UPPERCASE). */
	public void whatPawnlegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// If the pawn has not moved and there's nothing in the next 2 squares,
		// add those coordinates to the queue.
		if (isEmpty(loc.south())) {
			this.queue.enqueue(loc.south());
			if (!haspawnmoved(loc) && isEmpty(loc.south().south())) {
				this.queue.enqueue(loc.south().south());
			}
		}
		// If there's an enemy in the southeast corner, add those coordinates to
		// the queue with the 'enemy' boolean indicator.
		if (areEnemies(loc, loc.southeast()) && (!isOutofBounds(loc))) {
			this.queue.enqueue(loc.southeast().addBoolean());
		}
		// If there's an enemy in the southwest corner, add those coordinates to
		// the queue with the 'enemy' boolean indicator.
		if (areEnemies(loc, loc.southwest()) && (!isOutofBounds(loc))) {
			this.queue.enqueue(loc.southwest().addBoolean());
		}
	}

	/** Checks for a knight's moves. */
	public void whatknightlegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		Coordinates testloc = loc.east().east().north();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc);
			}
		}
		testloc = loc.north().north().east();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc);
			}
		}
		testloc = loc.north().north().west();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
		testloc = loc.north().west().west();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
		testloc = loc.south().west().west();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
		testloc = loc.south().south().west();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
		testloc = loc.south().south().east();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
		testloc = loc.south().east().east();
		if (!isOutofBounds(testloc)) {
			if ((isEmpty(testloc))) {
				this.queue.enqueue(testloc);
			} else if (areEnemies(loc, testloc)) {
				this.queue.enqueue(testloc.addBoolean());
			}
		}
	}

	/** Checks for a queen's moves. */
	public void whatqueenlegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// EAST
		Coordinates newloc = loc;
		do {
			newloc = newloc.east();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// NORTHEAST
		newloc = loc;
		do {
			newloc = newloc.northeast();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// NORTH
		newloc = loc;
		do {
			newloc = newloc.north();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// NORTHWEST
		newloc = loc;
		do {
			newloc = newloc.northwest();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// WEST
		newloc = loc;
		do {
			newloc = newloc.west();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTHWEST
		newloc = loc;
		do {
			newloc = newloc.southwest();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTH
		newloc = loc;
		do {
			newloc = newloc.south();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTHEAST
		newloc = loc;
		do {
			newloc = newloc.southeast();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
	}

	/** Checks for a Rook moves. */
	public void whatRookLegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// EAST
		Coordinates newloc = loc;
		do {
			newloc = newloc.east();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// NORTH
		newloc = loc;
		do {
			newloc = newloc.north();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// WEST
		newloc = loc;
		do {
			newloc = newloc.west();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTH
		newloc = loc;
		do {
			newloc = newloc.south();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
	}

	/** Checks for a king's moves. */
	public void whatkinglegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// EAST
		Coordinates newloc = loc;
		newloc = newloc.east();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// NORTHEAST
		newloc = loc;
		newloc = newloc.northeast();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// NORTH
		newloc = loc;
		newloc = newloc.north();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// NORTHWEST
		newloc = loc;
		newloc = newloc.northwest();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// WEST
		newloc = loc;
		newloc = newloc.west();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// SOUTHWEST
		newloc = loc;
		newloc = newloc.southwest();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// SOUTH
		newloc = loc;
		newloc = newloc.south();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
		// SOUTHEAST
		newloc = loc;
		newloc = newloc.southeast();
		if (!isOutofBounds(newloc)) {
			if ((isEmpty(newloc))) {
				this.queue.enqueue(newloc);
			} else if (areEnemies(loc, newloc)) {
				this.queue.enqueue(newloc.addBoolean());
			}
		}
	}

	/** Checks for a bishop's moves. */
	public void whatBishopLegal(Coordinates loc) {
		while (!this.queue.isEmpty()) {
			this.queue.dequeue();
		}
		// NORTHEAST
		Coordinates newloc = loc;
		do {
			newloc = newloc.northeast();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// NORTHWEST
		newloc = loc;
		do {
			newloc = newloc.northwest();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTHWEST
		newloc = loc;
		do {
			newloc = newloc.southwest();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
		// SOUTHEAST
		newloc = loc;
		do {
			newloc = newloc.southeast();
			if (!isOutofBounds(newloc)) {
				if ((isEmpty(newloc))) {
					this.queue.enqueue(newloc);
				} else if (areEnemies(loc, newloc)) {
					this.queue.enqueue(newloc.addBoolean());
				}
			}
		} while (!isOutofBounds(newloc) && isEmpty(newloc));
	}

	/** Returns the string with the legal moves of a particular string
	 * PETER, IF THERE IS A SINGLE METHOD THAT YOU SHOULD CHECK OUT, IT'S THIS ONE.
	 * IT'S GREAT.
	 *  */
	public void toStringwhatLegal(Coordinates loc) {
		// First zero out the moves boolean array
		for (int j = 0; j < moves.length; j++) {
			for (int i = 0; i < this.moves.length; i++) {
				this.moves[i][j] = false;
			}
		}
		while (!this.queue.isEmpty()) {
			Coordinates newloc = this.queue.dequeue();
			this.moves[newloc.getX()][newloc.getY()] = true;
		}

		String s = "";
		for (int j = 0; j < state.length; j++) {
			for (int i = 0; i < this.state.length; i++) {
				if (state[i][j] == null) {
					s += "  *  ";
				} else {
					s += "  " + state[i][j].toString() + "  ";
				}
				if (this.moves[i][j] == true) {
					s += ". ";
				} else {
					s += "  ";
				}
			}
			s += "\n";
		}
		System.out.print(s);
	}

	/**
	 * Return 1 if white k (lowercase) is in check. -1 for black K (UPPERCASE)
	 */
	public int checkCheckMate() {
		int check = 0;
		Coordinates king = null;
		Coordinates King = null;
		// Find the two kings.
		king = wherespiece("k");
		King = wherespiece("K");
		// Plug those coordinates as if they were the queen. Plug in those in
		// reverse.
		whatqueenlegal(king);
		while (!this.queue.isEmpty()) {
			this.check.enqueue(queue.dequeue());
		}
		whatknightlegal(king);
		while (!this.queue.isEmpty()) {
			this.check.enqueue(queue.dequeue());
		}
		while (!this.check.isEmpty()) {
			boolean chek = isLegal(this.check.dequeue(), king);
			if (chek) {
				return 1;
			}
		}
		// Empty queue
		while (!this.check.isEmpty()) {
			this.check.dequeue();
		}
		whatqueenlegal(King);
		while (!queue.isEmpty()) {
			this.check.enqueue(queue.dequeue());
		}
		whatknightlegal(King);
		while (!queue.isEmpty()) {
			this.check.enqueue(queue.dequeue());
		}
		while (!queue.isEmpty()) {
			boolean chek = isLegal(this.queue.dequeue(), King);
			if (chek) {
				return -1;
			}
		}
		return check;
	}

	/** Are there still kings. Returns true if kings died. False otherwise. */
	public boolean kingdied() {
		if (wherespiece("k") == null) {
			return true;
		}
		if (wherespiece("K") == null) {
			return true;
		}
		return false;
	}

	/** Intended to find either a king or a King */
	public Coordinates wherespiece(String piece) {
		for (int j = 0; j < state.length; j++) {
			for (int i = 0; i < this.state.length; i++) {
				if (state[i][j] == piece) {
					return new Coordinates(i, j);
				}
			}
		}
		return null;
	}

	/** Return who's turn it is. White is true black is false. */
	public Coordinates dequeueQueue() {
		return queue.dequeue();
	}

	/** Return who's turn it is. White is true black is false. */
	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	/** Our attempt at having a single method that would do it all.
	 *  It would receive the clicks, check the moves of a given piece,
	 *  check if a given move is legal, and move all the pieces */
	public Boolean play(int x, int y) {
		if (activated) {
			System.out.println("IT IS EMPTY");
			this.inquestion = new Coordinates(x, y);
			if (!isOutofBounds(this.inquestion) || (whatSide(this.inquestion) != this.turn)) {
				activated=false; return false;
			}
			whatLegal(this.inquestion);
			return false;
		} else {
			System.out.println("IT IS NOT EMPTY");
			Coordinates theother = new Coordinates(x, y);
			legalmove(this.inquestion, theother);
			switchTurn();
			while (!this.queue.isEmpty()) {
				this.queue.dequeue();
			}
			while (!this.check.isEmpty()) {
				this.check.dequeue();
			}
			activated = true;
		}
		return true;
	}

	public boolean getActivated() {
		return this.activated;
	}
}
