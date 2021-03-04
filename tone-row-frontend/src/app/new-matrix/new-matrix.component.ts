import { Component, OnInit } from '@angular/core';
import { ComposerWorkService } from '../composer-work.service';
import { ToneRowService } from '../tone-row.service';
import { faSave } from '@fortawesome/free-solid-svg-icons';

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

  constructor(private cwService: ComposerWorkService, private trService: ToneRowService) {
    this.newRow = [];
    this.tempMatrix = [];
    this.buttonStack = [];
  }

  ngOnInit(): void {
  }
  
  addToRow(event: MouseEvent, pitchClass: number) {
    if (event.target && event.target instanceof HTMLButtonElement){
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

  saveToneRow() {
    this.trService.postToneRow({ noteOrder: this.newRow, workId: null })
    .subscribe(x => console.log(x));
  }

}
