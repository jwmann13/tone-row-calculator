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
    // TODO: Get rid of this triangle of death
    if (this.toneRow && this.toneRow.workId) {
      // 
      const wObs = this.service.getWorkById(this.toneRow.workId)
        .subscribe(work => {
          if (work) {
            this.workTitle = work.title
            if (work.workId) {
              this.service.getComposerWorkByWorkId(work.workId)
                .subscribe(composerWorks => {
                  console.log(composerWorks);
                  if (composerWorks) {
                    for (let cw of composerWorks) {
                      this.service.getComposerById(cw.composerId)
                        .subscribe(composer => {
                          console.log(composer);
                          if (composer) {
                            this.composers.push(composer.name);
                          }
                        })
                    }
                  }
                });
            }
          }
        });
    }
  }

  toggleFlats() {
    this.displayFlat = !this.displayFlat;
  }

}
