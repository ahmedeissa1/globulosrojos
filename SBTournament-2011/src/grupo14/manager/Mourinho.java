package grupo14.manager;

import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import grupo14.aprendizaje.CBR.voting.Prediction;
import grupo14.aprendizaje.redNeuronal.TeamMLP;
import grupo14.players.*;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.CBRUtils;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.TeamManager;
import teams.rolebased.WorldAPI;

public class Mourinho extends TeamManager {

	Ordenes ordenesDeEquipo;
	WorldAPI goalkeepersWorldAPI;
	CBRUtils CBR;
	TeamMLP teamMLP;
	public MatchStateUtils matchStateUtils;
	
	private String modoActual;
	private long ticks;
	private static final long MAX_TICKS_CBR = 150;
	private static final long MAX_TICKS_RN = 150;
	

	@Override
	public int configure() {
		ordenesDeEquipo = Ordenes.getInstance();
		// Start the CBR system
		this.CBR = new CBRUtils();
		// Start the Perceptron system
		this.teamMLP = new TeamMLP();
		((MegaDefender)super.robots[1]).setMLP(teamMLP.getUltraDefender());
		((Defender)super.robots[2]).setMLP(teamMLP.getDefender());
		((Striker)super.robots[3]).setMLP(teamMLP.getStriker());
		((FuckingStriker)super.robots[4]).setMLP(teamMLP.getUltraStriker());
		// Start the match state utils class
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		
		modoActual = "ME";
		ticks = 0;
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public int takeStep() {
		// Esto se hace aqui porque en configure el portero todavia tiene su
		// worldAPI a null
		if (this.goalkeepersWorldAPI == null)
			this.goalkeepersWorldAPI = ((Goalkeeper) super.robots[0])
					.getWorldApi();
		matchStateUtils.getMatchState(this.goalkeepersWorldAPI);
		
		if (puedeCambiarDeModo()) {
			// Se consulta al CBR
			Prediction prediccion = utilizarCBR();
			double confianzaCBR = (prediccion == null)? 0.0 : prediccion.getConfidence();
			// Se consulta la red neuronal
			double confianzaRN = teamMLP.getAverageConfidence();
			
			switch (decideEntreCBRoRN(confianzaCBR, confianzaRN)) {
			case 0://No utilizar nada
				//Se pone el estado a null para que cuando los jugadores consulten no tengan un estado al que pasar
				modoActual = "ME";
				ordenesDeEquipo.establecerEstado("null");
				break;
			case 1://RN
				modoActual = "RN";
				ordenesDeEquipo.setAccionesMLP();
				break;
			case 2://CBR
				modoActual = "CBR";
				ordenesDeEquipo.establecerEstado((String)prediccion.getClassification());
				break;
	
			default:
				break;
			}
			
			ticks = 0;
		}
		
		ticks++;
		return WorldAPI.ROBOT_OK;
	}

	private boolean puedeCambiarDeModo() {
		if (modoActual.equals("CBR"))
			return ticks > MAX_TICKS_CBR;
		else if (modoActual.equals("RN"))
			return ticks > MAX_TICKS_RN;
		else
			return true;
	}

	/**
	 * Consulta al CBR para que devuelva un estado al que pasar. IMPORTANTE: Si
	 * no se dan las circunstancias especificadas por needToCreateCase este
	 * metodo devolvera null
	 * 
	 * @return Una prediccion del CBR o null si no se cumplen las condiciones
	 *         para consultar el CBR
	 */
	private Prediction utilizarCBR() {
		// Si el sistema considera que debe crearse/leerse un nuevo caso
		if (this.CBR.needToCreateCase(this.goalkeepersWorldAPI,this.CBR.lastCase)) {
			// Hay que crear un nuevo caso
			CBRCase caso = this.CBR.crearCaso(this.goalkeepersWorldAPI,	this.matchStateUtils.matchState);
			// Se guarda el momento en que se ha leido el caso, para asegurar
			// que no
			// se cogen dos casos muy juntos
			this.CBR.lastCase = goalkeepersWorldAPI.getMatchTotalTime() - goalkeepersWorldAPI.getMatchRemainingTime();
			// Se hace la consulta a la base de casos para que esta nos devuelva
			// el caso mejor
			// y tomar una decision
//			try {
//				// Recuperar un caso para aplicar. Obtendremos el estado al que
//				// pasar y una confianza
//				return this.CBR.cbr.recuperarCaso(caso);
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
		}
		return null;
	}

	/**
	 * Decide que sistema es mejor utilizar para dar ordenes al equipo. La
	 * decision dependera de las confianzas recibidas tanto del CBR como de la
	 * RedNeuronal
	 * 
	 * @param confianzaCBR
	 *            : confianza devuelta por el CBR
	 * @param confianzaRN
	 *            confianza devuelta por la Red Neuronal
	 * @return un integer que representa la eleccion a tomar. 1 = utilizar RN, 2
	 *         = Utilziar CBR, 0 = Ninguna de las dos
	 */
	private int decideEntreCBRoRN(double confianzaCBR, double confianzaRN) {
		if (confianzaCBR < 0.6 && confianzaRN < 0.6) {
			return 0;
		} else if (confianzaCBR < confianzaRN)
			return 1;
		else
			return 2;
	}
}
