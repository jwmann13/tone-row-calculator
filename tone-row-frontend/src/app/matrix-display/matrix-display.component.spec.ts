import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatrixDisplayComponent } from './matrix-display.component';

describe('MatrixDisplayComponent', () => {
  let component: MatrixDisplayComponent;
  let fixture: ComponentFixture<MatrixDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MatrixDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MatrixDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
