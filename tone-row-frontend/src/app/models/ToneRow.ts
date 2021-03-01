import { Note } from "./Note";

export interface ToneRow {
    noteOrder: Note[];
    toneRowId?: number;
    workId?: number;
}