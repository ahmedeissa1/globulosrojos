package jcolibri.test.quiniela;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.DataBaseConnector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.evaluation.Evaluator;
import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import jcolibri.evaluation.evaluators.LeaveOneOutEvaluator;
import jcolibri.evaluation.evaluators.SameSplitEvaluator;
import jcolibri.exception.AttributeAccessException;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.DisplayCasesTableMethod;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.test8.EvaluableApp;

public class Quiniela implements jcolibri.cbraplications.StandardCBRApplication 
{
	//Connector object
	Connector _connector;
	//CaseBase object
	CBRCaseBase _caseBase;
	
	private Log log;
	
	public static void main(String[] args)
	{
		Quiniela test = new Quiniela();
		try {
			test.configure();
			test.preCycle();
			
			 //aqui metemos la GUI
            CBRQuery query = new CBRQuery();
            query.setDescription(new Casos());
            do
            {
                    ObtainQueryWithFormMethod.obtainQueryWithoutInitialValues(query, null, null);
                    test.cycle(query);
            }while (JOptionPane.showConfirmDialog(null, "Continuar?")==JOptionPane.OK_OPTION);
			
			
			
			
			
			//Estos se elegiran segun el comboBox
			
			//test.HoldOutEvaluation();
			//test.LeaveOneOutEvaluation();
			//test.SameSplitEvaluation();
		
			
			
			
		} catch (ExecutionException e) {
			org.apache.commons.logging.LogFactory.getLog(Quiniela.class).error(e);
		}
	}
	
	@Override
	public void configure() throws ExecutionException {
		try
		{
			//Crear el conector con la base de casos
//			_connector = new DataBaseConnector();
			_connector = new PlainTextConnector();
			//Inicializar el conector con su archivo xml de configuracion
			_connector.initFromXMLfile(jcolibri.util.FileIO.findFile("jcolibri/test/quiniela/plaintextconfig.xml"));
			//La organizacion en la memoria ser� lineal
			_caseBase = new LinealCaseBase();
		} catch (Exception e)
		{
			throw new ExecutionException(e);
		}
		log = org.apache.commons.logging.LogFactory.getLog(this.getClass());
		
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		
		//Para configurar el KNN se utiliza un objeto NNCONfig
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
//		simConfig.setDescriptionSimFunction(new StandardGlobalSimilarityFunction() {
//			
//			@Override
//			public double computeSimilarity(double[] values, double[] weigths,
//					int numberOfvalues) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		});
		
		Attribute equipoLocal = new Attribute("equipoLocal",Casos.class);
		Attribute equipoVisitante = new Attribute("equipoVisitante",Casos.class);
		Attribute puntosEquipoLocal = new Attribute("puntosEquipoLocal",Casos.class);
		Attribute puntosEquipoVisitante = new Attribute("puntosEquipoVisitante",Casos.class);
		Attribute diferenciaPuntos = new Attribute("diferenciaPuntos",Casos.class);
		Attribute posicionEquipoLocal = new Attribute("posicionEquipoLocal",Casos.class);
		Attribute posicionEquipoVisitante = new Attribute("posicionEquipoVisitante",Casos.class);
		Attribute golesAFavorEquipoLocal = new Attribute("golesAFavorEquipoLocal",Casos.class);
		Attribute golesEnContraEquipoLocal = new Attribute("golesEnContraEquipoLocal",Casos.class);
		Attribute golesAFavorEquipoVisitante = new Attribute("golesAFavorEquipoVisitante",Casos.class);
		Attribute golesEnContraEquipoVisitante = new Attribute("golesEnContraEquipoVisitante",Casos.class);
		Attribute porcentajeGanagadosLocal = new Attribute("porcentajeGanagadosLocal",Casos.class);
		Attribute porcentajeGanagadosVisitante = new Attribute("porcentajeGanagadosVisitante",Casos.class);
		Attribute resultadoLocal = new Attribute("resultadoLocal",Casos.class);
		Attribute resultadoVisitante = new Attribute("resultadoVisitante",Casos.class);
		
		//Fijamos las funciones de similitud locales
		simConfig.addMapping(equipoLocal,new Equal());
		simConfig.setWeight(equipoLocal, 1.0);
		simConfig.addMapping(puntosEquipoLocal,new Equal());
		simConfig.setWeight(puntosEquipoLocal, 1.0);
		simConfig.addMapping(puntosEquipoVisitante,new Equal());
		simConfig.setWeight(puntosEquipoVisitante, 1.0);
		simConfig.addMapping(equipoVisitante,new Equal());
		simConfig.setWeight(equipoVisitante, 1.0);
		simConfig.addMapping(diferenciaPuntos,new Equal());
		simConfig.setWeight(diferenciaPuntos, 0.6);
		simConfig.addMapping(posicionEquipoLocal,new Interval(20));
		simConfig.setWeight(posicionEquipoLocal, 0.2);
		simConfig.addMapping(posicionEquipoVisitante,new Interval(20));
		simConfig.setWeight(posicionEquipoVisitante, 0.2);
		simConfig.addMapping(golesAFavorEquipoLocal,new Equal());
		simConfig.setWeight(golesAFavorEquipoLocal, 0.3);
		simConfig.addMapping(golesEnContraEquipoLocal,new Equal());
		simConfig.setWeight(golesEnContraEquipoLocal, 0.3);
		simConfig.addMapping(golesAFavorEquipoVisitante,new Equal());
		simConfig.setWeight(golesAFavorEquipoVisitante, 0.3);
		simConfig.addMapping(golesEnContraEquipoVisitante,new Equal());
		simConfig.setWeight(golesEnContraEquipoVisitante, 0.3);
		simConfig.addMapping(porcentajeGanagadosLocal,new Interval(100.0));
		simConfig.setWeight(porcentajeGanagadosLocal, 0.7);
		simConfig.addMapping(porcentajeGanagadosVisitante,new Interval(100.0));
		simConfig.setWeight(porcentajeGanagadosVisitante, 0.7);
		simConfig.addMapping(resultadoLocal,new Interval(100));
		simConfig.setWeight(resultadoLocal, 0.7);
		simConfig.addMapping(resultadoVisitante,new Interval(100));
		simConfig.setWeight(resultadoVisitante, 0.7);
		
		log.info("Query: "+ query.getDescription());
		
		//Ejecutamos la recuperacion por vecino mas proximo
		
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		//esto es para las evaluaciones
	    //// Now we add the similarity of the most similar case to the serie "Similarity".
		//Evaluator.getEvaluationReport().addDataToSeries("Similarity", new Double(eval.iterator().next().getEval()));
		
		//imprimimos los k mejores casos
		eval = SelectCases.selectTopKRR(eval, 5);
		
		//Imprimimos el resultado del k-NN y obtenemos la lista de casos recuperados
		
		Collection<CBRCase> casos = new ArrayList<CBRCase>();
		System.out.println("Casos Recuperados");
		for(RetrievalResult nse: eval)
		{
			System.out.println(nse);
			casos.add(nse.get_case());
		}
		//Aqui se incluiria el codigo para adaptar la solucion
		
		//Solamente mostramos el resultado
		DisplayCasesTableMethod.displayCasesInTableBasic(casos);
	
		
		
		//votacion basica
		MajorityVotingMethod prueba = new MajorityVotingMethod();
		Prediction pre;
	    pre = prueba.getPredictedClass(eval);
		System.out.println("Votacion Basica "+pre.Classification.toString()+" __ "+pre.confidence);
		
		
		//votacion ponderada
		SimilarityWeightedVotingMethod prueba2 = new SimilarityWeightedVotingMethod();
		pre = prueba2.getPredictedClass(eval);
		System.out.println("Votacion ponderada "+pre.Classification.toString()+" __ "+pre.confidence);
				
	}

	@Override
	public void postCycle() throws ExecutionException {
		this._caseBase.close();
		
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		//Cargar los casos desde el conector a la base de casos
		_caseBase.init(_connector);
		//Imprimir los casos leidos
		java.util.Collection<CBRCase> cases = _caseBase.getCases();
		for(CBRCase c: cases)
				System.out.println(c);
		return _caseBase;
	}
	
	public void HoldOutEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), HoldOutEvaluator.class);
	
    	// Example of the Hold-Out evaluation
		
		HoldOutEvaluator eval = new HoldOutEvaluator();
		eval.init(new Quiniela());
		eval.HoldOut(5, 1);
		
		System.out.println(Evaluator.getEvaluationReport());
		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
	public void LeaveOneOutEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), LeaveOneOutEvaluator.class);
	
    	//Example of the Leave-One-Out evaluation
		
		LeaveOneOutEvaluator eval = new LeaveOneOutEvaluator();
		eval.init(new Quiniela());
		eval.LeaveOneOut();
		
		System.out.println(Evaluator.getEvaluationReport());
		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
	public void SameSplitEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), SameSplitEvaluator.class);
	
    	// Example of the Same-Split evaluation
		
		SameSplitEvaluator eval = new SameSplitEvaluator();
		eval.init(new Quiniela());
		eval.generateSplit(5, "split1.txt");
		eval.HoldOutfromFile("split1.txt");
		
		System.out.println(Evaluator.getEvaluationReport());
		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
}
