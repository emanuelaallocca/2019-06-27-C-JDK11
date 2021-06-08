package it.polito.tdp.crimes.model;

import it.polito.tdp.crimes.db.EventsDao;

import java.time.LocalDate;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Model {
	
	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	//private Map<String, Str>
	List<String> vertici;
	List<Adiacenze> archi;
	
	public Model() {
		dao = new EventsDao();
		grafo = new SimpleWeightedGraph(DefaultWeightedEdge.class);
	}
	
	
	public List<String> getCategorie(){
		return dao.getCategorie()
				;
	}
	public List<LocalDate> getDate(){
		return dao.getDate();
	}
	
	public void creaGrafo(String categoria, LocalDate giorno) {
		vertici = dao.getVertici(categoria, giorno);
		Graphs.addAllVertices(this.grafo, vertici);
		archi = dao.getArchi(categoria, giorno);
		for (Adiacenze a : archi)
			Graphs.addEdgeWithVertices(this.grafo, a.getId1(), a.getId2(), (double)a.getPeso());
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenze> getArchiMediani(){
		int max = archi.get(0).getPeso();
		int min = archi.get(0).getPeso();
		
		for (Adiacenze a : archi)
		{
			if (a.getPeso() < min)
				min = a.getPeso();
			else if(a.getPeso() > max)
				max = a.getPeso();
		}
		List <Adiacenze> ad = new ArrayList<>();
		double mediana = (max+min)/2;
		
		for (Adiacenze a : archi)
		{
			if (a.getPeso() < mediana)
				ad.add(a);
		}
		
		return ad;
		
	}

	public List<Adiacenze> getArchi(){
		return archi;
	}

	public List<String> getVicini(Adiacenze a){
		String partenza = a.getId1();
		String arrivo = a.getId2();
		DefaultWeightedEdge e = this.grafo.getEdge(partenza, arrivo);
		List <String> vicini = Graphs.neighborListOf(this.grafo, partenza);
		return vicini;
	}
	
	List <String> migliore;
    double  pesoMigliore;
	List<String> parziale;
	double pesoParziale;
	
	public List<String> getCammino(Adiacenze a){
		String partenza = a.getId1();
		String arrivo = a.getId2();
		migliore = new ArrayList<>(); 
		pesoMigliore =0;
		parziale = new ArrayList<>();
		pesoParziale = 0;
		parziale.add(partenza);
		cerca (arrivo, 0, parziale );
		return migliore;
	}

	private void cerca(String arrivo, int livello, List<String> parziale) {
		
		String ultimo = parziale.get(livello);
		if(ultimo.equals(arrivo)) {
			pesoParziale = calcolaPeso(parziale);
			
			if(pesoParziale > pesoMigliore) {
				migliore = new ArrayList<>(parziale); 
				pesoMigliore = pesoParziale;
		 }
		}
			if (livello == archi.size())
				return;
			
		List <String> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		
		for (String s : vicini) {
//			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(0), s);
//			DefaultWeightedEdge e1 = this.grafo.getEdge(parziale.get(livello), s);
			if(!parziale.contains(s)) {
			parziale.add(s);
			cerca(arrivo, livello+1, parziale);
			parziale.remove(parziale.size()-1);
			}
		 }
	 }


	private double calcolaPeso(List<String> parziale) {
	    double sum = 0;
		for (int i=1; i<parziale.size(); i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i-1), parziale.get(i));
			double d = this.grafo.getEdgeWeight(e);
			sum = sum + (int)d; 
		}
		return sum;
	}
}
