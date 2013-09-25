package Logic;


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
public abstract class GraphBasedObject<VertexType> {
    
    // Protected variables
    protected List<VertexType> _vertices;
    
    // Abstract methods
    public abstract void addVertex(VertexType vertex);
    public abstract void addEdge(int vertex, int neighbor);
    public abstract void setWeight(int startVertex, int endVertex, double weight);
    public abstract void removeVertex(int vertex);
    public abstract void removeEdge(int startVertex, int endVertex);
    
    // Getters
    public abstract List<VertexType> getVertices();
    public abstract double weightBetweenTwoVertices(VertexType firstVertex, VertexType secondVertex);
}
