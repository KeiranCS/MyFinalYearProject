package keiran.finalyearproject;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by Keiran on 17/11/2015.
 */
public class Evolution {

    private char[] V = {'X', 'F'};  //NOT USED
    private ArrayList<Character> symbols = new ArrayList<Character>();
    private ArrayList<String> productions = new ArrayList<String>();
    private float mutation_rate = 0.001f;

    private int init_pop_size = 6;          //CHANGE TO 6 FOR THE 6 DIFFERENT TREES
    private int production_size = 16;


    protected Tree[] pop = new Tree[init_pop_size];

    public Evolution() {
        /////////this.mutation_rate = mutation_rate;


        //this.tree = tree;
        //productions = tree.getProductions();
        /*
        for (char character : V)
            symbols.add(character);
        symbols.add('F');
        symbols.add('+');
        symbols.add('-');
        symbols.add('[');
        symbols.add(']');
        */
        //initializePopulation();
    }


    protected void initializePopulation(){
        /*
        for(int i = 0; i < pop.length; i++) {
           // String axiom = randomCharacters();
            //pop[i] = new Tree(V, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, axiom, 6);
            pop[i] = new Tree(V, new String[]{"F-[[X]+X]+F[+FX]-X", "FF"}, "X", 6);
            //pop[i] = new Tree(V, new String[]{"F-[X]+X", "FF"}, "X", 6);
        }

/*/

        pop[1] = new Tree(new char[] {'S', 'G', 'H', 'T', 'L'}, new String[]{"[+++G][---G]TS", "+H[-G]L", "-G[+H]L", "TL", "[-FFF][+FFF]F"}, "SLFFF", 9);
        pop[0] = new Tree(new char[] {'G', 'X'}, new String[]{"GFX[+G][-G]", "X[-FFF][+FFF]FX"}, "G", 6);
        pop[2] = new Tree(new char[] {'X', 'F'}, new String[]{"F[+X][-X]FX", "FF"}, "X", 7);
        pop[3] = new Tree(new char[] {'F'}, new String[]{"FF-[-F+F+F]+[+F-F-F]"}, "F", 4);
        pop[4] = new Tree(new char[] {'X', 'F'}, new String[]{"F[+X]F[-X]+X", "FF"}, "X", 7);
        pop[5] = new Tree(new char[] {'F'}, new String[]{"F[+F]F[-F][F]"}, "F", 5);


//*/
        /*
        productions.clear();
        for(int i = 0; i < V.length; i++){
            productions.add(randomProduction(V[i]));
        }
        for(int i = 0; i < pop.length; i++) {
            String axiom = randomCharacters();
            pop[i] = new Tree(V, productions.toArray(new String[productions.size()]), axiom, 6);
        }
        */
    }

    protected Tree[] crossover(Tree tree1, Tree tree2){
        Tree T1 = new Tree(tree1);
        Tree T2 = new Tree(tree2);
        String branch1;
        String branch2;
        List branchParams1;
        List branchParams2;
        Random RNG = new Random();
        String treeString1 = T1.getTree();
        String treeString2 = T2.getTree();
        ArrayList<Float> treeParams1 = T1.getParams();
        ArrayList<Float> treeParams2 = T2.getParams();
        int randomIndex1 = RNG.nextInt(treeString1.length()-1);
        if(treeString1.charAt(randomIndex1) != '['){
            if(treeString1.substring(randomIndex1).indexOf("[") == -1)
                randomIndex1 = treeString1.substring(0, randomIndex1).lastIndexOf("[");
            else randomIndex1 = randomIndex1 + treeString1.substring(randomIndex1).indexOf("[");
        }

        if(randomIndex1 == -1) //if there are no branches at all in the tree
            randomIndex1 = 0;

        int nextBracket1 = randomIndex1 + treeString1.substring(randomIndex1).indexOf("]");

        if(nextBracket1 == -1) //if there are no branches at all in the tree
            nextBracket1 = treeString1.length() - 1;

        //keep brackets paired
        int bracket = randomIndex1;
        boolean loop = true;
        /*
        if(treeString1.substring(bracket+1, nextBracket1).indexOf("[") == -1){
            loop = false;
        }
        */
        while(loop){
            if(treeString1.substring(bracket+1, nextBracket1).indexOf("[")== -1){
                loop = false;
            }else{
                bracket =  bracket + 1 + treeString1.substring(bracket+1, nextBracket1).indexOf("[");
                nextBracket1 = nextBracket1 + 1 + treeString1.substring(nextBracket1 + 1).indexOf("]");
            }
        }

        branch1 = treeString1.substring(randomIndex1, nextBracket1 +1);
        branchParams1 = new ArrayList(treeParams1.subList(randomIndex1, nextBracket1 +1));   //endIndex is exclusive

        int randomIndex2 = RNG.nextInt(treeString2.length()-1);
        if(treeString2.charAt(randomIndex2) != '['){
            if(treeString2.substring(randomIndex2).indexOf("[") == -1)
                randomIndex2 = treeString2.substring(0, randomIndex2).lastIndexOf("[");
            else randomIndex2 = randomIndex2 + treeString2.substring(randomIndex2).indexOf("[");
        }

        if(randomIndex2 == -1) //if there are no branches at all in the tree
            randomIndex2 = 0;

        int nextBracket2 = randomIndex2 + treeString2.substring(randomIndex2).indexOf("]");

        if(nextBracket2 == -1) //if there are no branches at all in the tree
            nextBracket2 = treeString2.length() - 1;

        //keep brackets paired
        bracket = randomIndex2;
        loop = true;
        /*
        if(treeString2.substring(bracket+1, nextBracket2).indexOf("[") == -1){
            loop = false;
        }
        */
        while(loop){
            if(treeString2.substring(bracket+1, nextBracket2).indexOf("[") == -1){
                loop = false;
            }else{
                bracket = bracket + 1 + treeString2.substring(bracket+1, nextBracket2).indexOf("[");
                nextBracket2 = nextBracket2 + 1 + treeString2.substring(nextBracket2 + 1).indexOf("]");

            }
        }

        branch2 = treeString2.substring(randomIndex2, nextBracket2 +1);
        branchParams2 = new ArrayList(treeParams2.subList(randomIndex2, nextBracket2 +1));   //endIndex is exclusive
        //Log.d("Crossover", "TreeParams1: " + treeParams1.get(randomIndex1) + ", " + treeParams1.get(nextBracket1));
        //Log.d("Crossover", "TreeParams2: " + treeParams2.get(randomIndex2) + ", " + treeParams2.get(nextBracket2));
        //Log.d("Crossover", branch1);
        //Log.d("Crossover", branchParams1.toString());
        //Log.d("Crossover", branch2);
        //Log.d("Crossover", branchParams2.toString());
        //Log.d("Crossover", "Branch1: " + branch1.length() + ", " + branchParams1.size());
        //Log.d("Crossover", "Branch2: " + branch2.length() + ", " + branchParams2.size());
        StringBuilder sb1 = new StringBuilder(treeString1);
        StringBuilder sb2 = new StringBuilder(treeString2);
        sb1.replace(randomIndex1, nextBracket1 + 1, branch2);
        sb2.replace(randomIndex2, nextBracket2 + 1, branch1);
        T1.setTree(sb1.toString());
        T2.setTree(sb2.toString());
        //Log.d("Crossover", "TreeParams1 Size: " + treeParams1.size());

        for(int i = randomIndex1; i <= nextBracket1; i++){
            treeParams1.remove(randomIndex1);
        }
        treeParams1.addAll(randomIndex1, branchParams2);
        //Log.d("Crossover", "TreeParams1 New Size: " + treeParams1.size());
        //Log.d("Crossover", "TreeParams2 Size: " + treeParams2.size());

        for(int i = randomIndex2; i <= nextBracket2; i++){
            treeParams2.remove(randomIndex2);
        }
        treeParams2.addAll(randomIndex2, branchParams1);
        //Log.d("Crossover", "TreeParams2 New Size: " + treeParams2.size());

        //Log.d("Crossover", "Tree1: " + T1.getTree().length() + ", " + treeParams1.size());
        //Log.d("Crossover", "Tree2: " + T2.getTree().length() + ", " + treeParams2.size());
        T1.setParams(treeParams1);
        T2.setParams(treeParams2);
        //Log.d("Crossover", T1.getTree().length() + ", " + T1.getParams().size());
        //Log.d("Crossover", T2.getTree().length() + ", " + T2.getParams().size());

        Tree[] trees = {T1, T2};
        return trees;
    }

   /* protected Tree mutateTree(Tree oldTree){

        //MAY END UP WITH EMPTY BRACKETS
        Tree T = new Tree(oldTree);
        String tree = T.getTree();
        ArrayList<Float> params = T.getParams();
        Random RNG = new Random();
        int randomIndex = RNG.nextInt(tree.length());
        String newString = randomString();
        StringBuilder sb = new StringBuilder(tree);
        if(sb.charAt(randomIndex) == '[') {
            int nextBracket = sb.indexOf("]", randomIndex);
            sb.deleteCharAt(nextBracket);
            params.remove(nextBracket);
        }
        else if(sb.charAt(randomIndex) == ']') {
            int prevBracket = sb.substring(0, randomIndex).lastIndexOf("[");
            sb.deleteCharAt(prevBracket);
            params.remove(prevBracket);
            randomIndex--;
        }
        sb.replace(randomIndex, randomIndex + 1, newString);
        params.remove(randomIndex);

        //insert parameters
        for(char chr : newString.toCharArray()) {
            if(chr == 'F')
                params.add(randomIndex, (float) RNG.nextInt(10));
            else if(chr == '+')
                params.add(randomIndex, RNG.nextFloat() * 25.0f);
            else if(chr == '-')
                params.add(randomIndex, RNG.nextFloat() * 25.0f);
            else params.add(randomIndex, (float) 0);
            randomIndex++;
        }
        T.setTree(sb.toString());
        T.setParams(params);
        return T;
    }
    */
    protected Tree mutateTree(Tree oldTree){

        Tree T = new Tree(oldTree);
        String tree = T.getTree();
        ArrayList<Float> params = T.getParams();
        Random RNG = new Random();
        StringBuilder sb = new StringBuilder(tree);
        //int treeLength = tree.length();
        for(int index = 0; index < sb.length(); index++){
            if (RNG.nextFloat() < mutation_rate){
                String newString = randomString();
                if(sb.charAt(index) == '[') {
                    int nextBracket = sb.indexOf("]", index);
                    sb.deleteCharAt(nextBracket);
                    params.remove(nextBracket);
                    //treeLength--;
                }
                else if(sb.charAt(index) == ']') {
                    int prevBracket = sb.substring(0, index).lastIndexOf("[");
                    sb.deleteCharAt(prevBracket);
                    params.remove(prevBracket);
                    index--;
                    //treeLength--;
                }
                sb.replace(index, index + 1, newString);
                params.remove(index);

                //insert parameters
                int paramIndex = index;
                for(char chr : newString.toCharArray()) {
                    if(chr == 'F')
                        params.add(paramIndex, (float) RNG.nextInt(10));
                    else if(chr == '+')
                        params.add(paramIndex, RNG.nextFloat() * 25.0f);
                    else if(chr == '-')
                        params.add(paramIndex, RNG.nextFloat() * 25.0f);
                    else params.add(paramIndex, (float) 0);
                    paramIndex++;
                }
                index = index + newString.length()-1; //gets incremented in next iteration
                //treeLength = treeLength + newString.length();
            }
        }

        T.setTree(sb.toString());
        T.setParams(params);
        return T;
    }
        /*
        What to do with square brackets?
        Currently removes all the contents withing brackets and replaces with a new string
         */
        /*
        if(sb.charAt(randomIndex) == '['){
            int nextBracket = sb.indexOf("]", randomIndex);
            int bracketNum = checkBrackets(sb.substring(randomIndex, nextBracket + 1));
            while(bracketNum > 0){
                int rightBracket = sb.substring(nextBracket + 1, sb.length()).indexOf("]");
                sb.deleteCharAt(rightBracket);
                params.remove(rightBracket);
                bracketNum--;
            }
            while (bracketNum < 0){
                int leftBracket = sb.substring(0, randomIndex).lastIndexOf("[");
                sb.deleteCharAt(leftBracket);
                params.remove(leftBracket);
                bracketNum++;
                randomIndex--;
                nextBracket--;
            }
            sb.replace(randomIndex, nextBracket + 1, newString);
            int count = randomIndex;
            while(count <= nextBracket){
                params.remove(randomIndex);
                count++;
            }

        }else if(sb.charAt(randomIndex) == ']'){
            int prevBracket = sb.substring(0, randomIndex).lastIndexOf("[");
            int bracketNum = checkBrackets(sb.substring(prevBracket, randomIndex + 1));
            while(bracketNum > 0){
                int rightBracket = sb.substring(randomIndex + 1, sb.length()).indexOf("]");
                sb.deleteCharAt(rightBracket);
                params.remove(rightBracket);
                bracketNum--;
            }
            while (bracketNum < 0) {
                int leftBracket = sb.substring(0, prevBracket).lastIndexOf("[");
                sb.deleteCharAt(leftBracket);
                params.remove(leftBracket);
                bracketNum++;
                randomIndex--;
                prevBracket--;
            }
            sb.replace(prevBracket, randomIndex + 1, newString);
            int count = prevBracket;
            while(count <= randomIndex){
                params.remove(prevBracket);
                count++;
            }
            randomIndex = prevBracket;
        }else {
            //if(sb.indexOf("]" , randomIndex) > sb.indexOf("[", randomIndex)) //deletes
                //sb.deleteCharAt(sb.indexOf("]", randomIndex));
            sb.replace(randomIndex, randomIndex + 1, newString);
            params.remove(randomIndex);
        }
*/

/*
    private String randomCharacters(){
        Random RNG = new Random();
        char[] chars = new char[RNG.nextInt(6) + 1]; //max axiom size set to 6
        for(int i = 0; i < chars.length; i++)
            chars[i] = V[RNG.nextInt(V.length)];
        return new String(chars);
    }

    private String randomProduction(char predecessor){
        String prod = "";
        boolean isRecursive = false;
        while(prod.length() <= 1 && !isRecursive) {
            prod = randomString(V);
            if(prod.contains(Character.toString(predecessor)))
                isRecursive = true;
        }
        return prod;
    }
*/
    protected String randomString(){
        ArrayList<Character> symbols = new ArrayList<Character>();
        //for (char character : V)
            //symbols.add(character);
        symbols.add('F');
        symbols.add('+');
        symbols.add('-');
        symbols.add('[');
        symbols.add(']');
        symbols = removeDuplicates(symbols);    //removing duplicates within alphabet will ensure equal probability of each symbol being selected
        Random RNG = new Random();
        int randomSize = RNG.nextInt(production_size);
        if(randomSize == 0){
            return "";
        }
        
        int randomChar;
        int bracketCount = 0;
        StringBuilder sb = new StringBuilder(randomSize);
        for (int i = 0; i < randomSize; i++) {
            randomChar = RNG.nextInt(symbols.size());
            while ((symbols.get(randomChar) == ']' && bracketCount <= 0) || (symbols.get(randomChar) == '[' && randomSize < 2))
                randomChar = RNG.nextInt(symbols.size());
            switch (symbols.get(randomChar)){
                case '[':
                    bracketCount++;
                    break;
                case ']':
                    bracketCount--;
                    break;
                default:
                    break;
            }
            sb.insert(i, Character.toString(symbols.get(randomChar)));
        }
        // remove unnecessary '[' characters
        while (bracketCount > 0) {
            sb.deleteCharAt(sb.lastIndexOf(Character.toString('[')));
            bracketCount--;
        }
        while (bracketCount < 0){
            sb.deleteCharAt(sb.lastIndexOf(Character.toString(']')));
            bracketCount++;
        }
        return sb.toString();
    }


    public void geneticAlgorithm(){
        for(int i = 0; i < pop.length; i++)
            pop[i] = mutateTree(pop[i]);



    }

    public void geneticAlgorithm(int tree){
        Tree selectedTree = pop[tree];
        for(int i = 0; i < pop.length; i++){
            pop[i] = mutateTree(selectedTree);
        }

    }

    public ArrayList<Character> removeDuplicates(ArrayList<Character> symbols){

        for(int i = 0; i < symbols.size(); i++){
            while(symbols.indexOf(symbols.get(i)) != symbols.lastIndexOf(symbols.get(i))){
                symbols.remove(symbols.lastIndexOf((symbols.get(i))));
            }
        }
        return symbols;
    }

    /**
     * Checks if all brackets within a string are paired.
     * @param str
     * @return 0 means all brackets are paired. Positive is the number of extra [, negative is the number of extra ]
     */
    public int checkBrackets(String str){
        int bracketCount = 0;
        for(char chr : str.toCharArray()){
            if(chr == '[')
                bracketCount++;
            if(chr == ']')
                bracketCount--;
        }
        return bracketCount;
    }

    /*
    public void geneticAlgorithm() {

        Random RNG = new Random();
        int randomNo;
        int randomRule;
        //Mutate number of productions
        //Chance of adding, removing or nothing
        int size = productions.size();
        int max = tree.getMax_productions();
        int difference = max - size; //More likely to add production if size is lower
        randomNo = RNG.nextInt(max);
        if (randomNo <= difference && size < max) {
            //Generate and add production
            int randomSize = RNG.nextInt(19) + 2; //reasonable production length? Currently up to 20
            String prod = "";
            int randomChar;
            int count = 0;
            StringBuilder sb = new StringBuilder(prod);
            while(prod.length() == 0) {
                for (int i = 0; i < randomSize; i++) {
                    randomChar = RNG.nextInt(symbols.size());
                    while (symbols.get(randomChar) == ']' && count == 0)
                        randomChar = RNG.nextInt(symbols.size());

                    if (symbols.get(randomChar) == '[')
                        count++;
                    if (symbols.get(randomChar) == ']')
                        count--;
                    sb.append(Character.toString(symbols.get(randomChar)));
                }
                // remove unnecessary '[' characters
                while (count > 0) {
                    sb.deleteCharAt(sb.lastIndexOf(Character.toString('[')));
                    count--;
                }
                prod = sb.toString();
            }

            productions.add(prod);
        } else {
            //remove production or nothing
            //add constraint to not remove rule if difference is big
            //randomNo = RNG.nextInt(2);
            randomNo = RNG.nextInt(max);
            if (randomNo >= difference && size > 1) {
                //remove production
                randomRule = RNG.nextInt(size);
                productions.remove(randomRule);
            }
        }
        //Mutate production Rules
        //select production rule
        //mutate each rule through probability based on mutation rate
        randomRule = RNG.nextInt(productions.size());
        String production = productions.get(randomRule);
        StringBuilder sb = new StringBuilder(production);
        int randomIndex = RNG.nextInt(sb.length()); //letter to mutate
        //mutate rule
        randomNo = RNG.nextInt(3);
        if (randomNo == 0 && sb.length() > 1) {
            //delete Letter in random location
            if (production.charAt(randomIndex) == '[') {
                //delete next ']' as well
                sb.deleteCharAt(production.indexOf(']', randomIndex)); //delete next occurrence of ']'
                sb.deleteCharAt(randomIndex);
            } else if (production.charAt(randomIndex) == ']') {
                //delete previous '[' as well
                sb.deleteCharAt(randomIndex);
                sb.deleteCharAt(production.substring(0, randomIndex).lastIndexOf('[')); //delete previous occurrence of '['
            }else {
                sb.deleteCharAt(randomIndex);
            }

        } else if (randomNo == 1) {
            //replace letter from random location
            int newRandomChar = RNG.nextInt(symbols.size());
            //no point replacing a letter with the same letter
            while ((symbols.get(newRandomChar) == production.charAt(randomIndex)) ||
                    (symbols.get(newRandomChar) == ']' && randomIndex < 2)){
                newRandomChar = RNG.nextInt(symbols.size());
            }
            if (production.charAt(randomIndex) == '[') {
                sb.deleteCharAt(production.indexOf(']', randomIndex)); //delete next occurrence of ']'
            } else if (production.charAt(randomIndex) == ']') {
                sb.deleteCharAt(production.substring(0, randomIndex).lastIndexOf('['));//delete previous occurrence of '['
                randomIndex--;
            }
            sb.replace(randomIndex, randomIndex + 1, symbols.get(newRandomChar).toString());

            if (symbols.get(newRandomChar) == '[')
                sb.insert(RNG.nextInt(sb.length() - randomIndex) + randomIndex + 1, ']'); //is this right?

            if (symbols.get(newRandomChar) == ']')
                sb.insert(RNG.nextInt(randomIndex - 1),'[');

        } else {
            //add letter into random location
            int randomChar = RNG.nextInt(symbols.size());
            randomIndex = RNG.nextInt(sb.length() + 1);

            while(symbols.get(randomChar) == ']' && randomIndex < 2) {
                randomChar = RNG.nextInt(symbols.size());
                randomIndex = RNG.nextInt(sb.length() + 1);
            }

            sb.insert(randomIndex, symbols.get(randomChar));
            if (symbols.get(randomChar) == '[')
                sb.insert(RNG.nextInt(sb.length() - randomIndex) + randomIndex + 1, ']');

            if (symbols.get(randomChar) == ']')
                sb.insert(RNG.nextInt(randomIndex - 1), '[');
        }
        production = sb.toString();
        productions.set(randomRule, production);
        setTree(productions);
    }


    public Tree getTree() { return tree; }

    public void setTree(ArrayList<String> productions) {

        char[] alphabet = this.V;
        String[] newProductions = productions.toArray(new String[productions.size()]);
        char axiom = this.tree.getAxiom();
        int n = this.tree.getN();
        this.tree = new Tree(alphabet, newProductions, axiom, n); }

        */
    public Tree[] getPop() { return pop; }

    public void setPop(Tree[] pop) { this.pop = pop; }


    public int getInit_pop_size() { return init_pop_size; }

    public void setInit_pop_size(int init_pop_size) { this.init_pop_size = init_pop_size; }
}
