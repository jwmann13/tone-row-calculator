import { Component, Input, OnInit } from '@angular/core';
import { ToneRow } from '../models/ToneRow';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'tone-row-display',
  templateUrl: './tone-row-display.component.html',
  styleUrls: ['./tone-row-display.component.scss']
})
export class ToneRowDisplayComponent implements OnInit {

  @Input() toneRow: ToneRow | null;
  displayFlat: boolean;

  constructor(private service: ToneRowService) {
    this.toneRow = null;
    this.displayFlat = true;
   }

  ngOnInit(): void {
    this.service.getToneRow(1).subscribe(data => this.toneRow = data);
  }

  toggleFlats() {
    this.displayFlat = !this.displayFlat;
  }

}
