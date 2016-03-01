package com.example.kelvin_pc.film.Controller;

import com.example.kelvin_pc.film.Model.Film;

import java.util.ArrayList;
import java.util.HashMap;

import Jama.Matrix;

public class Maths_Handler  {

    private Matrix xtr, xts, ytr, yts;
    private HashMap<Film, Integer> ratings;

    public Maths_Handler(HashMap<Film, Integer> ratings) {
        this.ratings = ratings;
    }

    public void generateTrainingData() {
        ArrayList<Film> ratedFilms = new ArrayList<>(ratings.keySet());

        xtr = new Matrix(ratedFilms.size(), 3);
        xtr = generateMatrix(xtr, ratedFilms);

        ytr = new Matrix(ratedFilms.size(), 1);
        ytr = generateLabels(ytr, ratedFilms, ratings);

        new Debugger().printMatrix("TRAINING", xtr);
        new Debugger().printMatrix("LABELS", ytr);
    }

    public Matrix generateTestData(ArrayList<Film> films) {
        xts = new Matrix(20, 3);
        xts = generateMatrix(xts, films);
        yts = KNN(xtr, ytr, xts, 1);

        new Debugger().printMatrix("TEST", xts);
        new Debugger().printMatrix("LABELS", yts);

        return yts;
    }

    public Matrix generateMatrix(Matrix m, ArrayList<Film> films) {
        for (int i=0; i<m.getRowDimension(); i++) {
            Film f = films.get(i);
            m.set(i, 0, Double.parseDouble(f.getRating()));
            m.set(i, 1, Double.parseDouble(f.getRunTime()));
            m.set(i, 2, Double.parseDouble(f.getReleaseDate().substring(0, 4)));
        }
        return m;
    }

    public Matrix generateLabels(Matrix m, ArrayList<Film> films, HashMap<Film, Integer> ratings) {
        for (int i=0; i<m.getRowDimension(); i++) {
            Film f = films.get(i);
            Double label =  1.0 * ratings.get(f);
            m.set(i, 0, label);
        }
        return m;
    }

    public Matrix KNN(Matrix xtr, Matrix ytr, Matrix xts, int k) {

        // K is the number of neighbours to look at
        int K = k;

        new Debugger().print(Integer.toString(xtr.getRowDimension()));
        new Debugger().print(Integer.toString(xtr.getColumnDimension()));

        new Debugger().print(Integer.toString(xts.getRowDimension()));
        new Debugger().print(Integer.toString(xts.getColumnDimension()));

        int xtrRow = xtr.getRowDimension();
        int xtsRow = xts.getRowDimension();

        // Create a matrix for the y test labels
        Matrix yts = new Matrix(xtsRow, 1);

        // For each film recommendation
        for (int i=0; i<xtsRow; i++) {

            // Get the row of the ith film recommendation
            Matrix xtsi = xts.getMatrix(i, i, 0, 2);

            // Calculate the vector distance of the film recommendation
            Double v = xtsi.normF();

            // Store the distances of the recommendation against all the rated films
            Double[] distances = new Double[xtrRow];

            // For each rated film
            for (int j=0; j<xtrRow; j++) {

                // Get the row of the ith rated film
                Matrix xtri = xtr.getMatrix(j, j, 0, 2);

                // Calculate the vector distance of the rated film
                Double u = xtri.normF();

                // Find the Euclidean distance between the film recommendation and the rated film
                Double dist = Math.abs(u - v);

                // Store the distance
                distances[j] = dist;

            }

            // Smallest counter
            Double smallest = Integer.MAX_VALUE * 1.0;

            // Temp label storage, with -1 as default
            Double smallestLabel = -1 * 1.0;

            // Find the smallest distance
            for (int l=0; l<distances.length; l++) {
                if (distances[l] < smallest) {
                    smallest = distances[l];
                    smallestLabel = ytr.get(l, 0);
                }
            }

            // Assign the label to the film recommendation whether it is good or bad
            yts.set(i, 0, smallestLabel);

        }

        return yts;

    }

}
