package com.tp.toneRowMatrixCalculator.models;

import java.util.Arrays;

public enum NoteInfo {
    C(0, "C", null, null, false),
    C_SHARP(1, null, "C#", "Db", true),
    D(2, "D", null, null, false),
    D_SHARP(3, null, "D#", "Eb", true),
    E(4, "E", null, null, false),
    F(5, "F", null, null, false),
    F_SHARP(6, null, "F#", "Gb", true),
    G(7, "G", null, null, false),
    G_SHARP(8, null, "G#", "Ab", true),
    A(9, "A", null, null, false),
    A_SHARP(10, null, "A#", "Bb", true),
    B(11, "B", null, null, false);

    int pitchClass;
    String naturalName;
    String sharpName;
    String flatName;
    boolean accidental;

    NoteInfo(int pitchClass, String naturalName, String sharpName, String flatName, boolean accidental) {
        this.pitchClass = pitchClass;
        this.naturalName = naturalName;
        this.sharpName = sharpName;
        this.flatName = flatName;
        this.accidental = accidental;
    }

    public static NoteInfo getByValue(int value) {
        return Arrays.stream(values()).filter((n) -> n.pitchClass == value).findFirst().orElse(null);
    }

    public int getPitchClass() {
        return pitchClass;
    }

    public String getNaturalName() {
        return naturalName;
    }

    public String getSharpName() {
        return sharpName;
    }

    public String getFlatName() {
        return flatName;
    }

    public boolean isAccidental() {
        return accidental;
    }
}
