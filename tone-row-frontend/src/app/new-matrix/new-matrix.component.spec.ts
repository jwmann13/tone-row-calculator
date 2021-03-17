import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { ToneRowService } from '../tone-row.service';

import { NewMatrixComponent } from './new-matrix.component';

describe('NewMatrixComponent', () => {
  let component: NewMatrixComponent;
  let fixture: ComponentFixture<NewMatrixComponent>;
  let postToneRowMetaSpy: jasmine.Spy;
  let routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);

  beforeEach(async () => {
    const toneRowServiceSpy = jasmine.createSpyObj('ToneRowService', ['postToneRowMeta']);

    postToneRowMetaSpy = toneRowServiceSpy.postToneRowMeta.and.returnValue(of({}));

    await TestBed.configureTestingModule({
      declarations: [ NewMatrixComponent ],
      providers: [
        { provide: NgbModal},
        { provide: ToneRowService, useValue: toneRowServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewMatrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
