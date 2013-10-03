/**
 * @author amazyad, mcherkaoui
 *
 */
public class Yinsh {
    static int maxLigne=11;
    static int maxColonne=11;


    private int nMarkers;

    int BlackPlayer = 0; // number of black ring removed
    int WhitePlayer = 0; // number of white ring removed
    Color winner = null; // le gagnant

    private int mode = 0;		 // normal or blitz, 0 pour normal, 1 pour blitz
    private Color turn;					 // a qui le tour

    //le plateau du jeu
    Color[][] plateau = new Color[maxColonne][maxLigne];

    //pour gerer les cases inexistants en jeu
    //                             A  B  C  D   E   F   G   H   I   J   K
    final int[]BorderMin=new int[]{2, 1, 1, 1,  1,  2,  2,  3,  4,  5,  7};
    final int[]BorderMax=new int[]{5, 7, 8, 9, 10, 10, 11, 11, 11, 11, 10};


    /**
     * Initialisation du classe
     */
    public Yinsh(){
        setnMarkers(51);
        for(int j=0; j<maxColonne; j++){
            for(int i=0; i<maxLigne;i++){
                if((i + 1) > this.BorderMax[j] || (i + 1) < this.BorderMin[j]) this.plateau[j][i]=Color.IMPOSSIBLE;
                else this.plateau[j][i]=Color.NONE;
            }
        }
    }

    public Yinsh(int mode){
        setMode(mode);
        setnMarkers(51);
        for(int j=0; j<maxColonne; j++){
            for(int i=0; i<maxLigne;i++){
                if((i + 1) > this.BorderMax[j] || (i + 1) < this.BorderMin[j]) this.plateau[j][i]=Color.IMPOSSIBLE;
                else this.plateau[j][i]=Color.NONE;
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

    /**
     * @description changer le tour
     */
    public void changeTurn(){
        if(turn.Is_black() == true) this.setTurn(Color.WHITERING);
        else if(turn.Is_white() == true) this.setTurn(Color.BLACKRING);
    }

    public int getnMarkers() {
        return nMarkers;
    }

    public void setnMarkers(int nMarkers) {
        this.nMarkers = nMarkers;
    }

    /**
     * @author amazyad
     * @param mode blitz ou normal
     */
    public void setMode(int mode){
        this.mode = mode;
    }

    /**
     *@author amazyad
     * @return mode
     */
    public int getMode(){
        return mode;
    }

    /**
     *
     * @return le couleur du joueur qui commence la partie
     */
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

    /**
     *
     * @param positionY colonne
     * @param positionX ligne
     * @param color le couleur de l'anneau
     * @return true si l'anneau existe false sinon
     */
    public boolean anneau_existe(int positionY, int positionX, Color color){
        if(	this.plateau[positionY][positionX]==color) return true;
        return false;
    }

    /**
     *
     * @param color
     * @return combien d'anneau de la couleur color existe
     */
    public int nombre_anneau(Color color){
        int counterRing = 0;
        for(int j=0; j<maxColonne ;j++){
            for(int i=(this.BorderMin[j] - 1); i< this.BorderMax[j]; i++){
                if(this.plateau[j][i]==color) counterRing++;
            }
        }
        return counterRing;
    }

    /**
     * @author amazyad
     * @return le nombre des anneaux
     */
    public int nombre_anneau(){
        int counterRing = 0;
        for(int j=0; j<maxColonne ;j++){
            for(int i=(this.BorderMin[j] - 1); i< this.BorderMax[j]; i++){
                if(this.plateau[j][i]==Color.WHITERING ||
                        this.plateau[j][i]==Color.BLACKRING) counterRing++;
            }
        }
        return counterRing;
    }

    /**
     * @author amazyad
     * @param positionY
     * @param positionX
     * @param color
     * @throws YinshException
     */
    public void put_ring(int positionY, int positionX, Color color) throws YinshException{
        test_exception(color, this.plateau[positionY][positionX]);
        this.plateau[positionY][positionX]=color;
        this.changeTurn();
    }


    /**
     * @author amazyad
     * @return true si le jeu a ete bien initialise false sinon
     */
    public boolean is_initialized(){
        int nombre_anneau_white=this.nombre_anneau(Color.WHITERING);
        int nombre_anneau_black=this.nombre_anneau(Color.BLACKRING);
        if(nombre_anneau_white==5 && nombre_anneau_black==5) return true;
        else return false;
    }

    /**
     * @author amazyad
     * @param colorFrom
     * @param colorTo
     * @return le couleur apres l'ajout d'un anneau
     */
    public Color addRing(Color colorFrom, Color colorTo){
        if(colorTo == Color.BLACKMARK) colorTo = Color.BLACKRINGMARKED;
        else if(colorTo == Color.WHITEMARK) colorTo = Color.WHITERINGMARKED;
        else if (colorTo == Color.NONE){
            if(colorFrom == Color.WHITERINGMARKED || colorFrom == Color.WHITERING)  colorTo = Color.WHITERING;
            else if(colorFrom == Color.BLACKRINGMARKED || colorFrom == Color.BLACKRING) colorTo = Color.BLACKRING;
        }
        return colorTo;
    }

    /**
     * @author amazyad
     * @param color
     * @return le couleur apres la suppression(deplacement) d'un anneau
     */
    public Color removeRing(Color color){
        if(color == Color.BLACKRINGMARKED) color = Color.BLACKMARK;
        else if(color == Color.WHITERINGMARKED) color = Color.WHITEMARK;
        else if(color == Color.WHITEMARK || color == Color.BLACKMARK) color = Color.NONE;
        return color;
    }


    /**
     * @author amazyad
     * @param positionY
     * @param positionX
     * @param color
     * @throws YinshException
     * @description il ajoute un markeur dans un anneau a un position donnee
     */
    public void put_marker(int positionY, int positionX, Color color) throws YinshException{
        end_game();
        test_exception(color, this.plateau[positionY][positionX]);
        plateau[positionY][positionX] = addRing(this.plateau[positionY][positionX], color);
        int nMarkers = getnMarkers();
        setnMarkers(nMarkers-1);
    }



    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @throws YinshException
     * @description deplacer un anneau d'un position a un autre.
     * tester s il y a un row
     * remove le row
     * remove le ring
     * changer le tour
     */
    public void move_ring(int positionYFrom, int positionXFrom, int positionYTo, int positionXTo) throws YinshException{
        Color colorFrom = this.plateau[positionYFrom][positionXFrom];
        Color colorTo = this.plateau[positionYTo][positionXTo];
        test_exception(positionYFrom, positionXFrom, positionYTo, positionXTo);

        this.plateau[positionYFrom][positionXFrom] = removeRing(this.plateau[positionYFrom][positionXFrom]);
        this.plateau[positionYTo][positionXTo] = addRing(colorFrom,colorTo);
        this.setTurn(this.getColor(this.plateau[positionYFrom][positionXFrom]));
    }


    /**
     * @author amazyad
     * @param positionY
     * @param positionX
     * @description il change la couleur markeur du marqueur
     */
    public void flipMarker(int positionY, int positionX){
        if(plateau[positionY][positionX] == Color.WHITEMARK) plateau[positionY][positionX] =  Color.BLACKMARK;
        else if(plateau[positionY][positionX] == Color.BLACKMARK) plateau[positionY][positionX] =  Color.WHITEMARK;
    }

    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @description changer la couleur de plusieurs marqueur sur la meme ligne
     */
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

    /**
     * @author amazyad
     * @return un tableau contenant la position de tous les marqueurs
     */
    public int[][] getMarkers(){
        int[][] markers = new int[26][2];
        int index = 0;
        for(int i = 0; i < maxColonne; i++){
            for(int j=(this.BorderMin[i] - 1); j< this.BorderMax[j]; j++){
                if(plateau[i][j].Is_marked()){
                    markers[index][0] = i;
                    markers[index][1] = j;
                    index++;
                }
            }
        }
        return markers;
    }
    /**
     *
     * @return un tableau du position du rows
     * @description trouver tout les rows
     */
    public int[][] getRows(){
        int[][] markers = getMarkers();
        int[][] rows = new int[6][4];
        int index = 0;
        for(int i = 0; i < markers.length && markers[i][0] != 0 && markers[i][1] != 0; i++){
            // on cherche le rows sur les colonnes
            if(markers[i][1] - this.BorderMin[markers[i][0]] + 1 >= 4){
                int positionX = markers[i][1];
                int positionY = markers[i][0];
                Color couleur = plateau[positionY][positionX];
                int counter = 1;
                for(int j=positionX - 1; j >= 0; j--){
                    if(plateau[positionY][j].Is_marked() == false
                            || plateau[positionY][j].getColor() != couleur.getColor()) break;
                    counter++;
                    if(counter == 5){
                        rows[index][0] = positionY;
                        rows[index][1] = positionX;
                        rows[index][2] = positionY;
                        rows[index][3] = j;
                        index++;
                    }
                }
            }
			/*else if(BorderMax[markers[i][0]] - markers[i][1] - 1 >= 4){
				int positionX = markers[i][1];
				int positionY = markers[i][0];
				Color couleur = plateau[positionY][positionX];
				int counter = 1;
				for(int j=positionX + 1; j <= BorderMax[positionY] -1; j++){
					if(plateau[positionY][j].Is_marked() == false 
							|| plateau[positionY][j].getColor() != couleur.getColor()) break;
					counter++;
					if(counter == 5){
						rows[index][0] = positionY;
						rows[index][1] = positionX;
						rows[index][2] = positionY;
						rows[index][3] = j;
						index++;
					}
				}
				
			}*/

            // on cherche le rows sur les lignes
            if(markers[i][0] >= 4){
                int positionX = markers[i][1];
                int positionY = markers[i][0];
                Color couleur = plateau[positionY][positionX];
                int counter = 1;
                for(int j=positionY - 1; j >= 0; j--){
                    if(plateau[j][positionX].Is_marked() == false
                            || plateau[j][positionX].getColor() != couleur.getColor()) break;
                    counter++;
                    if(counter == 5){
                        rows[index][0] = positionY;
                        rows[index][1] = positionX;
                        rows[index][2] = j;
                        rows[index][3] = positionX;
                        index++;
                    }
                }
            }
			/*else if(maxColonne - 1 - markers[i][0] >= 4){
				int positionX = markers[i][1];
				int positionY = markers[i][0];
				Color couleur = plateau[positionY][positionX];
				int counter = 1;
				for(int j=positionY + 1; j <= maxColonne - 1; j++){
					if(plateau[j][positionX].Is_marked() == false 
							|| plateau[j][positionX].getColor() != couleur.getColor()) break;
					counter++;
					if(counter == 5){
						rows[index][0] = positionY;
						rows[index][1] = positionX;
						rows[index][2] = j;
						rows[index][3] = positionX;
						index++;
					}
				}
			}*/

            //on cherche les rows sur les diagonnales
            if(markers[i][0] >= 4 && markers[i][1] >= 4){
                int positionX = markers[i][1];
                int positionY = markers[i][0];
                Color couleur = plateau[positionY][positionX];
                int counter = 1;
                for(int j=positionY - 1, k = positionX - 1; j >= 0 && k >=0; j--, k--){
                    if(plateau[j][k].Is_marked() == false
                            || plateau[j][k].getColor() != couleur.getColor()) break;
                    counter++;
                    if(counter == 5){
                        rows[index][0] = j;
                        rows[index][1] = k;
                        rows[index][2] = positionY;
                        rows[index][3] = positionX;
                        index++;
                    }
                }
            }

        }
        return rows;
    }

    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @return true s il existe un row entre ce deux poisitions false sinon
     */
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

    public boolean row_exist(Color color){
        return ((getRow())[1] == 0) ? false : true;
    }

    public int[] getRow(){
        Color color = this.getTurn();
        int[][] rows = getRows();
        int[] row = new int[4];
        for(int i= 0; i < rows.length && rows[i][0] != 0 && rows[i][1] !=0; i++){
            if(getColor(plateau[rows[i][0]][rows[i][1]]) == color){
                row = rows[i];
                break;
            }
        }
        return row;
    }

    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @description supprimer un row s'il existe
     */
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

    public void remove_row(){
        Color color = this.getTurn();
        int[] row = getRow();
        if(row_exist(color)){
            int positionYFrom = row[0];
            int positionXFrom = row[1];
            int positionYTo = row[2];
            int positionXTo = row[3];
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

    /**
     * @author amazyad
     * @param poisitonY
     * @param positionX
     * description on supprime l'anneau de notre choix
     * et on ajoute un point au joueur,
     * et on test si le jeu est terminee
     */
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

    /**
     * @author amazyad
     * @param color
     * @description ajouter un point a un joueur
     */
    public void gain_point(Color color){
        if(color.getColor() == "white") this.WhitePlayer++;
        else if(color.getColor() == "black") this.BlackPlayer++;
    }

    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @return true si le deplacement est possible, false sinon
     */
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

    /**
     * @author amazyad
     * @param positionY
     * @param positionX
     * @return tous les deplacements possible pour une case
     */
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

    /**
     * @author amazyad
     * @return tester si le jeu est terminee
     * si un joueur a 1 point en mode blitz
     * ou si il a 3 point en mode normal
     */
    public Color end_game(){
        switch(mode){
            case 0 :{
                if(getnMarkers() == 0){
                    if(BlackPlayer >  WhitePlayer) winner = Color.BLACKRING;
                    else if(WhitePlayer > BlackPlayer) winner = Color.WHITERING;
                    else winner = Color.NONE;
                }
                if(BlackPlayer == 3) winner = Color.BLACKRING;
                else if(WhitePlayer == 3) winner = Color.WHITERING;
            }
            break;
            case 1 :{
                if(getnMarkers() == 0){
                    if(BlackPlayer >  WhitePlayer) winner = Color.BLACKRING;
                    else if(WhitePlayer > BlackPlayer) winner = Color.WHITERING;
                    else winner = Color.NONE;
                }
                if(BlackPlayer == 1) winner = Color.BLACKRING;
                else if(WhitePlayer == 1) winner = Color.WHITERING;
            }
        }
        return winner;
    }

    /**
     * @author amazyad
     * @param positionYFrom
     * @param positionXFrom
     * @param positionYTo
     * @param positionXTo
     * @throws YinshException
     * @description gerer les exceptions
     */
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

    /**
     * @author amazyad
     * @description afficher un tableau
     */
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

    /**
     *
     * @param colonne
     * @return le position
     */
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