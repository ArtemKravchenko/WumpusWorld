package Logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class GraphPathAlgorithms<VertexType> {
    
    // Constants
    public static final String MIN_PATH_VALUES = "minPathValues";
    public static final String MIN_PATH_MATRIX = "minPathMatrix";
    public static final String MIN_PATH_VALUES_ARRAY = "minPathValuesArray";
    public static final String MIN_PATH_VERTICES_ARRAY = "minPathVerticesArray";
    
    public static final String MAX_DISTANCE_VALUE = "maxDistanceValue";
    public static final String MAX_DISTNACE_TO_VERTEX = "maxDistanceToVertex";
    
    public static final String RADIUS = "radius";
    public static final String CENTR = "centr";
    public static final String DIAMRTER = "diameter";
    
    private Graph _graph;
    
    public GraphPathAlgorithms(Graph graph) {
        this._graph = graph;
    }
    
    // Floyd algorithm that returned matrix of distance in graph
    public Map<String, Object> floydAlgorithm()
    {
        double[][] minPathArray = this._graph.getAdgencyMatrix();
        double[][] pathMatrix = new double[this._graph._vertices.size()][this._graph._vertices.size()];
        
        for (int k = 0; k < this._graph._vertices.size(); k++) {
            for (int i = 0; i < this._graph._vertices.size(); i++) {
                for (int j = 0; j < this._graph._vertices.size(); j++) {
                    if (minPathArray[i][j] > minPathArray[i][k] + minPathArray[k][j]) {
                        minPathArray[i][j] = minPathArray[i][k] + minPathArray[k][j];
                        pathMatrix[i][j] = k;
                    } else {
                        pathMatrix[i][j] = 0;
                    }
                }
            }
        }
        
        Map<String, Object> minPath = new HashMap<>();
        minPath.put(MIN_PATH_VALUES, minPathArray);
        minPath.put(MIN_PATH_MATRIX, pathMatrix);
        
        return minPath;
    }
 
    public Map<String, List> dijstraAlgorithm(VertexType source) {
        // Array of distance from source vertex to another
        List<Double> distanceArray = new ArrayList<>();
        List<VertexType> previousArray = new ArrayList<>();
        List<VertexType> vertices = this._graph.getVertices();
        
        int indexOfSourceVertex = vertices.indexOf(source);
        
        for (VertexType vertex: vertices) {
            distanceArray.add(Double.MAX_VALUE);
            previousArray.add(null);
        }
        
        distanceArray.set(indexOfSourceVertex, 0.0);
        while(vertices.size() > 0) {
            VertexType u = this.smallestDistanceInArray(vertices, distanceArray); // Vertex with smallest distance in distanceArray
            vertices.remove(u);
            
            int indexOfU = this._graph.getVertices().indexOf(u);
            if (distanceArray.get(indexOfU) == Double.MAX_VALUE) {
                break;
            }
            
            List<VertexType> neighborhood = this._graph.getNeighborhoodOfVertex(u);
            for (VertexType neighbor : neighborhood) {
                if (vertices.contains(neighbor)) {
                    int indexOfNeighbor = this._graph.getVertices().indexOf(neighbor);
                    double alt = distanceArray.get(indexOfU) + this._graph.weightBetweenTwoVertices(u, neighbor);
                    if (alt < distanceArray.get(indexOfNeighbor)) {
                        distanceArray.set(indexOfNeighbor, alt);
                        previousArray.set(indexOfNeighbor, u);
                        vertices.remove(neighbor);
                    }
                }
            }
        }
        
        Map<String, List> returnMap = new HashMap<>();
        returnMap.put(MIN_PATH_VALUES_ARRAY, distanceArray);
        returnMap.put(MIN_PATH_VERTICES_ARRAY, previousArray);
        
        return returnMap;
    }
    
    private VertexType smallestDistanceInArray(List<VertexType> array, List<Double> distanceArray){
        double minValue = Double.MAX_VALUE;
        int indexWithMinValue = 0;
        
        for (int i = 0; i < array.size(); i++) {
            int indexOfVertex = ((List<VertexType>)this._graph.getVertices()).indexOf(i);
            double distance = distanceArray.get(i);
            if (distance < minValue) {
                minValue = distance;
                indexWithMinValue = i;
            }
        }
        
        return ((List<VertexType>)this._graph.getVertices()).get(indexWithMinValue);
    }
    
    public List<VertexType> rebuildShortesPathFromSourceToTarget(VertexType source, VertexType target, List<VertexType> previousArray) {
        List<VertexType> sequence = new ArrayList<>();
        List<VertexType> vertices = this._graph.getVertices();
        
        int indexOfTarget = vertices.indexOf(target);
        while (previousArray.get(indexOfTarget) != null) {
            sequence.set(0,previousArray.get(indexOfTarget));
            indexOfTarget = vertices.indexOf(previousArray.indexOf(indexOfTarget));
        }
        
        return sequence;
    }
    
    // Centre, radius and diametr
    
   public Map<String, Object> eccenticity(VertexType vertex)
   {
       int maxIndex = 0;
       double maxValue = 0;
       Map<String, Object> eccentricity = new HashMap<>();
       
       // Get matrix of all distance
       double[][] minPathArray =  (double[][]) this.floydAlgorithm().get(MIN_PATH_VALUES);
       int vertexIndex = this._graph._vertices.indexOf(vertex);
       
       // Finding eccentricity for current vertex
       for (int i = 0; i < minPathArray[vertexIndex].length; i++) {
           if (minPathArray[vertexIndex][i] > maxValue) {
               maxValue = minPathArray[vertexIndex][i];
               maxIndex = i;
           }
 
       }
       
       eccentricity.put(MAX_DISTANCE_VALUE, maxValue);
       eccentricity.put(MAX_DISTNACE_TO_VERTEX, this._graph._vertices.get(maxIndex));
       
       return eccentricity;
   }
   
   public Map<String, Object> centerAndRadius()
   {
       Map<String, Object> centerMap = new HashMap<>();
       List<Map> eccentricities = new ArrayList<>();
       List<VertexType> center = new ArrayList<>();
       double radius = Double.POSITIVE_INFINITY;
       
       // Finding min eccentricity, it will be radius
       for (Object vertex : this._graph._vertices) {
           Map eccentricity = this.eccenticity(((VertexType)vertex));
           eccentricities.add(eccentricity);
           if (radius > ((double)eccentricity.get(MAX_DISTANCE_VALUE))) {
               radius = ((double)eccentricity.get(MAX_DISTANCE_VALUE));
           }
       }
       
       // Add to center array verticies that have min eccentricity
       for (int i = 0; i < eccentricities.size(); i++) {
           Map eccentricity = eccentricities.get(i);
           if (eccentricity.get(MAX_DISTANCE_VALUE) == radius) {
               center.add(((VertexType)this._graph._vertices.get(i)));
           }
       }
       
       centerMap.put(CENTR, center);
       centerMap.put(RADIUS, radius);
       
       return centerMap;
   }
   
   public Map<String, Object> diameter()
   {
       Map<String, Object> diameterMap = new HashMap<>();
       
       double diameter = 0;
       for (Object vertex : this._graph._vertices) {
           Map eccentricity = this.eccenticity(((VertexType)vertex));
           if (diameter < ((float)eccentricity.get(MAX_DISTANCE_VALUE))) {
               diameter = ((float)eccentricity.get(MAX_DISTANCE_VALUE));
           }
       }
       
       return diameterMap;
   }
}
