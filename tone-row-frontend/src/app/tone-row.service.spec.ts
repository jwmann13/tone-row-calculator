import { TestBed } from '@angular/core/testing';

import { ToneRowService } from './tone-row.service';

describe('ToneRowService', () => {
  let service: ToneRowService;
  let httpClient: { get: jasmine.Spy };

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [] });
    httpClient = jasmine.createSpyObj('HttpClient', ['get']);
    service = new ToneRowService(httpClient as any);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
