export interface Note {
    noteId?: number;
    orderIndex: number;
    toneRowId: number;
    noteInfo: string;
    pitchClass: number;
    naturalName?: string | null;
    flatName?: string | null;
    sharpName?: string | null;
    accidental: boolean;
}

//  {
//     "noteId": 1,
//     "orderIndex": 0,
//     "toneRowId": null,
//     "noteInfo": "C",
//     "pitchClass": 0,
//     "naturalName": "C",
//     "flatName": null,
//     "sharpName": null,
//     "accidental": false
// },