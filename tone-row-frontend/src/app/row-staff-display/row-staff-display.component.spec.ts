import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowStaffDisplayComponent } from './row-staff-display.component';

describe('RowStaffDisplayComponent', () => {
  let component: RowStaffDisplayComponent;
  let fixture: ComponentFixture<RowStaffDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RowStaffDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RowStaffDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
