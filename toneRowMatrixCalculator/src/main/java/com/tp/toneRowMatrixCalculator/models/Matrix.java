package com.tp.toneRowMatrixCalculator.models;

import java.util.Map;

public class Matrix {
    public Map<String, ToneRow> primes;
    public Map<String, ToneRow> inversions;
    public Map<String, ToneRow> retrogrades;
    public Map<String, ToneRow> retrogradeInversions;
    public int[][] matrix;

    public Matrix() {
        matrix = new int[12][12];
    }

    public Map<String, ToneRow> getPrimes() {
        return primes;
    }

    public void setPrimes(Map<String, ToneRow> primes) {
        this.primes = primes;
    }

    public Map<String, ToneRow> getInversions() {
        return inversions;
    }

    public void setInversions(Map<String, ToneRow> inversions) {
        this.inversions = inversions;
    }

    public Map<String, ToneRow> getRetrogrades() {
        return retrogrades;
    }

    public void setRetrogrades(Map<String, ToneRow> retrogrades) {
        this.retrogrades = retrogrades;
    }

    public Map<String, ToneRow> getRetrogradeInversions() {
        return retrogradeInversions;
    }

    public void setRetrogradeInversions(Map<String, ToneRow> retrogradeInversions) {
        this.retrogradeInversions = retrogradeInversions;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
