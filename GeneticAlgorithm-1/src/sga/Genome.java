package sga;

import java.util.Arrays;

/**
 *
 * @author Cihanser Çalışkan - 20500002010
 */
public class Genome {
    private boolean[][] genes;

    @Override
    protected Object clone() {
        return new Genome(this);
    }

    public Genome(int length) {
        genes = new boolean[length] [length];
    }

    public Genome(Genome g) {
        genes = Arrays.copyOf( g.genes, g.getLength() );
    }

    public int getLength() {
        return genes.length;
    }

    public void setBit(int posX, int posY, boolean value){
        genes[posX] [posY] =value;
    }
    public boolean getBit(int posX, int posY) {
        return genes[posX] [posY];
    }

    public void randomize() {

        for ( int i = 0; i < genes.length; i++ ) {
            for ( int j = 0; j < genes[i].length; j++ ){
                if ( Math.random()> 0.5){
                    genes[i][j]=true;
                }
            }
        }
    }

    @Override
    public String toString() {
        String rep="";
        for ( int i = 0; i < genes.length; i++ ) {
            for (int j = 0; j < genes[i].length; j++){

                if (genes[i][j]){
                    rep+="1";
                }
                else{
                    rep+="0";
                }
            }
        }
        return rep;
    }

    public int fitness() {
        int value = 0;
        for(int i = 0; i < genes.length; i++){
            for(int j = 0; j < genes[i].length; j++){
                if(i==j && genes[i][j]){
                    value++;
                }else if(i!=j && !genes[i][j]){
                    value++;
                }
            }
        }
        return value;
    }
}
