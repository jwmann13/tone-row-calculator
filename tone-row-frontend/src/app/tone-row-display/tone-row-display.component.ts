import { Component, Input, OnInit } from '@angular/core';
import { ComposerWorkService } from '../composer-work.service';
import { ToneRow } from '../models/ToneRow';

@Component({
  selector: 'tone-row-display',
  templateUrl: './tone-row-display.component.html',
  styleUrls: ['./tone-row-display.component.scss']
})
export class ToneRowDisplayComponent implements OnInit {

  @Input() toneRow: ToneRow | null;
  @Input() workTitle: string | null;
  displayFlat: boolean;

  constructor(private service: ComposerWorkService) {
    this.toneRow = null;
    this.workTitle = null;
    this.displayFlat = true;
   }

  ngOnInit(): void {
    if (this.toneRow && this.toneRow.workId) {
      this.service.getWorkById(this.toneRow.workId).subscribe(data => {
        if (data) {
          this.workTitle = data.title
        }
      });
    }
  }

  toggleFlats() {
    this.displayFlat = !this.displayFlat;
  }

}
