package starSailor;

public class Fighter extends EnemyShip {

	private Carrier commander;
	
	public Fighter(int x, int y, Carrier commander){

		super(x, y, EnemyShip.shipClass.FIGHTER);
		

		this.commander = commander;
		
	}
	
	
}
