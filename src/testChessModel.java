import static org.junit.Assert.*;
import org.junit.Test;

public class testChessModel {

	ChessModel model = new ChessModel();
	
	@Test
	public void testoString(){
		String end =
				"RNBKQBNR\n" +
				"PPPPPPPP\n" +
				"********\n" +
				"********\n" +
				"********\n" +
				"********\n" +
				"pppppppp\n" +
				"rnbqkbnr\n";
		assertEquals(end,model.toString());
	}
	
	@Test
	public void testIsLegal(){
		boolean test = model.isLegal(new Coordinates(0,1),new Coordinates(0,4));
		assertEquals(false,test);
		test = model.isLegal(new Coordinates(1,7),new Coordinates(2,5));
		assertEquals(true,test);
	}
	
	@Test
	public void wherearethekings(){
		Coordinates king = null;
		king = model.wherespiece("k");
		assertEquals(new Coordinates(4,7), king);
		Coordinates King = null;
		King = model.wherespiece("K");
		assertEquals(new Coordinates(3,0), King);
	}
	
	@Test
	public void testCheckMate(){
		int check = model.checkCheckMate();
		assertEquals(0,check);
		model = new ChessModel();
		model.move(new Coordinates(3,1), new Coordinates(3,3));
		model.move(new Coordinates(2,0), new Coordinates(7,5));
		model.move(new Coordinates(7,0), new Coordinates(4,6));
		String end =
				"RN*KQBN*\n" +
				"PPP*PPPP\n" +
				"********\n" +
				"***P****\n" +
				"********\n" +
				"*******B\n" +
				"ppppRppp\n" +
				"rnbqkbnr\n";
		assertEquals(end, model.toString());
		assertEquals(1,check);
	}
	
	@Test
	public void testPlay(){
		model.play(0,1);
		model.play(0,2);
		String end =
				"RNBKQBNR\n" +
				"*PPPPPPP\n" +
				"P*******\n" +
				"********\n" +
				"********\n" +
				"********\n" +
				"pppppppp\n" +
				"rnbqkbnr\n";
		assertEquals(end, model.toString());
	}
	
	@Test
	public void testHardMove(){
		model.move(new Coordinates(3,1), new Coordinates(3,3));
		model.move(new Coordinates(2,0), new Coordinates(7,5));
		model.move(new Coordinates(7,0), new Coordinates(7,4));
		String end =
				"RN*KQBN*\n" +
				"PPP*PPPP\n" +
				"********\n" +
				"***P****\n" +
				"*******R\n" +
				"*******B\n" +
				"pppppppp\n" +
				"rnbqkbnr\n";
		assertEquals(end, model.toString());
		model = new ChessModel();
	}
	
	@Test
	public void testMove(){
		model.legalmove(new Coordinates(3,1), new Coordinates(3,3));
		model.legalmove(new Coordinates(2,0), new Coordinates(7,5));
		model.legalmove(new Coordinates(7,0), new Coordinates(7,4));
		model.legalmove(new Coordinates(6,6), new Coordinates(7,5));

		String end =
				"RN*KQBNR\n" +
				"PPP*PPPP\n" +
				"********\n" +
				"***P****\n" +
				"********\n" +
				"*******p\n" +
				"pppppp*p\n" +
				"rnbqkbnr\n";
		assertEquals(end, model.toString());
		model = new ChessModel();
	}
	
	

	@Test
	public void testwhatpawnLegal(){
		Coordinates loc = new Coordinates(1,6);
		model.whatpawnlegal(loc);
		Queue<Coordinates> AllMoves = model.getQueue();
		Coordinates Single = AllMoves.dequeue();
		assertEquals("1, 5", Single.toString());
		Coordinates Double = AllMoves.dequeue();
		assertEquals("1, 4", Double.toString());
		model.getState()[2][5] = "Q";
		model.whatpawnlegal(loc);
		AllMoves = model.getQueue();
		AllMoves.dequeue();
		AllMoves.dequeue();
		Coordinates AttackNW = AllMoves.dequeue();
		assertEquals("2, 5", AttackNW.toString());
		model.getState()[0][5] = "Q";
		model.whatpawnlegal(loc);
		AllMoves = model.getQueue();
		AllMoves.dequeue();
		AllMoves.dequeue();
		AllMoves.dequeue();
		Coordinates AttackNE = AllMoves.dequeue();
		assertEquals("0, 5", AttackNE.toString());	
	}

	@Test
	public void testwhatPawnLegal(){
		Coordinates loc = new Coordinates(1,1);
		model.whatPawnlegal(loc);
		Queue<Coordinates> AllMoves = model.getQueue();
		Coordinates Single = AllMoves.dequeue();
		assertEquals("1, 2", Single.toString());
		Coordinates Double = AllMoves.dequeue();
		assertEquals("1, 3", Double.toString());
		model.getState()[2][2] = "q";
		model.whatPawnlegal(loc);
		AllMoves = model.getQueue();
		AllMoves.dequeue();
		AllMoves.dequeue();
		Coordinates AttackSE = AllMoves.dequeue();
		assertEquals("2, 2", AttackSE.toString());
	}

	@Test
	public void testwhatknightLegal(){
		Coordinates loc = new Coordinates(3,4);
		model.getState()[3][4]="N";
		model.getState()[5][5]="K";
		model.whatknightlegal(loc);
		Queue<Coordinates> AllMoves = model.getQueue();
		Coordinates easteastnorth = AllMoves.dequeue();
		assertEquals("5, 3", easteastnorth.toString());
		Coordinates northnortheast = AllMoves.dequeue();
		assertEquals("4, 2", northnortheast.toString());
		Coordinates northnorthwest = AllMoves.dequeue();
		assertEquals("2, 2", northnorthwest.toString());
		Coordinates northwestwest = AllMoves.dequeue();
		assertEquals("1, 3", northwestwest.toString());
		Coordinates southwestwest = AllMoves.dequeue();
		assertEquals("1, 5", southwestwest.toString());
		Coordinates southsouthwest = AllMoves.dequeue();
		assertEquals("2, 6", southsouthwest.toString());
		Coordinates southsoutheast = AllMoves.dequeue();
		assertEquals("4, 6", southsoutheast.toString());
		assertEquals(true, AllMoves.isEmpty());
		model.getState()[3][4]="n";
		model.getState()[1][3]="k";
		model.whatknightlegal(loc);
		AllMoves = model.getQueue();
		easteastnorth = AllMoves.dequeue();
		assertEquals("5, 3", easteastnorth.toString());
		northnortheast = AllMoves.dequeue();
		assertEquals("4, 2", northnortheast.toString());
		northnorthwest = AllMoves.dequeue();
		assertEquals("2, 2", northnorthwest.toString());
		southwestwest = AllMoves.dequeue();
		assertEquals("1, 3", northwestwest.toString());
		Coordinates southeasteast = AllMoves.dequeue();
		assertEquals("5, 5", southeasteast.toString());
		assertEquals(true, AllMoves.isEmpty());
		model.getState()[1][3]=null;
		model.getState()[5][5]=null;
		assertEquals(false,false);
	}

	@Test
	public void testwhatqueenLegal(){
		Coordinates loc = new Coordinates(3,4);
		model.getState()[3][4]="q";
		model.getState()[5][5]="K";
		model.whatqueenlegal(loc);
		Queue<Coordinates> AllMoves = model.getQueue();
		Coordinates oneeast = AllMoves.dequeue();
		assertEquals("4, 4", oneeast.toString());
		Coordinates twoeast = AllMoves.dequeue();
		assertEquals("5, 4", twoeast.toString());
		Coordinates threeeast = AllMoves.dequeue();
		assertEquals("6, 4", threeeast.toString());
		Coordinates foureast = AllMoves.dequeue();
		assertEquals("7, 4", foureast.toString());
		model.getState()[3][4]="Q";
		model.getState()[5][4]="K";
		model.whatqueenlegal(loc);
		AllMoves = model.getQueue();
		oneeast = AllMoves.dequeue();
		assertEquals("4, 4", oneeast.toString());
		model.getState()[3][4]="Q";
		model.getState()[5][4]="k";
		model.whatqueenlegal(loc);
		AllMoves = model.getQueue();
		oneeast = AllMoves.dequeue();
		assertEquals("4, 4", oneeast.toString());
		assertEquals(false, oneeast.getZ());
		twoeast = AllMoves.dequeue();
		assertEquals("5, 4", twoeast.toString());
		assertEquals(true, twoeast.getZ());
		model.whatqueenlegal(loc);
		model.getState()[5][4]=null;
		model.getState()[5][5]=null;
		model.getState()[2][4]="n";
		model.getState()[4][4]="n";
		model.getState()[3][5]="n";
		model.getState()[3][3]="n";
		model.whatqueenlegal(loc);
//		model.toStringwhatLegal(loc);
		model.getState()[2][4]=null;
		model.getState()[4][4]=null;
		model.getState()[3][5]=null;
		model.getState()[3][3]=null;
		model.getState()[3][4]=null;
	}
	
	@Test
	public void testKingLegal(){
		Coordinates loc = new Coordinates(3,4);
		model.getState()[3][4]="k";
		model.whatkinglegal(loc);
		model.getState()[4][4]="P";
//		model.toStringwhatLegal(loc);
	}

	@Test
	public void testwhatSide(){
		Coordinates loc = new Coordinates(0,6);
		assertEquals(1, model.whatSide(loc));
		assertEquals(0, model.whatSide(new Coordinates(2,3)));
		assertEquals(-1, model.whatSide(new Coordinates(0,1)));
	}

	@Test
	public void testisOutofBounds(){
		assertEquals(true, model.isOutofBounds(new Coordinates(-1,3)));
		assertEquals(true, model.isOutofBounds(new Coordinates(8,3)));
		assertEquals(true, model.isOutofBounds(new Coordinates(3,8)));
		assertEquals(true, model.isOutofBounds(new Coordinates(1,-1)));
		assertEquals(false, model.isOutofBounds(new Coordinates(0,3)));
		assertEquals(false, model.isOutofBounds(new Coordinates(7,3)));
		assertEquals(false, model.isOutofBounds(new Coordinates(1,0)));
		assertEquals(false, model.isOutofBounds(new Coordinates(1,7)));
		assertEquals(false, model.isOutofBounds(new Coordinates(1,3)));
	}

	@Test
	public void testhaspawnmoved(){
		Coordinates smallpawn = new Coordinates(0,6);
		assertEquals(false, model.haspawnmoved(smallpawn));
	}

	@Test public void testisEmpty(){
		ChessModel board = new ChessModel();
		assertEquals(true, board.isEmpty(new Coordinates(5,5)));
		assertEquals(false, board.isEmpty(new Coordinates(3,7)));
		//		assertEquals(false, board.isEmpty(0,1));
		//		assertEquals(false, board.isEmpty(1,1));
		//		assertEquals(false, board.isEmpty(2,1));
		//		assertEquals(false, board.isEmpty(3,1));
		//		assertEquals(false, board.isEmpty(4,1));
		//		assertEquals(false, board.isEmpty(5,1));
		//		assertEquals(false, board.isEmpty(7,1));
		//		assertEquals(false, board.isEmpty(7,));
		//		
		//




	}


	@Test
	public void testareEnemies(){
		Coordinates whitepawn = new Coordinates(0,6);
		Coordinates blackpawn = new Coordinates(0,1);
		assertTrue(model.areEnemies(whitepawn, blackpawn));
	}

	@Test
	public void testCoordinateToString(){
		Coordinates loc = new Coordinates(0,0);
		assertEquals("0, 0", loc.toString());
	}

	@Test
	public void testchangeCoordinates(){
		Coordinates loc = new Coordinates(0,7);
		Coordinates newloc = loc.north();
		assertEquals("0, 6", newloc.toString());
	}

	@Test
	public void testchangeCoordinatesMethod(){
		Coordinates loc = new Coordinates(0,0);
		Coordinates newloc = loc.change(0, 1);
		assertEquals("0, 1", newloc.toString());
	}


}
