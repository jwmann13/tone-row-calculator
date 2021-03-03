import { Component, Input, OnInit } from '@angular/core';
import { ComposerWorkService } from '../composer-work.service';
import { ToneRow } from '../models/ToneRow';

export enum ToneRowDisplayOptions {
  FLATS = 1,
  SHARPS = 2,
  PITCH_CLASSES = 3
}

@Component({
  selector: '[tone-row-display]',
  templateUrl: './tone-row-display.component.html',
  styleUrls: ['./tone-row-display.component.scss']
})
export class ToneRowDisplayComponent implements OnInit {

  @Input() toneRow: ToneRow | null;
  workTitle: string | null;
  composers: string[];
  displaying: ToneRowDisplayOptions;

  constructor(private service: ComposerWorkService) {
    this.toneRow = null;
    this.workTitle = null;
    this.composers = [];
    this.displaying = ToneRowDisplayOptions.FLATS;
  }

  ngOnInit(): void {
    if (this.toneRow && this.toneRow.toneRowId && this.toneRow.workId) {
      this.service.getToneRowMeta(this.toneRow.toneRowId)
        .subscribe(meta => {
          if (meta?.work) {
            this.workTitle = meta.work.title;
          }
          if (meta?.composers) {
            this.composers = meta.composers.map(c => c.name);
          }
        })
    }
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
        this.displaying = ToneRowDisplayOptions.PITCH_CLASSES;
        break;
      default:
        break;
    }
  }

}
