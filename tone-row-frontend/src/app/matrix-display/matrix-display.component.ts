import { Component, EventEmitter, Input, OnInit, Output, OnChanges, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router'
import { Matrix } from '../models/Matrix';
import { ToneRowDisplayOptions } from '../tone-row-display/tone-row-display.component';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'matrix-display',
  templateUrl: './matrix-display.component.html',
  styleUrls: ['./matrix-display.component.scss']
})
export class MatrixDisplayComponent implements OnInit, OnChanges {

  @Input() matrix: Matrix | null;
  @Input() displaying: ToneRowDisplayOptions;
  @Input() tempMatrix: number[][] | null;
  tempPrimeLabels: string[];
  tempInversionLabels: string[];
  tempRetrogradeLabels: string[];
  tempRetrogradeInversionLabels: string[];
  selectedLabel: string = "P0";

  constructor(private service: ToneRowService, private route: ActivatedRoute) {
    this.matrix = null;
    this.tempMatrix = null;
    this.tempPrimeLabels = [];
    this.tempInversionLabels = [];
    this.tempRetrogradeLabels = [];
    this.tempRetrogradeInversionLabels = [];
    this.displaying = 0;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.tempMatrix && this.tempMatrix.length > 0) {
      this.tempPrimeLabels = this.tempMatrix.map(n => "P" + n[0]);
      this.tempInversionLabels = this.tempMatrix[0].map(n => "I" + n);
      this.tempRetrogradeLabels = this.tempMatrix.map(n => "R" + n[0]);
      this.tempRetrogradeInversionLabels = this.tempMatrix[0].map(n => "RI" + n);
    }
  }

  ngOnInit(): void {
    // const routeMap = this.route.snapshot.paramMap;
    // const matrixId = Number(routeMap.get("toneRowId"));
    // if (matrixId) {
    //   this.service.getMatrix(matrixId).subscribe(data => {
    //     this.matrix = data;
    //   });
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

  convertMatrixToPCChar(): string[][] {
    let converted: any[] = [];
    if (this.matrix) {
      for (const row of this.matrix.matrix) {
        converted.push(
          row.map(n => {
            if (n === 10) return 't';
            if (n === 11) return 'e';
            return n.toString();
          })
        )
      }
    }
    return converted;
  }

  emptyArray() {
    return Array(12);
  }

  shrinkingArray(arr: any[]) {
    return Array(12 - arr.length);
  }

  labelClick(label: string) {
    console.log(label);
    this.selectedLabel = label;
  }

}
