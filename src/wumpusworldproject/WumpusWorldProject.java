/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworldproject;

import Logic.ActionManagment.IActionManager;
import Logic.ActionManagment.SimpleActionManager;
import Logic.InferenceAlgoritms.AbstractInferenceAlgorithm;
import Logic.WumpusWorldGame;
import Models.Agent;
import Models.BaseWorkSpace;
import Models.EmptyCellProperty;
import Models.IBaseCellProperty;
import Models.Roles.Pit;
import Models.Roles.Wumpus;
import generated.KnowledgeBases;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 *
 * @author admin
 */
public class WumpusWorldProject {

    
    public static void main(String[] args) {
        
        
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
        Set<Class<? extends AbstractInferenceAlgorithm>> allAlgoritms = reflections.getSubTypesOf(AbstractInferenceAlgorithm.class);
        
        IActionManager manager = new SimpleActionManager(); 
        // Init kBase
        
        KnowledgeBases kBase = new KnowledgeBases();
        IBaseCellProperty cellProperty = new EmptyCellProperty();
        List<String> sentences = cellProperty.getSentences(0, 0);
        for (String sentence: sentences) {
            kBase.addSentence(sentence);
        }
        
        cellProperty = new Pit();
        sentences = cellProperty.getSentences(0, 1);
        for (String sentence: sentences) {
            kBase.addSentence("!" + sentence);
        }
        
        cellProperty = new Wumpus();
        sentences = cellProperty.getSentences(0, 1);
        for (String sentence: sentences) {
            kBase.addSentence("!" + sentence);
        }
        
        cellProperty = new Pit();
        sentences = cellProperty.getSentences(1, 0);
        for (String sentence: sentences) {
            kBase.addSentence("!" + sentence);
        }
        
        cellProperty = new Wumpus();
        sentences = cellProperty.getSentences(1, 0);
        for (String sentence: sentences) {
            kBase.addSentence("!" + sentence);
        }
        
        for (String sentence: kBase.getSentences()) {
            System.out.println(sentence);
        }
        
        Agent agent = new Agent(kBase, manager);
        BaseWorkSpace workSpace  = new BaseWorkSpace();
        
        WumpusWorldGame game = new WumpusWorldGame(workSpace, agent);
        game.starteGame();
        game.simulateGame();
    }
}
