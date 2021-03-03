import { Component, Input, OnInit } from '@angular/core';
import { ComposerWorkService } from '../composer-work.service';
import { ToneRow } from '../models/ToneRow';

@Component({
  selector: '[tone-row-display]',
  templateUrl: './tone-row-display.component.html',
  styleUrls: ['./tone-row-display.component.scss']
})
export class ToneRowDisplayComponent implements OnInit {

  @Input() toneRow: ToneRow | null;
  workTitle: string | null;
  composers: string[];
  displayFlat: boolean;

  constructor(private service: ComposerWorkService) {
    this.toneRow = null;
    this.workTitle = null;
    this.composers = [];
    this.displayFlat = true;
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

  toggleFlats() {
    this.displayFlat = !this.displayFlat;
  }

}
