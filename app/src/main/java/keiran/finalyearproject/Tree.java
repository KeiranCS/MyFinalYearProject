package keiran.finalyearproject;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Keiran on 23/10/2015.
 */
public class Tree implements Parcelable{

    private char[] V; //appropriate size alphabet?
    private String axiom;

    //private String[] productions = {"F-[[X]+X]+F[+FX]-A", "FF"};
    protected ArrayList<String> productions = new ArrayList<String>();
    private int max_productions; //currently set so each character can only have one production
    private char[] turtleRules = {'F', '+', '-', '[', ']'};

    private int n;
    private String tree;
    private ArrayList<Float> params = new ArrayList<>(); //character in string and parameter should share the same index

    private String produce(char rule) {
        /*
        switch (rule) {
            case 'X':
                return productions.get(0);
            case 'F':
                return productions.get(1);
            case 'f':
                return "f";
            case '+':
                return "+";
            case '-':
                return "-";
            case '|':
                return "|";
            case '[':
                return "[";
            case ']':
                return "]";
            default:
                return "";
        }
        */
        if (new String(V).indexOf(rule) == -1) {
            return Character.toString(rule);
        } else {
            int index = new String(V).indexOf(rule);
            if (index >= productions.size()) {
                return Character.toString(rule);
            } else {
                return productions.get(index);
            }
        }

    }

    protected String produceTree(String axiom) {
        /*
        if(axiom.isEmpty()){
            return "";
        }else if(axiom.length() == 1) {
            return produce(axiom.toCharArray()[0]);
        }else{
            return produce(axiom.toCharArray()[0]) + produceTree(axiom.substring(1,axiom.length()));
        }
        */
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < axiom.length(); i++) {
            sb.append(produce(axiom.toCharArray()[i]));
        }

        return sb.toString();
    }

    /**
     * Removes characters not interpreted by the turtle
     * @param tree
     */
    protected void removeCharacters(String tree){
        StringBuilder sb = new StringBuilder(tree);
        for(int i = 0; i < sb.length(); i++){
            boolean isSymbol = false;
            for(char chr : turtleRules) {
                if (sb.charAt(i) == chr) {
                    isSymbol = true;
                    break;
                }
            }
            if(!isSymbol){
                sb.deleteCharAt(i);
                i--;
            }
        }

        setTree(sb.toString());
    }

    public Tree(char[] alphabet, String[] productions, String axiom, int n) {

        setV(alphabet);
        setMax_productions(alphabet.length);
        setAxiom(axiom);
        setN(n);
        setProductions(productions);
        setTree(axiom);
        for (int i = 0; i < n; i++)
            setTree(produceTree(tree));
        removeCharacters(getTree());
        Random RNG = new Random();
        for (char chr : tree.toCharArray()) {
            if(chr =='F')
                params.add((float) RNG.nextInt(10)); //Random integer between 0 and 10
            else if(chr == '+')
                //params.add((float) RNG.nextGaussian() * 25); //Uniformly random (standard deviation 25, mean 0)
                params.add(RNG.nextFloat() * 25.0f);
            else if(chr == '-')
                //params.add((float) RNG.nextGaussian() * 25);
                params.add(RNG.nextFloat() * 25.0f);
            else params.add((float) 0);
        }
    }

    public Tree(Tree tree){
        this.V = tree.getV();
        setMax_productions(tree.getMax_productions());
        //setAxiom(tree.getAxiom());
        setN(tree.getN());
        setProductions(tree.getProductions().toArray(new String[0]));
        setTree(tree.getTree());
        setParams(new ArrayList<Float>(tree.getParams()));
    }

    public Tree(Parcel in){
        in.readList(params, Character.class.getClassLoader());
        tree = in.readString();
        //in.readCharArray(V);

    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getAxiom() {
        return axiom;
    }

    public void setAxiom(String axiom) throws InvalidAlphabetException {
        for (char chr : axiom.toCharArray()) {
            if (new String(V).indexOf(chr) == -1) {
                if(new String(turtleRules).indexOf(chr) == -1){
                    throw new InvalidAlphabetException("Error: Axiom is not contained in alphabet given.");
                }
            }
        }
        this.axiom = axiom;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public ArrayList<String> getProductions() {
        return this.productions;
    }

    public void setProductions(String[] productions) {
        if (productions.length > max_productions)
            throw new MaxProductionsException("Error: There are too many productions. " +
                    "\nCurrent Productions : " + productions.length +
                    " Max Productions : " + max_productions);

        for (String production : productions) {
            for (Character chr : production.toCharArray()) {
                if (new String(V).indexOf(chr) == -1 && new String(turtleRules).indexOf(chr) == -1)
                    throw new InvalidAlphabetException("Error: Production is not within alphabet." +
                            "\nCharacter : " + chr + " Production : " + production);
            }
            this.productions.add(production);
        }
    }

    public char[] getV() {
        return V;
    }

    public void setV(char[] v) {
        this.V = v;
    }

    public int getMax_productions() {
        return max_productions;
    }

    public void setMax_productions(int max_productions) {
        this.max_productions = max_productions;
    }

    public ArrayList<Float> getParams() {
        return params;
    }

    public void setParams(ArrayList<Float> params) {
        this.params = params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        //NEED TO PASS TREE, PARAMS AND V AS THEY ARE USED BY EVOLUTION.CLASS
        dest.writeList(this.params);
        dest.writeString(this.tree);
        //dest.writeCharArray(this.V);


    }

    public static final Parcelable.Creator<Tree> CREATOR = new Parcelable.Creator<Tree>(){
        public Tree createFromParcel(Parcel in) {
            return new Tree(in);
        }

        public Tree[] newArray(int size) {
            return new Tree[size];
        }
    };
}
