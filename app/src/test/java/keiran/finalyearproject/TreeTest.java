package keiran.finalyearproject;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import static org.junit.Assert.*;

/**
 * Created by Keiran on 03/02/2016.
 */
public class TreeTest {

    @Test
    public void testBracketsPaired(){
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
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
    public void testNumberOfParams(){
        Tree tree = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
        int treeSize = tree.getTree().length();
        int paramSize = tree.getParams().size();
        assertEquals("Characters and parameters don't match!", treeSize, paramSize);
    }

    @Test
    public void testMatchingParams(){
        Tree x = new Tree(new char[]{'X', 'F'}, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
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
    public void testRemoveCharacters(){
        Tree x = new Tree(new char[] {'S', 'G', 'H', 'T', 'L'}, new String[]{"[+++G][---G]TS", "+H[-G]L", "-G[+H]L", "TL", "[-FFF][+FFF]F"}, "SLFFF", 9);
        x.removeCharacters(x.getTree());
        System.out.println(x.getTree());
        for(char chr : x.getTree().toCharArray()){
            switch (chr){
                case 'S':
                    fail("Not all uninterpreted characters were removed!");
                    break;
                case 'G':
                    fail("Not all uninterpreted characters were removed!");
                    break;
                case 'H':
                    fail("Not all uninterpreted characters were removed!");
                    break;
                case 'T':
                    fail("Not all uninterpreted characters were removed!");
                    break;
                case 'L':
                    fail("Not all uninterpreted characters were removed!");
                    break;
                default:
                    break;
            }
        }

    }
}