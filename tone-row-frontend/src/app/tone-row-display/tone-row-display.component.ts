import { Component, Input, OnInit } from '@angular/core';
import { ToneRow } from '../models/ToneRow';
import { ToneRowMeta, ToneRowService } from '../tone-row.service';

export enum ToneRowDisplayOptions {
  FLATS = 1,
  SHARPS = 2,
  PITCH_CLASSES_NUMS = 3,
  PITCH_CLASSES_CHAR = 4
}

@Component({
  selector: '[tone-row-display]',
  templateUrl: './tone-row-display.component.html',
  styleUrls: ['./tone-row-display.component.scss']
})
export class ToneRowDisplayComponent implements OnInit {

  @Input() details: ToneRowMeta | null;
  toneRow: ToneRow | null;
  workTitle: string | null;
  composers: string[];
  displaying: ToneRowDisplayOptions;

  constructor(private service: ToneRowService) {
    this.details = null;
    this.toneRow = null;
    this.workTitle = null;
    this.composers = [];
    this.displaying = ToneRowDisplayOptions.FLATS;
  }

  ngOnInit(): void {
    if (this.details) {
      this.toneRow = this.details.toneRow;
      this.workTitle = this.details.work? this.details.work.title : null;
      this.composers = this.details.composers ? this.details.composers.map(c => c.name) : [];
    }
    // if (this.toneRow && this.toneRow.toneRowId && this.toneRow.workId) {
    //   this.service.getToneRowMeta(this.toneRow.toneRowId)
    //     .subscribe(meta => {
    //       if (meta?.work) {
    //         this.workTitle = meta.work.title;
    //       }
    //       if (meta?.composers) {
    //         this.composers = meta.composers.map(c => c.name);
    //       }
    //     })
    // }
  }

  toggleDisplay(option: number) {
    switch (option) {
      case 1:
        this.displaying = ToneRowDisplayOptions.FLATS;
        break;
      case 2:
        this.displaying = ToneRowDisplayOptions.SHARPS;
        break;
      case 3:
        this.displaying = ToneRowDisplayOptions.PITCH_CLASSES_NUMS;
        break;
      case 4:
        this.displaying = ToneRowDisplayOptions.PITCH_CLASSES_CHAR;
        break;
      default:
        break;
    }
  }

  convertToneRowToPCChar(): string[] {
    if (this.details) {
      return this.details.toneRow.noteOrder.map(n => {
        if (n.pitchClass === 10) return 't';
        if (n.pitchClass === 11) return 'e';
        return n.pitchClass.toString();
      })
    } else return [];
  }

}
