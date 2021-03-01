import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToneRowDisplayComponent } from './tone-row-display.component';

describe('ToneRowDisplayComponent', () => {
  let component: ToneRowDisplayComponent;
  let fixture: ComponentFixture<ToneRowDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToneRowDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToneRowDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
