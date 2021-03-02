import { ToneRow } from "./ToneRow";

export interface Matrix{
    primes: ToneRowMap;
    inversions: ToneRowMap;
    retrogrades: ToneRowMap;
    retrogradeInversions: ToneRowMap;
    matrix: number[][];
}

export interface ToneRowMap {
    [label: string]: ToneRow;
}