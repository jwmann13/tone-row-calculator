import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'
import { Matrix } from '../models/Matrix';
import { ToneRowDisplayOptions } from '../tone-row-display/tone-row-display.component';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'matrix-display',
  templateUrl: './matrix-display.component.html',
  styleUrls: ['./matrix-display.component.scss']
})
export class MatrixDisplayComponent implements OnInit {

  @Input() matrix: Matrix | null;
  displaying: ToneRowDisplayOptions;

  constructor(private service: ToneRowService, private route: ActivatedRoute) {
    this.matrix = null;
    this.displaying = ToneRowDisplayOptions.FLATS;
  }

  ngOnInit(): void {
    const routeMap = this.route.snapshot.paramMap;
    const matrixId = Number(routeMap.get("toneRowId"));
    this.service.getMatrix(matrixId).subscribe(data => {
      this.matrix = data;
    });
  }

  toggleDisplay(option: number) {
    switch (option) {
      case 1:
        this.displaying = ToneRowDisplayOptions.FLATS
        break;
      case 2:
        this.displaying = ToneRowDisplayOptions.SHARPS
        break;
      case 3:
        this.displaying = ToneRowDisplayOptions.PITCH_CLASSES
        break;
      default:
        break;
    }
  }

}
