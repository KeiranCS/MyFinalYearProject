package keiran.finalyearproject;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Keiran on 28/01/2016.
 */
public class EvolutionTest {

    @Test
    public void testMutateTreeBrackets() throws Exception {
        Evolution evolution = new Evolution();
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        for(int i =0; i < 500; i++) {
             x = evolution.mutateTree(x);
        }
        String tree = x.getTree();
        int count = 0;
        int leftBrackCount = 0;
        int rightBrackCount = 0;
        while(count < tree.length()){
            if(tree.charAt(count) == '['){
                leftBrackCount++;
            }else if(tree.charAt(count) == ']'){
                rightBrackCount++;
            }
            count++;
        }
        assertEquals("Not all square brackets are paired!", leftBrackCount, rightBrackCount);
    }

    @Test
    public void testMutateTreeParams(){
        Evolution evolution = new Evolution();
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        for(int i =0; i < 500; i++) {
            x = evolution.mutateTree(x);
        }
        int treeLength = x.getTree().length();
        int paramLength = x.getParams().size();
        assertEquals("Characters and parameters don't match after mutations!", treeLength, paramLength);
    }

    @Test
    public void testMutateTreeMatchingParams(){
        Evolution evolution = new Evolution();
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        for(int i =0; i < 500; i++) {
            x = evolution.mutateTree(x);
        }
        for(int i=0; i < x.getTree().length(); i++){
            if(x.getTree().charAt(i) == 'F'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) >= 0 && x.getParams().get(i) <= 10);
            }else if(x.getTree().charAt(i) == '+'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) <=25);
            }else if(x.getTree().charAt(i) == '-'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) <=25);
            }else{
                assertTrue("Mismatched parameter!", x.getParams().get(i) == 0);
            }
        }
    }

    @Test
    public void testRemoveDuplicates(){
        ArrayList<Character> symbols = new ArrayList<>();
        for(int i = 0; i <= 4; i++)
            symbols.add('F');
        Evolution e = new Evolution();
        symbols = e.removeDuplicates(symbols);
        ArrayList<Character> expected = new ArrayList<>();
        expected.add('F');
        assertArrayEquals("Does not remove all duplicates!", expected.toArray(), symbols.toArray());
    }

    @Test
    public void crossoverTreeBrackets() {
        Evolution evolution = new Evolution();
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        Tree y = new Tree(new char[]{'S', 'G', 'H', 'T', 'L'}, new String[]{"[+++G][---G]TS", "+H[-G]L", "-G[+H]L", "TL", "[-FFF][+FFF]F"}, "SLFFF", 9);
        Tree[] trees;

        for (int i = 0; i < 100; i++) {
            trees = evolution.crossover(x, y);
            x = trees[0];
            y = trees[1];
        }
        String tree = x.getTree();
        int count = 0;
        int leftBrackCount = 0;
        int rightBrackCount = 0;
        while(count < tree.length()){
            if(tree.charAt(count) == '['){
                leftBrackCount++;
            }else if(tree.charAt(count) == ']'){
                rightBrackCount++;
            }
            count++;
        }
        assertEquals("Not all square brackets are paired!", leftBrackCount, rightBrackCount);

        tree = y.getTree();
        count = 0;
        leftBrackCount = 0;
        rightBrackCount = 0;
        while(count < tree.length()){
            if(tree.charAt(count) == '['){
                leftBrackCount++;
            }else if(tree.charAt(count) == ']'){
                rightBrackCount++;
            }
            count++;
        }
        assertEquals("Not all square brackets are paired!", leftBrackCount, rightBrackCount);

    }
    @Test
    public void crossoverMatchingParams(){
        Evolution evolution = new Evolution();
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        Tree y = new Tree(new char[] {'S', 'G', 'H', 'T', 'L'}, new String[]{"[+++G][---G]TS", "+H[-G]L", "-G[+H]L", "TL", "[-FFF][+FFF]F"}, "SLFFF", 9);
        Tree[] trees;

        for(int i = 0; i < 100; i++){
            trees = evolution.crossover(x, y);
            x = trees[0];
            y = trees[1];
        }

        for(int i=0; i < x.getTree().length(); i++){
            if(x.getTree().charAt(i) == 'F'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) >= 0 && x.getParams().get(i) <= 10);
            }else if(x.getTree().charAt(i) == '+'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) <=25);
            }else if(x.getTree().charAt(i) == '-'){
                assertTrue("Mismatched parameter!", x.getParams().get(i) <=25);
            }else{
                assertTrue("Mismatched parameter!", x.getParams().get(i) == 0);
            }
        }
        for(int i=0; i < y.getTree().length(); i++){
            if(y.getTree().charAt(i) == 'F'){
                assertTrue("Mismatched parameter!", y.getParams().get(i) >= 0 && y.getParams().get(i) <= 10);
            }else if(y.getTree().charAt(i) == '+'){
                assertTrue("Mismatched parameter!", y.getParams().get(i) <=25);
            }else if(y.getTree().charAt(i) == '-'){
                assertTrue("Mismatched parameter!", y.getParams().get(i) <=25);
            }else{
                assertTrue("Mismatched parameter!", y.getParams().get(i) == 0);
            }
        }
    }
}