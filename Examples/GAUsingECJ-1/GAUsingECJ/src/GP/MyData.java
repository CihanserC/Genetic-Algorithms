/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GP;

import ec.gp.*;

public class MyData extends GP.DoubleData {

    public double val;

    @Override
    public void copyTo( GPData other ) {
        (( MyData ) other).val = val;
    }
}
