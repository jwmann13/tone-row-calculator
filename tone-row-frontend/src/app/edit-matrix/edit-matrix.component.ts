import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Matrix } from '../models/Matrix';
import { ToneRowService } from '../tone-row.service';

import { faSave, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-edit-matrix',
  templateUrl: './edit-matrix.component.html',
  styleUrls: ['./edit-matrix.component.scss']
})
export class EditMatrixComponent implements OnInit {
  matrix: Matrix | null;
  workTitle: string;
  composers: string[];
  buttonStack: HTMLButtonElement[];
  newRow: number[];
  saveIcon = faSave;
  deleteIcon = faTrash;

  constructor(
    private modalService: NgbModal,
    private service: ToneRowService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.matrix = null;
    this.workTitle = "";
    this.composers = [];
    this.newRow = [];
    this.buttonStack = [];
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const matrixId = Number(params.get("toneRowId"));
      if (matrixId) {
        this.service.getMatrix(matrixId).subscribe(data => {
          this.matrix = data;
          if (data?.matrix) {
            this.newRow = data.matrix[0];
          }

          this.newRow.forEach(pc => {
            let button = document.getElementById(`Note-Btn-${pc}`) as HTMLButtonElement
            button.disabled = true;
            this.buttonStack.push(button);
          });

          console.log(this.newRow);
        });
        this.service.getToneRowMeta(matrixId).subscribe(data => {
          if (data) {
            let { work, composers } = data;
            if (work) this.workTitle = work.title;
            if (composers) this.composers = composers.map(c => c.name);
          }
        })
      }
    },
      err => console.log(err))
  }

  addToRow(event: MouseEvent, pitchClass: number) {
    if (event.target && event.target instanceof HTMLButtonElement) {
      event.target.disabled = true;
      this.buttonStack.push(event.target);
    }
    this.newRow.push(pitchClass);
    if (this.matrix) {
      this.matrix.matrix = EditMatrixComponent.generateTempMatrix(this.newRow);
    }
  }

  deleteNote() {
    let lastButton = this.buttonStack.pop();
    if (lastButton !== undefined) lastButton.disabled = false;
    this.newRow.pop();
    if (this.matrix) {
      this.matrix.matrix = EditMatrixComponent.generateTempMatrix(this.newRow);
    }
  }

  static generateTempMatrix(toneRow: number[]): number[][] {
    let toReturn: number[][] = [];
    const firstPC = toneRow[0];
    for (let i = 0; i < toneRow.length; i++) {
      toReturn.push([]);
      for (let j = 0; j < toneRow.length; j++) {
        toReturn[i].push((toneRow[j] - (toneRow[i] - firstPC) + 12) % 12);
      }
    }
    return toReturn;
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
