import { TestBed } from '@angular/core/testing';

import { ComposerWorkService } from './composer-work.service';

describe('ComposerWorkService', () => {
  let service: ComposerWorkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComposerWorkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
