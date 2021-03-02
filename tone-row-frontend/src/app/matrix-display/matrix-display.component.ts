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
  primeOrder: string[];
  inversionOrder: string[];
  retrogradeOrder: string[];
  retrogradeInversionOrder: string[];

  constructor(private service: ToneRowService, private route: ActivatedRoute) {
    this.matrix = null;
    this.primeOrder = [];
    this.inversionOrder = [];
    this.retrogradeOrder = [];
    this.retrogradeInversionOrder = [];
  }

  ngOnInit(): void {
    const routeMap = this.route.snapshot.paramMap;
    const matrixId = Number(routeMap.get("toneRowId"));
    this.service.getMatrix(matrixId).subscribe(data => {
      this.matrix = data;
      if (data && data.matrix) {
        if (data.primes) {
          this.primeOrder = MatrixDisplayComponent
            .generateLabelOrder({...data.primes }, [...data.matrix[0]], [...this.primeOrder]);
        }

        if (data.inversions) {
          let inverted: number[] = [];
          for (let i = 0; i < data.matrix.length; i++) {
            inverted.push(data.matrix[i][0]);
          }
          this.inversionOrder = MatrixDisplayComponent
            .generateLabelOrder({...data.inversions }, inverted, [...this.inversionOrder]);
        }

        if (data.retrogrades) {
          this.retrogradeOrder = MatrixDisplayComponent
            .generateLabelOrder({...data.retrogrades }, [...data.matrix[11]], [...this.retrogradeOrder]);
        }

        if (data.retrogradeInversions) {
          let retrogradeInverted: number[] = [];
          for (let i = 0; i < data.matrix.length; i++) {
            retrogradeInverted.push(data.matrix[i][11]);
          }
          this.retrogradeInversionOrder = MatrixDisplayComponent
            .generateLabelOrderRI({...data.retrogradeInversions }, retrogradeInverted, [...this.retrogradeInversionOrder]);
        }
      }
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
