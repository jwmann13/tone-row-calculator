import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import Vex from 'vexflow';
import { Matrix } from '../models/Matrix';
import { Note } from '../models/Note';
import { ToneRowDisplayOptions } from '../tone-row-display/tone-row-display.component';

@Component({
  selector: 'row-staff-display',
  templateUrl: './row-staff-display.component.html',
  styleUrls: ['./row-staff-display.component.scss']
})
export class RowStaffDisplayComponent implements OnInit, OnChanges {
  @Input() displaying: ToneRowDisplayOptions = ToneRowDisplayOptions.FLATS;
  @Input() matrix: Matrix | null = null;
  @Input() selectedLabel: string = "P0";
  noteRow: Note[];
  noteString: string;
  VF = Vex.Flow;
  vf: Vex.Flow.Factory | undefined = undefined;
  score: Vex.Flow.EasyScore | undefined = undefined;
  system: Vex.Flow.System | undefined = undefined;


  constructor() {
    this.noteRow = [];
    this.noteString = "";
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.matrix) {
      switch (this.selectedLabel.slice(0, 1)) {
        case "P":
          this.noteRow = this.matrix.primes[this.selectedLabel].noteOrder;
          break;
        case "I":
          this.noteRow = this.matrix.inversions[this.selectedLabel].noteOrder;
          break;
        case "R":
          this.noteRow = this.selectedLabel.slice(0, 2) === "RI" ?
            this.matrix.retrogradeInversions[this.selectedLabel].noteOrder :
            this.matrix.retrogrades[this.selectedLabel].noteOrder
          break;
        default:
          break;
      }
      this.noteString = RowStaffDisplayComponent.parseNoteRow(this.noteRow, this.displaying);
    }

    const boo = document.getElementById("boo");
    if (boo) boo.innerHTML = "";
    if (this.vf) {
      this.vf = new this.VF.Factory({ renderer: { elementId: "boo", width: 500, height: 120 } });
      this.score = this.vf.EasyScore();
      this.system = this.vf.System();

      this.system.addStave({
        voices: [
          this.score.voice(this.score.notes(this.noteString, { stem: "up" }), { time: "24/4" })
        ]
      }).addClef("treble")

      this.vf.draw();
    }
  }

  ngOnInit(): void {
    if (this.matrix) {
      switch (this.selectedLabel.slice(0, 1)) {
        case "P":
          this.noteRow = this.matrix.primes[this.selectedLabel].noteOrder;
          break;
        case "I":
          this.noteRow = this.matrix.inversions[this.selectedLabel].noteOrder;
          break;
        case "R":
          this.noteRow = this.selectedLabel.slice(0, 2) === "RI" ?
            this.matrix.retrogradeInversions[this.selectedLabel].noteOrder :
            this.matrix.retrogrades[this.selectedLabel].noteOrder
          break;
        default:
          break;
      }
    }
    this.noteString = RowStaffDisplayComponent.parseNoteRow(this.noteRow, this.displaying);

    this.vf = new this.VF.Factory({ renderer: { elementId: "boo", width: 500, height: 120 } });
    this.score = this.vf.EasyScore();
    this.system = this.vf.System();

    this.system.addStave({
      voices: [
        this.score.voice(this.score.notes(this.noteString, { stem: "up" }), { time: "24/4" })
      ]
    }).addClef("treble")

    this.vf.draw();
  }

  static parseNoteRow(row: Note[], displaying: ToneRowDisplayOptions): string {
    let noteStr: string = "";

    for (let note of row) {
      switch (displaying) {
        case 1:
          noteStr += note.accidental ? `${note.flatName}4, ` : `${note.naturalName}4, `;
          break;
        case 2:
          noteStr += note.accidental ? `${note.sharpName}4, ` : `${note.naturalName}4, `;
          break;
        default:
          noteStr += note.accidental ? `${note.flatName}4, ` : `${note.naturalName}4, `;
          break;
      }
    }

    noteStr = noteStr.replace(", ", "/h, ")

    noteStr = noteStr.slice(0, noteStr.lastIndexOf(","));
    console.log(noteStr);
    return noteStr;
  }

}
