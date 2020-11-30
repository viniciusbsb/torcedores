import { TestBed } from '@angular/core/testing';

import { TorcedorService } from './torcedor.service';

describe('TorcedorService', () => {
  let service: TorcedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TorcedorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
