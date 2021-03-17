import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { ActivatedRouteStub } from 'src/testing/activated-route-stub';
import { ToneRowService } from '../tone-row.service';

import { ShowMatrixComponent } from './show-matrix.component';

describe('ShowMatrixComponent', () => {
  let component: ShowMatrixComponent;
  let fixture: ComponentFixture<ShowMatrixComponent>;
  let getMatrixSpy: jasmine.Spy;
  let getToneRowMetaSpy: jasmine.Spy;
  let routerSpy = jasmine.createSpyObj('Router', ['navigateByUrl']);

  beforeEach(async () => {
    const toneRowServiceSpy = jasmine.createSpyObj('ToneRowService', ['getMatrix', 'getToneRowMeta']);
    const activatedRouteStub = new ActivatedRouteStub();

    getMatrixSpy = toneRowServiceSpy.getMatrix.and.returnValue(of([]));
    getToneRowMetaSpy = toneRowServiceSpy.getToneRowMeta.and.returnValue(of({}));
    await TestBed.configureTestingModule({
      declarations: [ ShowMatrixComponent ],
      providers: [
        { provide: NgbModal},
        { provide: ToneRowService, useValue: toneRowServiceSpy },
        { provide: ActivatedRoute, useValue: activatedRouteStub },
        { provide: Router, useValue: routerSpy }
      ]
    })
    .compileComponents();
  });

  beforeEach(waitForAsync(() => {
    fixture = TestBed.createComponent(ShowMatrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
