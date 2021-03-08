import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowMatrixComponent } from './show-matrix.component';

describe('ShowMatrixComponent', () => {
  let component: ShowMatrixComponent;
  let fixture: ComponentFixture<ShowMatrixComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowMatrixComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowMatrixComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
