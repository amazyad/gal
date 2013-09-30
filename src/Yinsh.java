/**
 * @author amazyad
 *
 */
public class Yinsh {
	static int maxLigne=11;
	static int maxColonne=11;
	
	int BlackPlayer = 0; // number of black ring removed
	int WhitePlayer = 0; // number of white ring removed
	Color winner = null;
	
	private int mode = 0;		 // normal or blitz
	Color turn;
	
	Color[][] plateau = new Color[maxColonne][maxLigne];
	
	
	//                             A  B  C  D   E   F   G   H   I   J   K
	final int[]BorderMin=new int[]{2, 1, 1, 1,  1,  2,  2,  3,  4,  5,  7};
	final int[]BorderMax=new int[]{5, 7, 8, 9, 10, 10, 11, 11, 11, 11, 10};
	
	public Yinsh(){
		for(int j=0; j<maxColonne; j++){	
			for(int i=0; i<maxLigne;i++){
				this.plateau[j][i]=Color.NONE;
				if((i + 1) > this.BorderMax[j] || (i + 1) < this.BorderMin[j]) this.plateau[j][i]=Color.IMPOSSIBLE;
			}
		}
	}
	
	/**
	 * @return the turn
	 */
	public Color getTurn() {
		return turn;
	}

	/**
	 * @param turn the turn to set
	 */
	public void setTurn(Color turn) {
		this.turn = turn;
	}
	
	public void changeTurn(){
		if(turn.Is_black() == true) this.setTurn(Color.WHITERING);
		else if(turn.Is_white() == true) this.setTurn(Color.BLACKRING);
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	public int getMode(){
		return mode;
	}
	
	public Color current_color(){
		int random =  (int) (Math.random() * 2);
		System.out.println(random);
		if(random==1){
			this.setTurn(Color.BLACKRING);
			System.out.println(this.getTurn());
			return Color.BLACKRING;
		}
		else{
			this.setTurn(Color.WHITERING);
			System.out.println(this.getTurn());
			return Color.WHITERING;
		}
	}
	
	public Color getColor(Color color){
		if(color == Color.BLACKRING || color == Color.BLACKRINGMARKED || color == Color.BLACKMARK) return Color.BLACKRING;
		else if(color == Color.WHITERING  || color == Color.WHITERINGMARKED || color == Color.WHITEMARK) return Color.WHITERING;
		else return color;
	}
	
	public boolean anneau_existe(int positionY, int positionX, Color color){
		if(	this.plateau[positionY][positionX]==color) return true;
		return false;
	}
	
	public int nombre_anneau(Color color){
		int counterRing = 0;
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				if(this.plateau[j][i]==color) counterRing++;
			}
		}
		return counterRing;
	}
	
	public int nombre_anneau(){
		int counterRing = 0;
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				if(this.plateau[j][i]==Color.WHITERING || 
						this.plateau[j][i]==Color.BLACKRING) counterRing++;
			}
		}
		return counterRing;
	}
	
	public void put_ring(int positionY, int positionX, Color color) throws YinshException{
		test_exception(color, this.plateau[positionY][positionX]);
		this.plateau[positionY][positionX]=color;
		this.changeTurn();
	}
	
	
	
	public boolean is_initialized(){
		int nombre_anneau_white=this.nombre_anneau(Color.WHITERING);
		int nombre_anneau_black=this.nombre_anneau(Color.BLACKRING);
		if(nombre_anneau_white==5 && nombre_anneau_black==5) return true;
		else return false;
	}
	
	public Color markColor(Color colorFrom, Color colorTo){
		if(colorTo == Color.BLACKMARK) colorTo = Color.BLACKRINGMARKED;
		else if(colorTo == Color.WHITEMARK) colorTo = Color.WHITERINGMARKED;
		else if (colorTo == Color.NONE){
			if(colorFrom == Color.WHITERINGMARKED || colorFrom == Color.WHITERING)  colorTo = Color.WHITERING;
			else if(colorFrom == Color.BLACKRINGMARKED || colorFrom == Color.BLACKRING) colorTo = Color.BLACKRING;
		}
		return colorTo;
	}
	
	public Color unmarkColor(Color color){
		if(color == Color.BLACKRINGMARKED) color = Color.BLACKMARK;
		else if(color == Color.WHITERINGMARKED) color = Color.WHITEMARK;
		else if(color == Color.WHITEMARK || color == Color.BLACKMARK) color = Color.NONE;
		return color;
	}
	
	
	
	public void put_marker(int positionY, int positionX, Color color) throws YinshException{
		test_exception(color, this.plateau[positionY][positionX]);
		this.plateau[positionY][positionX] = markColor(this.plateau[positionY][positionX], color);
	}
	
	
	

	public boolean move_ring(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo) throws YinshException{
		Color colorFrom = this.plateau[positionYFrom][positionXFrom];
		Color colorTo = this.plateau[positionYTo][positionXTo];
		test_exception(positionYFrom, positionXFrom, positionYTo, positionXTo);
		
		this.plateau[positionYFrom][positionXFrom] = unmarkColor(this.plateau[positionYFrom][positionXFrom]);
		this.plateau[positionYTo][positionXTo] = markColor(colorFrom,colorTo);
		this.setTurn(this.getColor(this.plateau[positionYFrom][positionXFrom]));
		return true;
	}
	
	
	
	public void flipMarker(int positionY, int positionX){
		if(plateau[positionY][positionX] == Color.WHITEMARK) plateau[positionY][positionX] =  Color.BLACKMARK;
		else if(plateau[positionY][positionX] == Color.BLACKMARK) plateau[positionY][positionX] =  Color.WHITEMARK;
	}
	
	public void flipMarkers(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo){
		int replacement;
		if(positionYFrom == positionYTo){
			if(positionXFrom > positionXTo){
				replacement = positionXFrom;
				positionXFrom = positionXTo;
				positionXTo = replacement;
			}
			for(int i = positionXFrom + 1; i < positionXTo; i++) flipMarker(positionYFrom, i);
		}
		else if(positionXFrom == positionXTo){
			if(positionYFrom > positionYTo){
				replacement = positionYFrom;
				positionYFrom = positionYTo;
				positionYTo = replacement;
			}
			for(int i = positionYFrom + 1; i < positionYTo; i++) flipMarker(i, positionXFrom);
		}
	}
	
	public boolean is_row(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo){
		int beginX = 0, beginY = 0;
		Color color = this.getTurn();
		if (positionYTo - positionYFrom != 0) beginY = (positionYTo - positionYFrom) / Math.abs(positionYTo - positionYFrom);
		if (positionXTo - positionXFrom != 0) beginX = (positionXTo - positionXFrom) / Math.abs(positionXTo - positionXFrom);
		if(beginX * beginY == 1 || beginX * beginY == 0){
			for(int i = positionYFrom, j = positionXFrom; beginY * i <= beginY * positionYTo && beginX * j <= beginX * positionXTo; i = i + beginY, j = j + beginX){
				if(plateau[i][j].Is_marked() == false || this.getColor(color) != this.getColor(plateau[i][j])) return false;
			}
		}
		return true;
	}
	
	public void remove_row(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo){
		if(is_row(positionYFrom, positionXFrom, positionYTo, positionXTo)){
			int replacement;
			if(positionYFrom > positionYTo){
				replacement = positionYFrom;
				positionYFrom = positionYTo;
				positionYTo = replacement;
				replacement = positionXFrom;
				positionXFrom = positionXTo;
				positionXTo= replacement;
			}
			for(int i = positionYFrom, j=positionXFrom ; i <= positionYTo && j <= positionXTo ; i++, j++){
				plateau[i][j] = Color.NONE;
			}
		}
	}
	
	public void remove_ring(int poisitonY, int positionX){
		Color color = plateau[poisitonY][positionX];
		if(color.Is_ring()){
			gain_point(color);
			end_game();
			if(color.getColor()=="white"){
				if(color.Is_marked()) plateau[poisitonY][positionX]=Color.WHITEMARK;
				else plateau[poisitonY][positionX]=Color.NONE;
				
			} else {
				if(color.Is_marked()) plateau[poisitonY][positionX]=Color.BLACKMARK;
				else plateau[poisitonY][positionX]=Color.NONE;
			}
		}
	}
	
	public void gain_point(Color color){
		if(color.getColor() == "white") this.WhitePlayer++;
		else if(color.getColor() == "black") this.BlackPlayer++;
	}
	
	public boolean is_possible_move(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo){
		if(plateau[positionYFrom][positionXFrom].Is_ring() && plateau[positionYTo][positionXTo] == Color.NONE){
			int beginX = 0, beginY = 0;
			if (positionYTo - positionYFrom != 0) beginY = (positionYTo - positionYFrom) / Math.abs(positionYTo - positionYFrom);
			if (positionXTo - positionXFrom != 0) beginX = (positionXTo - positionXFrom) / Math.abs(positionXTo - positionXFrom);
			if(beginX * beginY == 1 || beginX * beginY == 0){
				boolean mark_found = false;
				for(int i = positionYFrom + beginY, j = positionXFrom + beginX; beginY * i <= beginY * positionYTo && beginX * j <= beginX * positionXTo; i = i + beginY, j = j + beginX){
					if(plateau[i][j].Is_ring() == true) return false;
					if(plateau[i][j] == Color.NONE && mark_found == true && (i != positionYTo || j != positionXTo)) return false;
					else if(plateau[i][j].Is_marked() == true) mark_found = true;
				}
				return true;
			} else return false;
		} else return false;
	}
	
	public int[][] get_possible_move(int positionY, int positionX){
		//max possible move is 26
		int [][] possible_move = new int[26][2];
		int counter = 0; // to fill the array in the right index
		
		//get possible move on the upper side of the column
		if(plateau[positionY][positionX].Is_ring() == true){ //we can't move a marker
			boolean mark_found = false; //needed to tell when we find a marker;
			for(int i = (positionX + 1); i <= (BorderMax[positionY] - 1); i++){
				if(plateau[positionY][i].Is_ring() == true) break; //we can't jump over a ring
				else if(plateau[positionY][i] == Color.NONE){ //if empty
					//we fill it	
					possible_move[counter][0] = positionY;
					possible_move[counter][1] = i;
					counter++; // inc the counter
					if(mark_found == true) break; //if we already passed by a marker then stop
				}
				//if marked, set mark_found to true so in the next iteration we know that we already passed a marker
				else if(plateau[positionY][i].Is_marked() == true) mark_found = true;
			}
			//get possible move on the lower side of the column
			mark_found = false; //needed to tell when we find a marker;
			for(int i = (positionX - 1); i >= (BorderMin[positionY] - 1); i--){
				if(plateau[positionY][i].Is_ring() == true) break;
				else if(plateau[positionY][i] == Color.NONE){
					possible_move[counter][0] = positionY;
					possible_move[counter][1] = i;
					counter++;
					if(mark_found == true) break;
				}
				else if(plateau[positionY][i].Is_marked() == true) mark_found = true;
			}
			//get possible move on the right side of the line
			mark_found = false; //needed to tell when we find a marker;
			for(int i = (positionY + 1); i <= (maxColonne - 1) && positionX <= (BorderMax[i] - 1) ; i++){
				if(plateau[i][positionX] == Color.IMPOSSIBLE) break;
				else if(plateau[i][positionX].Is_ring() == true) break;
				else if(plateau[i][positionX] == Color.NONE){
						possible_move[counter][0] = i;
						possible_move[counter][1] = positionX;
						counter++;
						if(mark_found == true) break;
				}
				else if(plateau[i][positionX].Is_marked() == true) mark_found = true;
			}
			//get possible move on the left side of the line
			mark_found = false;
			for(int i = (positionY - 1); i >= 0 && positionX >= (BorderMin[i] - 1) ; i--){
				if(plateau[i][positionX] == Color.IMPOSSIBLE) break;
				else if(plateau[i][positionX].Is_ring() == true) break;
				else if(plateau[i][positionX] == Color.NONE){
						possible_move[counter][0] = i;
						possible_move[counter][1] = positionX;
						counter++;
						if(mark_found == true) break;
				}
				else if(plateau[i][positionX].Is_marked() == true) mark_found = true;
			}
			//get possible move on the up right side of the diagonal
			mark_found = false;
			for(int i = (positionY + 1), j = (positionX + 1); i  <= (maxColonne - 1) && j <= (BorderMax[i] - 1); i++, j++){
				if(plateau[i][j] == Color.IMPOSSIBLE) break;
				else if(plateau[i][j].Is_ring() == true) break;
				else if(plateau[i][j] == Color.NONE){
						possible_move[counter][0] = i;
						possible_move[counter][1] = j;
						counter++;
						if(mark_found == true) break;
				}
				else if(plateau[i][j].Is_marked() == true) mark_found = true;
			}
			//get possible move on the down left side of the diagonal
			mark_found = false;
			for(int i = (positionY - 1), j = (positionX - 1); i >= 0 && j >= (BorderMin[i] - 1) ; i--, j--){
				if(plateau[i][j] == Color.IMPOSSIBLE) break;
				else if(plateau[i][j].Is_ring() == true) break;
				else if(plateau[i][j] == Color.NONE){
						possible_move[counter][0] = i;
						possible_move[counter][1] = j;
						counter++;
						if(mark_found == true) break;
				}
				else if(plateau[i][j].Is_marked() == true) mark_found = true;
			}
		}
		return possible_move;
	}
	
	public Color end_game(){
		switch(mode){
			case 0 :{
				if(BlackPlayer == 3) winner = Color.BLACKRING;
				else if(WhitePlayer == 3) winner = Color.WHITERING;
			}
			break;
			case 1 :{
				if(BlackPlayer == 1) winner = Color.BLACKRING;
				else if(WhitePlayer == 1) winner = Color.WHITERING;
			}
		}
		return winner;
	}
	
	
	public void test_exception(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo) throws YinshException{
		Color colorFrom = this.plateau[positionYFrom][positionXFrom];
		Color colorTo = this.plateau[positionYTo][positionXTo];
		if(winner != null)
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.GAME_ENDED);
		if(positionYFrom != positionYTo && positionXFrom != positionXTo && (positionYFrom - positionYTo) != (positionXFrom - positionXTo)) 
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.STEP_ERROR);
		if(colorTo == Color.IMPOSSIBLE)
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.IMPOSSIBLE);
		if((colorFrom == Color.WHITEMARK || colorFrom == Color.BLACKMARK) && (colorTo != Color.WHITERING || colorTo == Color.BLACKRING))
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.NEED_A_RING);
		if(this.getColor(colorFrom) != this.getColor(colorTo) && this.getColor(colorTo) != Color.NONE) 
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.OTHER_PLAYER_FIELD);
		if(this.getTurn() != getColor(colorFrom))
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.NOT_YOUR_RING_MARK);
		if(colorTo == Color.WHITERINGMARKED || colorTo == Color.BLACKRINGMARKED)
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.FULL_CASE);
		if(colorTo ==  colorFrom)
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.COLOR_ON_COLOR);
		if(this.is_possible_move(positionYFrom, positionXFrom, positionYTo, positionXTo) == false)
			throw new YinshException(this.plateau[positionYFrom][positionXFrom], TypeException.STEP_NOT_PERMITTED);
	}
	
	public void test_exception(Color colorFrom, Color colorTo) throws YinshException{
		if(winner != null)
			throw new YinshException(colorFrom, TypeException.GAME_ENDED);
		if(colorTo == Color.IMPOSSIBLE)
			throw new YinshException(colorFrom, TypeException.IMPOSSIBLE);
		if((colorFrom == Color.WHITEMARK || colorFrom == Color.BLACKMARK) && (colorTo != Color.WHITERING && colorTo != Color.BLACKRING))
			throw new YinshException(colorFrom, TypeException.NEED_A_RING);
		if(this.getColor(colorFrom) != this.getColor(colorTo) && this.getColor(colorTo) != Color.NONE) 
			throw new YinshException(colorFrom, TypeException.OTHER_PLAYER_FIELD);
		if(this.getTurn() != getColor(colorFrom))
			throw new YinshException(colorFrom, TypeException.NOT_YOUR_RING_MARK);
		if(colorTo == Color.WHITERINGMARKED || colorTo == Color.BLACKRINGMARKED)
			throw new YinshException(colorFrom, TypeException.FULL_CASE);
		if(colorTo ==  colorFrom)
			throw new YinshException(colorFrom, TypeException.COLOR_ON_COLOR);
	}
	
	
	///////////////////////////////////////////NEEDED FUNCTIONS //////////////////////////
	
	
	public void printPlateau(){
		System.out.print("|-");
		System.out.print("-----");
		System.out.print("-|");
		for(int i=0; i<9;i++){
			System.out.print("|-");
			System.out.print("- "+(i+1)+" -");
			System.out.print("-|");
		}
		System.out.print("|-");
		System.out.print("- 10-");
		System.out.print("-|");
		System.out.print("|-");
		System.out.print("- 11-");
		System.out.print("-|");
		System.out.println();
		for(int i=0; i<=maxLigne;i++){
			System.out.print("---------");
		}
		System.out.println();
		for(int j=0; j<maxColonne; j++){
			System.out.print("|-- "+(this.toY(j))+" --|");
			for(int i=0; i<maxLigne;i++){
				if(plateau[j][i]== Color.IMPOSSIBLE){
					System.out.print("|-");
					System.out.print("--I--");
					System.out.print("-|");
				}
				if(plateau[j][i]== Color.NONE){
					System.out.print("|-");
					System.out.print("     ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.WHITERING){
					System.out.print("|-");
					System.out.print(" W-R ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.BLACKRING){
					System.out.print("|-");
					System.out.print(" B-R ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.WHITEMARK){
					System.out.print("|-");
					System.out.print(" W-M ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.BLACKMARK){
					System.out.print("|-");
					System.out.print(" B-M ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.BLACKRINGMARKED){
					System.out.print("|-");
					System.out.print(" BRM ");
					System.out.print("-|");
				}
				else if (plateau[j][i]== Color.WHITERINGMARKED){
					System.out.print("|-");
					System.out.print(" WRM ");
					System.out.print("-|");
				}
			}
			System.out.println();
			for(int i=0; i<=maxLigne;i++){
				System.out.print("---------");
			}
			System.out.println();
		}
	}
	
	public int toY(char colonne){
		int ascii = (int)colonne;
		return ascii - 65;
	}
	
	public char toY(int colonne){
		char ascii = (char)(colonne + 65);
		return ascii;
	}
	
	public int toX(int ligne){
		ligne = ligne -1;
		return ligne;
	}
	
}
