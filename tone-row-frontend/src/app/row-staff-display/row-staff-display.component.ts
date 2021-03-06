import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import Vex from 'vexflow';
import { Note } from '../models/Note';
import { ToneRowDisplayOptions } from '../tone-row-display/tone-row-display.component';

@Component({
  selector: 'row-staff-display',
  templateUrl: './row-staff-display.component.html',
  styleUrls: ['./row-staff-display.component.scss']
})
export class RowStaffDisplayComponent implements OnInit, OnChanges {
  @Input() displaying: ToneRowDisplayOptions = ToneRowDisplayOptions.FLATS;
  @Input() noteRow: Note[];
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
    this.noteString = RowStaffDisplayComponent.parseNoteRow(this.noteRow, this.displaying);

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
          if (!note.accidental && noteStr.includes(note.naturalName!)) {
            noteStr += `${note.naturalName}n4, `;
          } else {
            noteStr += note.accidental ? `${note.flatName}4, ` : `${note.naturalName}4, `;
          }
          break;
        case 2:
          if (!note.accidental && noteStr.includes(note.naturalName!)) {
            noteStr += `${note.naturalName}n4, `;
          } else {
            noteStr += note.accidental ? `${note.sharpName}4, ` : `${note.naturalName}4, `;
          }
          break;
        default:
          if (!note.accidental && noteStr.includes(note.naturalName!)) {
            noteStr += `${note.naturalName}n4, `;
          } else {
            noteStr += note.accidental ? `${note.flatName}4, ` : `${note.naturalName}4, `;
          }
          break;
      }
    }

    noteStr = noteStr.replace(", ", "/h, ")

    noteStr = noteStr.slice(0, noteStr.lastIndexOf(","));
    return noteStr;
  }

}
