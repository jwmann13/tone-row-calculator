import { ToneRow } from "./ToneRow";

export interface Matrix{
    primes: ToneRowMap;
    inversions: ToneRowMap;
    retrogrades: ToneRowMap;
    retrogradeInversions: ToneRowMap;
    primeLabelOrder: string[];
    inversionLabelOrder: string[];
    retrogradeLabelOrder: string[];
    retrogradeInversionLabelOrder: string[];
    matrix: number[][];
}

export interface ToneRowMap {
    [label: string]: ToneRow;
}