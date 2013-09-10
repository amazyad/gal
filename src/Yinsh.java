/**
 * @author amazyad
 *
 */
public class Yinsh {
	static int maxLigne=11;
	static int maxColonne=11;
	int colonne;
	int ligne;
	Color color;
	Ring[][] plateau = new Ring[maxColonne][maxLigne];
	
	
	public Yinsh(){
		for(int i=0; i<maxLigne;i++){
			for(int j=0; j<maxColonne; j++){
				this.plateau[j][i]=Ring.NONE;
			}
		}
		this.plateau[0][0]=Ring.IMPOSSIBLE;
	}
	
	public Color current_color(){
		double random =  (double)Math.round((Math.random() * 1) + 1);
		System.out.println(random);
		if(random==1) return Color.BLACK;
		else return Color.WHITE;
	}
	
	public boolean put_ring(){
		return true;
	}
	
}
