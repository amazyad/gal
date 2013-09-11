/**
 * @author amazyad
 *
 */
public class Yinsh {
	static int maxLigne=11;
	static int maxColonne=11;
	Ring lastPlayedRing;
	Ring[][] plateau = new Ring[maxColonne][maxLigne];
	
	
	public Yinsh(){
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				this.plateau[j][i]=Ring.NONE;
			}
		}
		this.plateau[0][0]=Ring.IMPOSSIBLE;
		this.plateau[0][5]=Ring.IMPOSSIBLE;
		this.plateau[0][10]=Ring.IMPOSSIBLE;
		this.plateau[5][0]=Ring.IMPOSSIBLE;
		this.plateau[5][10]=Ring.IMPOSSIBLE;
		this.plateau[7][4]=Ring.IMPOSSIBLE;
	}
	
	public Ring current_color(){
		double random =  (double)Math.round((Math.random() * 1) + 1);
		System.out.println(random);
		if(random==1) return Ring.BLACK;
		else return Ring.WHITE;
	}
	
	public int toY(char colonne){
		int ascii = (int)colonne;
		return ascii - 65;
	}
	
	public int toX(int ligne){
		ligne = ligne -1;
		return ligne;
	}
	
	public boolean anneau_existe(char colonne, int ligne, Ring ring){
		int positionY=this.toY(colonne);
		int positionX=this.toX(ligne);
		if(	this.plateau[positionY][positionX]==ring) return true; 
		return false;
	}
	
	public int nombre_anneau(Ring ring){
		int counterRing = 0;
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				if(this.plateau[j][i]==ring) counterRing++;
			}
		}
		return counterRing;
	}
	
	public int nombre_anneau(){
		int counterRing = 0;
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				if(this.plateau[j][i]==Ring.WHITE || 
						this.plateau[j][i]==Ring.BLACK) counterRing++;
			}
		}
		return counterRing;
	}
	
	public void put_ring(char colonne, int ligne, Ring ring) throws YinshException{
		int positionY=this.toY(colonne);
		int positionX=this.toX(ligne);
		if(this.plateau[positionY][positionX]==Ring.IMPOSSIBLE)
			throw new YinshException(this.plateau[positionY][positionX]);
		else if(ring==lastPlayedRing)
			throw new YinshException(this.plateau[positionY][positionX]);
		else{
			this.plateau[positionY][positionX]=ring;
			lastPlayedRing=ring;
		}
	}
	
	
	public boolean is_initialized(){
		int nombre_anneau_white=this.nombre_anneau(Ring.WHITE);
		int nombre_anneau_black=this.nombre_anneau(Ring.BLACK);
		if(nombre_anneau_white==5 && nombre_anneau_black==5) return true;
		else return false;
	}
	
	
}
