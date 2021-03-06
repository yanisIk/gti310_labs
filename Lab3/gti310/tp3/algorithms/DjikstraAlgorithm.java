package gti310.tp3.algorithms;

import java.util.ArrayList;

import gti310.tp3.models.MatriceGraphe;

/**
 * Classe utilitaire permetant d'executer l'algorithme de Dijkstra
 * sur un Graphe
 * @author Yanis
 * Base sur : https://www.swila.be/files/labo-ppm/PPM0708.pdf
 */
public class DjikstraAlgorithm {

	/**
	 * Initialiser le graphe de sortie et la queue
	 * @param graph
	 * @param originId
	 * @return le queue contenant toutes les villes
	 */
	private static ArrayList<Integer> init(int [][] graph, int originId){
		
		ArrayList<Integer> q = new ArrayList<Integer>(graph.length);
		
		for(int i=0; i<graph.length; i++){
			graph[i][0] = MatriceGraphe.INFINI; //Distance inconnue de la source 'a i
			graph[i][1] = -1;	//Noeud precedent dans le chemin optimal indefini
			q.add(i); //Ajouter tous les noeuds 'a la queue
		}
		
		graph[originId][0] = 0; //Distance de source 'a source
		graph[originId][1] = -1; //Sommet precedent 'a la source indefini
		
		return q;
	}
	
	/**
	 * Algorithme de Dijkstra qui permettra de trouver les meilleurs
	 * chemins 'a partir d'un point de depart dans un graphe
	 * @param originalGraph : graphe representant tous les chemins (matrice de poids)
	 * @param originId : point de depart
	 * @return tableau de chemins optimaux
	 */
	public static int[][] computePaths(int[][] originalGraph, int originId){
		
		// [i][0] Distance , [i][1] Parent o'u i = numero du noeud
		int[][] outGraph = new int[originalGraph.length][2];
		//Noeuds 'a visiter
		ArrayList<Integer> vertexQueue = init(outGraph, originId);
		
		//Tant qu'il reste des noeuds 'a visiter
		while(vertexQueue.isEmpty() == false){
			
			Integer nearestVertex = findShortestPath(vertexQueue, outGraph);
			vertexQueue.remove(nearestVertex);

			for (int i = 0; i < originalGraph.length; i++) {
				
				int distance = originalGraph[nearestVertex][i];
				
				if(distance != MatriceGraphe.INFINI){
					relax(nearestVertex, i, originalGraph, outGraph);
				}
			}
		}
		
		return outGraph;
	}

	/**
	 * Trouve la ville la plus proche de la source
	 * @param vertexQueue
	 * @param outGraph
	 * @return le noeud le plus proche
	 */
	private static Integer findShortestPath(ArrayList<Integer> vertexQueue, int[][] outGraph) {
		
		int shortestDistance = MatriceGraphe.INFINI;
		int shortestPathVertex = 0;
		
		//Visiter tous les voisins
		for(Integer vertex : vertexQueue) {
			
			int newShortestDistance = outGraph[vertex][0];
	
			if(newShortestDistance < shortestDistance){
				shortestDistance = newShortestDistance;
				shortestPathVertex = vertex;		
			}

		}

		return shortestPathVertex;
	}
	
	/**
	* Assigne les valeurs des meilleurs chemins dans le tableau de sortie
	* @param u Noeud avec plus petite valeur
	* @param v index dans le graphe original
	* @param originalGraph
	* @param outGraph tableau de sortie
	*/
	private static void relax(int u, int v, int[][] originalGraph, int[][] outGraph){
		
		int distance = outGraph[u][0] + originalGraph[u][v];
		
		if(distance < outGraph[v][0]){
			outGraph[v][0] = distance;
			outGraph[v][1] = u;
		}
	}
	
	
}
