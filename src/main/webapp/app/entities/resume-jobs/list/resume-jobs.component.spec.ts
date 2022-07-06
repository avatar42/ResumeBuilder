import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeJobsService } from '../service/resume-jobs.service';

import { ResumeJobsComponent } from './resume-jobs.component';

describe('ResumeJobs Management Component', () => {
  let comp: ResumeJobsComponent;
  let fixture: ComponentFixture<ResumeJobsComponent>;
  let service: ResumeJobsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeJobsComponent],
    })
      .overrideTemplate(ResumeJobsComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeJobsComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeJobsService);

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
    expect(comp.resumeJobs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
