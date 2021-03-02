import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToneRowListComponent } from './tone-row-list.component';

describe('ToneRowListComponent', () => {
  let component: ToneRowListComponent;
  let fixture: ComponentFixture<ToneRowListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToneRowListComponent ]
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
