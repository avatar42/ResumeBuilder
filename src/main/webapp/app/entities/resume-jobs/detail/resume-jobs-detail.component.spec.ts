import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeJobsDetailComponent } from './resume-jobs-detail.component';

describe('ResumeJobs Management Detail Component', () => {
  let comp: ResumeJobsDetailComponent;
  let fixture: ComponentFixture<ResumeJobsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeJobsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeJobs: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeJobsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeJobsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeJobs on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeJobs).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
