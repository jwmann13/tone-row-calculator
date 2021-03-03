import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'
import { Matrix, ToneRowMap } from '../models/Matrix';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'matrix-display',
  templateUrl: './matrix-display.component.html',
  styleUrls: ['./matrix-display.component.scss']
})
export class MatrixDisplayComponent implements OnInit {

  @Input() matrix: Matrix | null;

  constructor(private service: ToneRowService, private route: ActivatedRoute) {
    this.matrix = null;
  }

  ngOnInit(): void {
    const routeMap = this.route.snapshot.paramMap;
    const matrixId = Number(routeMap.get("toneRowId"));
    this.service.getMatrix(matrixId).subscribe(data => {
      this.matrix = data;
    });
  }

  static generateLabelOrder(map: ToneRowMap, noteOrder: number[], primeOrder: string[]): string[] {
    for (let i = 0; i < noteOrder.length; i++) {
      for (let key in map) {
        if (key.slice(1) === noteOrder[i].toString()) {
          primeOrder.push(key);
          delete map[key];
          break;
        }
      }
    }
    return primeOrder;
  }

  static generateLabelOrderRI(map: ToneRowMap, noteOrder: number[], primeOrder: string[]): string[] {
    for (let i = 0; i < noteOrder.length; i++) {
      for (let key in map) {
        if (key.slice(2) === noteOrder[i].toString()) {
          primeOrder.push(key);
          delete map[key];
          break;
        }
      }
    }
    return primeOrder;
  }

}
