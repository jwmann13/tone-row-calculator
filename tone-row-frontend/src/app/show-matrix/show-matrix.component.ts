import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Matrix } from '../models/Matrix';
import { Note } from '../models/Note';
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

  constructor(
    private modalService: NgbModal,
    private service: ToneRowService,
    private route: ActivatedRoute,
    private router: Router
  ) {
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
          let { work, composers } = data;
          if (work) this.workTitle = work?.title;
          if (composers) this.composers = composers.map(c => c.name);
        }

      })
    }
  }

  open(modal: any) {
    this.modalService.open(modal).result.then(
      result => { }, reason => { });
  }

  delete() {
    const routeMap = this.route.snapshot.paramMap;
    const matrixId = Number(routeMap.get("toneRowId"));
    this.service.deleteToneRow(matrixId).subscribe(data => {
      this.modalService.dismissAll("Tone Row Saved");
      this.router.navigateByUrl("");
    });
  }

}
