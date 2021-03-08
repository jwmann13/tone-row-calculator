import { Component, Directive, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { Composer, ToneRowMeta, ToneRowService, Work } from '../tone-row.service';

export type SortColumn = 'work' | 'composers' | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: {[key: string]: SortDirection} = {'asc': 'desc', 'desc': '', '': 'asc'}
const compare = (v1: Work | Composer[], v2: Work | Composer[]) => {
  if (instanceOfWork(v1) && instanceOfWork(v2)) {
    return (v1 as Work).title > (v2 as Work).title ? -1 : (v1 as Work).title < (v2 as Work).title ? 1 : 0;
  } else if (instanceOfComposerArray(v1) && instanceOfComposerArray(v2)) {
    return ((v1 as Composer[]).map(c => c.name).join(" ") as string) > ((v2 as Composer[]).map(c => c.name).join(" ") as string)
    ? -1
    : ((v1 as Composer[]).map(c => c.name).join(" ") as string) < ((v2 as Composer[]).map(c => c.name).join(" ") as string)
    ? 1 : 0;
  } else return 0;
}

const instanceOfWork = (w: any) => {
  return 'title' in w;
}

const instanceOfComposerArray = (cArr: any) => {
  return (cArr as []).every(c=> ('name' in c && 'composerId' in c));
}

export interface SortEvent {
  column: SortColumn;
  direction: SortDirection;
}

@Directive({
  selector: 'th[sortable]',
  host: {
    '[class.asc]': 'direction === "asc"',
    '[class.desc]': 'direction === "desc"',
    '(click)': 'rotate()'
  }
})
export class NgbSortableHeader {
  @Input() sortable: SortColumn = '';
  @Input() direction: SortDirection = '';
  @Output() sort = new EventEmitter<SortEvent>();

  rotate() {
    this.direction = rotate[this.direction];
    this.sort.emit({column: this.sortable, direction: this.direction});
  }
}

@Component({
  selector: 'app-tone-row-list',
  templateUrl: './tone-row-list.component.html',
  styleUrls: ['./tone-row-list.component.scss']
})
export class ToneRowListComponent implements OnInit {
  allToneRows: ToneRowMeta[];
  @ViewChildren(NgbSortableHeader) headers: QueryList<NgbSortableHeader> | undefined;

  constructor(private service: ToneRowService) {
    this.allToneRows = [];
  }

  ngOnInit(): void {
    this.service.getAllToneRowMeta().subscribe(data => {
      if (data) {
        // console.log(data)
        this.allToneRows = data;
      } else {
        throw new Error("No Tone Rows!")
      }
    })
  }

  onSort({column, direction}: SortEvent) {
    this.headers?.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    })

    if (direction === '' || column === '') {
      this.service.getAllToneRowMeta().subscribe(data => {
        this.allToneRows = data
      });
    } else {
      this.allToneRows = [...this.allToneRows].sort((a,b) =>{
        const result = compare(a[column], b[column]);
        return direction === 'asc' ? result : -result;
      })

    }
  }

}
