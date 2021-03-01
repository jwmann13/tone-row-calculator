import { TestBed } from '@angular/core/testing';

import { ToneRowService } from './tone-row.service';

describe('ToneRowService', () => {
  let service: ToneRowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ToneRowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
