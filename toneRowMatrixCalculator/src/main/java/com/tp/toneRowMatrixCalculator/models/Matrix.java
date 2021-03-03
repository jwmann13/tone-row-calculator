package com.tp.toneRowMatrixCalculator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matrix {
    public Map<String, ToneRow> primes;
    public Map<String, ToneRow> inversions;
    public Map<String, ToneRow> retrogrades;
    public Map<String, ToneRow> retrogradeInversions;
    public List<String> primeLabelOrder;
    public List<String> inversionLabelOrder;
    public List<String> retrogradeLabelOrder;
    public List<String> retrogradeInversionLabelOrder;
    public int[][] matrix;

    public Matrix() {
        matrix = new int[12][12];
        primeLabelOrder = new ArrayList<>();
        inversionLabelOrder = new ArrayList<>();
        retrogradeLabelOrder = new ArrayList<>();
        retrogradeInversionLabelOrder = new ArrayList<>();
        primes = new HashMap<>();
        retrogrades = new HashMap<>();
        inversions = new HashMap<>();
        retrogradeInversions = new HashMap<>();
    }

    public Matrix(Matrix toCopy) {
        matrix = new int[12][12];
        for (int i = 0; i < 12; i++) {
            System.arraycopy(toCopy.matrix[i], 0, matrix[i], 0, 12);
        }
        primeLabelOrder = new ArrayList<>(toCopy.primeLabelOrder);
        inversionLabelOrder = new ArrayList<>(toCopy.inversionLabelOrder);
        retrogradeLabelOrder = new ArrayList<>(toCopy.retrogradeLabelOrder);
        retrogradeInversionLabelOrder = new ArrayList<>(toCopy.retrogradeInversionLabelOrder);
        primes = new HashMap<>(toCopy.primes);
        retrogrades = new HashMap<>(toCopy.retrogrades);
        inversions = new HashMap<>(toCopy.inversions);
        retrogradeInversions = new HashMap<>(toCopy.retrogradeInversions);
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

    public List<String> getPrimeLabelOrder() {
        return primeLabelOrder;
    }

    public void setPrimeLabelOrder(List<String> primeLabelOrder) {
        this.primeLabelOrder = primeLabelOrder;
    }

    public List<String> getInversionLabelOrder() {
        return inversionLabelOrder;
    }

    public void setInversionLabelOrder(List<String> inversionLabelOrder) {
        this.inversionLabelOrder = inversionLabelOrder;
    }

    public List<String> getRetrogradeLabelOrder() {
        return retrogradeLabelOrder;
    }

    public void setRetrogradeLabelOrder(List<String> retrogradeLabelOrder) {
        this.retrogradeLabelOrder = retrogradeLabelOrder;
    }

    public List<String> getRetrogradeInversionLabelOrder() {
        return retrogradeInversionLabelOrder;
    }

    public void setRetrogradeInversionLabelOrder(List<String> retrogradeInversionLabelOrder) {
        this.retrogradeInversionLabelOrder = retrogradeInversionLabelOrder;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
