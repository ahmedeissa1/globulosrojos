package grupo14.players;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class MegaDefender extends Role{

	public MatchStateUtils matchStateUtils;
	String role="megaDefensor";
	UltraDefenderMLP mlp;
	
	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString(role);
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		
		//Inicializacion de la red neuronal para el ultra defensor
		mlp = new UltraDefenderMLP();
		mlp.readFromFile("training/MLP/UltraDefender");
		
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		matchStateUtils.getMatchState(this.worldAPI);
		matchStateUtils.matchState.accionARealizar(worldAPI,role);
		
		// El ultradefender muestra el movimiento que le aconseja la red neuronal + confianza (testing)
		worldAPI.setDisplayString(mlp.getNextMove(worldAPI));
		
		return WorldAPI.ROBOT_OK;
	}
	
	/**
	 * Pone al jugador en el estado que se le pasa como parametro
	 * @param estado: Estado al que debe pasar el jugador
	 */
	public void pasaAEstado(MatchState estado){
		matchStateUtils.setMatchState(estado);
	}
	
	/**
	 * Ordena al jugador que realice la accion recibida como parámetro
	 * @param accion: Accion que debe realizar el jugador
	 */
	public void realizaAccion(String accion){
		
	}
}
