package com.example.kelvin_pc.film.Controller;

import android.util.Log;

import Jama.Matrix;

public class Debugger {

    private String tag = "myTag";

    public void print(String string) {
        Log.d(tag, string);
    }
    public void print(String loc, String string) {Log.d(tag + loc, string);}

    public void printMatrix(String string, Matrix m) {
        print(string);
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (int i=0; i<m.getRowDimension(); i++) {
            for (int j=0; j<m.getColumnDimension(); j++) {
                sb.append(String.valueOf(m.get(i, j)) + " ");
            }
            sb.append("}");
            Log.d(tag, sb.toString());
            sb.setLength(0);
            sb.append("{ ");
        }

    }

}
