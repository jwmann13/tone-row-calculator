import { Component, Input, OnInit } from '@angular/core';
import { Matrix } from '../models/Matrix';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'matrix-display',
  templateUrl: './matrix-display.component.html',
  styleUrls: ['./matrix-display.component.scss']
})
export class MatrixDisplayComponent implements OnInit {

  @Input() matrix: Matrix | null;

  constructor(private service: ToneRowService) {
    this.matrix = null;
  }

  ngOnInit(): void {
    this.service.getMatrix(1).subscribe(data => this.matrix = data);
  }

}
