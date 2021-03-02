import { Component, Input, OnInit } from '@angular/core';
import { ToneRow } from '../models/ToneRow';
import { ToneRowService } from '../tone-row.service';

@Component({
  selector: 'app-tone-row-list',
  templateUrl: './tone-row-list.component.html',
  styleUrls: ['./tone-row-list.component.scss']
})
export class ToneRowListComponent implements OnInit {
  @Input() allToneRows: ToneRow[]; 

  constructor(private service: ToneRowService) {
    this.allToneRows = [];
  }

  ngOnInit(): void {
    this.service.getAllToneRows().subscribe(data => {
      if (data) {
        // console.log(data)
        for (let key of Object.keys(data)) {
          // console.log(key, data[key])
          this.allToneRows.push(data[key]);
        }
      } else {
        throw new Error("No Tone Rows!")
      }
    })
  }

}
