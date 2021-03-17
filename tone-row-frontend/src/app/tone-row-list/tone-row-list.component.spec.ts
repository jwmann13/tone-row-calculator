import { ComponentFixture, TestBed } from '@angular/core/testing';
import { from, Observable } from 'rxjs';
import { ToneRowMeta, ToneRowService } from '../tone-row.service';

import { ToneRowListComponent } from './tone-row-list.component';

describe('ToneRowListComponent', () => {
  let component: ToneRowListComponent;
  let fixture: ComponentFixture<ToneRowListComponent>;
  let toneRowServiceStub: Partial<ToneRowService>;

  beforeEach(async () => {
    toneRowServiceStub = {
      getAllToneRowMeta(): Observable<ToneRowMeta[]> {
        return from([]);
      }
    }
    await TestBed.configureTestingModule({
      declarations: [ ToneRowListComponent ],
      providers: [{ provide: ToneRowService, useValue: toneRowServiceStub }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToneRowListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
