import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeSummaryService } from '../service/resume-summary.service';

import { ResumeSummaryComponent } from './resume-summary.component';

describe('ResumeSummary Management Component', () => {
  let comp: ResumeSummaryComponent;
  let fixture: ComponentFixture<ResumeSummaryComponent>;
  let service: ResumeSummaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeSummaryComponent],
    })
      .overrideTemplate(ResumeSummaryComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeSummaryComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeSummaryService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.resumeSummaries?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
