import { Component, Directive, EventEmitter, Input, OnChanges, OnInit, Output, QueryList, SimpleChanges, ViewChildren } from '@angular/core';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { Composer, ToneRowMeta, ToneRowService, Work } from '../tone-row.service';

// TABLE SORT
export type SortColumn = 'work' | 'composers' | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: SortDirection } = { 'asc': 'desc', 'desc': '', '': 'asc' }
const compare = (v1: Work | Composer[], v2: Work | Composer[]) => {
  if (instanceOfWork(v1) && instanceOfWork(v2)) {
    return (v1 as Work).title.replace(/"/g, "") < (v2 as Work).title.replace(/"/g, "")
      ? -1 : (v1 as Work).title.replace(/"/g, "") > (v2 as Work).title.replace(/"/g, "")
        ? 1 : 0;
  } else if (instanceOfComposerArray(v1) && instanceOfComposerArray(v2)) {
    return ((v1 as Composer[]).map(c => c.name).join(" ") as string) < ((v2 as Composer[]).map(c => c.name).join(" ") as string)
      ? -1
      : ((v1 as Composer[]).map(c => c.name).join(" ") as string) > ((v2 as Composer[]).map(c => c.name).join(" ") as string)
        ? 1 : 0;
  } else return 0;
}

const instanceOfWork = (w: any) => {
  return 'title' in w && 'workId' in w;
}

const instanceOfComposerArray = (cArr: any) => {
  return (cArr as []).every(c => ('name' in c && 'composerId' in c));
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
    this.sort.emit({ column: this.sortable, direction: this.direction });
  }
}

// TABLE SEARCH
const search = (text: string, rowList: ToneRowMeta[]): ToneRowMeta[] => {
  return rowList.filter(tr => {
    const term = text.toLowerCase().trim();
    return tr.composers.map(c => c.name).join(" ").toLowerCase().includes(term)
      || tr.work.title.toLowerCase().includes(term)
      || tr.toneRow.noteOrder.map(n => n.pitchClass === 10 ? "t": n.pitchClass === 11 ? "e" : n.pitchClass).join('').includes(term)
      || tr.toneRow.noteOrder.map(n => n.accidental ? n.flatName : n.naturalName).join('').toLowerCase().includes(term)
      || tr.toneRow.noteOrder.map(n => n.accidental ? n.sharpName : n.naturalName).join('').toLowerCase().includes(term)
  })
}

@Component({
  selector: 'app-tone-row-list',
  templateUrl: './tone-row-list.component.html',
  styleUrls: ['./tone-row-list.component.scss']
})
export class ToneRowListComponent implements OnInit {
  allToneRows: ToneRowMeta[] = [];
  filteredToneRows: ToneRowMeta[] = [];
  filter: FormControl = new FormControl('');
  @ViewChildren(NgbSortableHeader) headers: QueryList<NgbSortableHeader> | undefined;

  constructor(private service: ToneRowService) {
  }

  ngOnInit(): void {
    this.service.getAllToneRowMeta()
      .subscribe(data => {
        if (data) {
          this.allToneRows = data;
          this.filteredToneRows = data;
        } else {
          throw new Error("No Tone Rows!")
        }
      })
    this.filter.valueChanges
      .pipe(
        startWith(''),
        map(text => search(text, this.allToneRows))
      ).subscribe(data => this.filteredToneRows = data);
  }

  onSort({ column, direction }: SortEvent) {
    this.headers?.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    })

    if (direction === '' || column === '') {
      this.filteredToneRows = this.filteredToneRows;
    } else {
      this.filteredToneRows = [...this.filteredToneRows].sort((a, b) => {
        const result = compare(a[column], b[column]);
        return direction === 'asc' ? result : -result;
      })

    }
  }

}
