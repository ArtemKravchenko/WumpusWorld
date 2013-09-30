package Logic;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
/**
 *
 * @author admin
 */
public class Graph<VertexType> extends GraphBasedObject<VertexType> {   
    // Constructors
    protected List<List<Integer>> _edges;
    protected List<Map<Integer,Double>> _weights;
    protected GraphPathAlgorithms _algoritms;
    
    public Graph()
    {
        this._vertices = new ArrayList<>();
        this._edges = new ArrayList<>();
        this._weights = new ArrayList<Map<Integer,Double>>();
    }
    
    public Graph(List<VertexType> vertecies, List<List<Integer>> edges)
    {
        this._vertices = vertecies;
        this._edges = edges;
        this._weights = new ArrayList<Map<Integer,Double>>();
    }
    
    // Public methods
    
    // Methods that change graph
    
    // Add vertex to graph
    @Override
    public void addVertex(VertexType vertex)
    {
        this._vertices.add(vertex);
    }
    
    // Add edge between vertecies
    @Override
    public void addEdge(int vertex, int neighbor)
    {
        if (this._vertices.size() > vertex) {
            if (this._vertices.size() > neighbor) {
                if (this._edges.size() > vertex) {
                    if (this._edges.get(vertex).indexOf(neighbor) == -1) {
                        this._edges.get(vertex).add(neighbor);
                    }
                } else {
                    for (int i = this._edges.size(); i < vertex + 1; i++) {
                        this._edges.add(new ArrayList<Integer>());
                        if (i == vertex) {
                            this._edges.get(this._edges.size() - 1).add(neighbor);
                        }
                    }
                }
            }
        }
    }
    
    // Add edge between vertecies
    public void addEdge(int vertex, ArrayList<Integer> neighborhood)
    {
        for (Integer neighbor: neighborhood) {
            this.addEdge(vertex, neighbor);
        }
    }
    
    // Add edge between vertecies
    public void addEdge(VertexType vertex, VertexType neighbor)
    {
        int vertexIndex = this._vertices.indexOf(vertex);
        int edgeIndex = this._vertices.indexOf(neighbor);
        
        if (vertexIndex == -1) {
            this._vertices.add(vertex);
            vertexIndex = this._vertices.indexOf(vertex);
        }
        if (edgeIndex == -1) {
            this._vertices.add(neighbor);
            edgeIndex = this._vertices.indexOf(neighbor);
        }
        
        this.addEdge(vertexIndex, edgeIndex);
    }
    
    // Add edge between vertecies
    public void addEdge(VertexType vertex, List<VertexType> neighborhood)
    {
        for (VertexType neighbor : neighborhood) {
            this.addEdge(vertex, neighbor);
        }
    }
    
    // Set weight to edge
    @Override
    public void setWeight(int startVertex, int endVertex, double weight)
    {
        if (this._vertices.size() > startVertex) {
            if (this._vertices.size() > endVertex) {
                if (this._weights.size() > startVertex) {
                    if (this._weights.get(startVertex) == null) {
                        this._weights.set(startVertex, new HashMap<Integer, Double>());
                    }
                    // !!! Possibly wrong behavior
                    Map<Integer, Double> tmpMap = this._weights.get(startVertex);
                    tmpMap.put(endVertex, weight);
                } else {
                    for (int i = this._weights.size(); i < startVertex + 1; i++) {
                        this._weights.add(new HashMap<Integer, Double>());
                        if (i == startVertex) {
                            Map<Integer, Double> tmpMap = this._weights.get(startVertex);
                            tmpMap.put(endVertex, weight);
                        }
                    }
                }
            }
        }
    }
    
    // Set weight to edge
    public void setWeight(VertexType startVertex, VertexType endVertex, double weight)
    {
        int startVertexIndex = this._vertices.indexOf(startVertex);
        int endVertexIndex = this._vertices.indexOf(endVertex);

        // Check verteices for existing in Graph
        if (startVertexIndex == -1) this._vertices.add(startVertex);
        if (endVertexIndex == -1) this._vertices.add(endVertex);

        // Check edge for existing in graph
        if (this._edges.get(startVertexIndex) == null) {
            this.addEdge(startVertex, endVertex);
        } else {
            if (!this._edges.get(startVertexIndex).contains(endVertexIndex))
                    this.addEdge(startVertex, endVertex);
        }
        
        this.setWeight(startVertexIndex, endVertexIndex, weight);
    }
    
    // 'Remove' methods
    
    @Override
    public void removeVertex(int vertex) {
        this._vertices.remove(vertex);
        this.removeAllEdgeForVertex(vertex);
    }
    
    public void removeVertex(VertexType vertex) {
        this._vertices.remove(vertex);
        this.removeAllEdgeForVertex(vertex);
    }
    
    @Override
    public void removeEdge(int startVertex, int endVertex) {
        List<Integer> array = this._edges.get(startVertex);
        if (array != null && array.size() > 0) {
            array.remove(endVertex);
        }
        
        Map<Integer, Double> weights = this._weights.get(startVertex);
        if (weights != null && weights.size() > 0) {
            weights.remove(endVertex);
        }
    }
    
    public void removeEdge(VertexType startVertex, VertexType endVertex) {
        int startVertexIndex = this._vertices.indexOf(startVertex);
        int endVertexIndex = this._vertices.indexOf(endVertex);
        
        if (startVertexIndex != -1 && endVertexIndex != -1) {
            this.removeEdge(startVertexIndex, endVertexIndex);
        }
    }
    
    public void removeAllEdgeForVertex(int vertex) {
        this._edges.remove(vertex);
        this._weights.remove(vertex);
        
        List<Integer> objectsForRemoving = new ArrayList<>();
        // Finding connection to current vertex from another vertex
        for (int i = 0; i < this._edges.size(); i++) {
            for(Integer key : this._edges.get(i)) {
                if (key == vertex) {
                    objectsForRemoving.add(i);
                }
            }
        }
        for (Integer index : objectsForRemoving) {
            this.removeEdge(index, vertex);
        }
        
        objectsForRemoving = new ArrayList<>();
        for (int i = 0; i < this._weights.size(); i++) {
            for(Map.Entry pair : (this._weights.get(i)).entrySet()) {
                if (pair.getKey() == vertex) {
                    objectsForRemoving.add(i);
                }
            }
        }
        for (Integer index : objectsForRemoving) {
            this.removeEdge(index, vertex);
        }
    }  
    
    public void removeAllEdgeForVertex(VertexType vertex) {
        int vertexIndex = this._vertices.indexOf(vertex);
        this.removeAllEdgeForVertex(vertexIndex);
    }
    
    // Methods that return some information about graph
    
    // Get list of vertecies that connect with given vertex by edge
    public List<VertexType> getNeighborhoodOfVertex(VertexType vertex)
    {
        List<VertexType> neighborhood = new ArrayList<>();
        int vertexIndex = _vertices.indexOf(vertex);

        for (Integer edge : this._edges.get(vertexIndex)) {
            neighborhood.add(_vertices.get(edge));
        }

        return neighborhood;
    }
    
    // Return adgency matrix
    public double[][] getAdgencyMatrix()
    {
        double[][] adgencyMatrix = new double[this._vertices.size()][this._vertices.size()];

        for (int i = 0; i < this._vertices.size(); i++) {
            for (int j = 0; j <  this._vertices.size(); j++) {
                double weight = this._weights.get(i).get(j);
                adgencyMatrix[i][j] = (weight != 0) ? weight : Double.POSITIVE_INFINITY;
            }
        }

        return adgencyMatrix;
    }
    
    public double[][] getAdgencyBinaryMatrix()
    {
        double[][] adgencyMatrix = new double[this._vertices.size()][this._vertices.size()];

        for (int i = 0; i < this._vertices.size(); i++) {
            for (int j = 0; j <  this._vertices.size(); j++) {
                double weight = this._weights.get(i).get(j);
                adgencyMatrix[i][j] = (weight != 0) ? 1 : 0;
            }
        }

        return adgencyMatrix;
    }
    
    @Override
    public List<VertexType> getVertices() {
        return this._vertices;
    }
    
    @Override
    public double weightBetweenTwoVertices(VertexType firstVertex, VertexType secondVertex) {
        int indexOfFirstVertex = this._vertices.indexOf(firstVertex);
        Map<Integer, Double> map = this._weights.get(indexOfFirstVertex);
        if (map == null) {
            return -1;
        }
        
        int indexOfSecondVertex = this._vertices.indexOf(secondVertex);
        double weight = map.get(indexOfSecondVertex);
        
        return weight;
    }
    
    public Queue<VertexType> getShortesPath(VertexType source, VertexType targget) {
        if (this._algoritms == null) {
            this._algoritms = new GraphPathAlgorithms(this);
        }
        Map<String, List> routeMap = this._algoritms.dijstraAlgorithm(source);
        Queue<VertexType> path = this._algoritms.rebuildShortesPathFromSourceToTarget(source, targget, routeMap.get(GraphPathAlgorithms.MIN_PATH_VERTICES_ARRAY));
        path.add(targget);
        return path;
    }
}