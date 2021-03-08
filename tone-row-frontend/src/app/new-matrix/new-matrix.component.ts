import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { faSave } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'app-new-matrix',
  templateUrl: './new-matrix.component.html',
  styleUrls: ['./new-matrix.component.scss']
})
export class NewMatrixComponent implements OnInit {
  newRow: number[];
  tempMatrix: number[][];
  buttonStack: HTMLButtonElement[];
  saveIcon = faSave;
  work: string;
  composer: string;
  @Output() saveEvent: EventEmitter<{ toneRow: number[] }> = new EventEmitter<{ toneRow: number[] }>();

  constructor(private modalService: NgbModal, private trService: ToneRowService, private router: Router) {
    this.newRow = [];
    this.tempMatrix = [];
    this.buttonStack = [];
    this.work = "";
    this.composer = "";
  }

  ngOnInit(): void {
  }

  addToRow(event: MouseEvent, pitchClass: number) {
    if (event.target && event.target instanceof HTMLButtonElement) {
      event.target.disabled = true;
      this.buttonStack.push(event.target);
    }
    this.newRow.push(pitchClass);
    this.tempMatrix = NewMatrixComponent.generateTempMatrix(this.newRow);
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

  deleteNote() {
    let lastButton = this.buttonStack.pop();
    if (lastButton !== undefined) lastButton.disabled = false;
    this.newRow.pop();
    this.tempMatrix = NewMatrixComponent.generateTempMatrix(this.newRow);
  }

  open(modal: any) {
    this.modalService.open(modal).result.then(
      result => {
        console.log(result);
      }, reason => {
        console.log(reason);
      });
  }

  save() {
    const composers = this.composer.split(",").map(c => c.trim());
    if (this.newRow.length === 12 && this.composer && this.work) {
      this.trService.postToneRowMeta(this.newRow, composers, this.work)
      .subscribe(data => {
        this.modalService.dismissAll("Tone Row Saved");
        this.router.navigateByUrl("");
      });
    }
  }

}
