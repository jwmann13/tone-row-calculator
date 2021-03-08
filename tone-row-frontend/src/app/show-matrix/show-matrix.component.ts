import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Matrix } from '../models/Matrix';
import { ToneRowDisplayOptions } from '../tone-row-display/tone-row-display.component';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'app-show-matrix',
  templateUrl: './show-matrix.component.html',
  styleUrls: ['./show-matrix.component.scss']
})
export class ShowMatrixComponent implements OnInit {
  matrix: Matrix | null;
  workTitle: string | null;
  composers: string[];
  displaying: ToneRowDisplayOptions;

  constructor(private service: ToneRowService, private route: ActivatedRoute) {
    this.matrix = null;
    this.workTitle = null;
    this.composers = [];
    this.displaying = ToneRowDisplayOptions.FLATS;
  }

  ngOnInit(): void {
    const routeMap = this.route.snapshot.paramMap;
    const matrixId = Number(routeMap.get("toneRowId"));
    if (matrixId) {
      this.service.getMatrix(matrixId).subscribe(data => this.matrix = data);
      this.service.getToneRowMeta(matrixId).subscribe(data => {
        if (data) {
          let {work, composers} = data;
          if (work) this.workTitle = work?.title;
          if (composers) this.composers = composers.map(c => c.name);
        }

      })
    }
  }

}
