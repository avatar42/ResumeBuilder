import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeSummaryDetailComponent } from './resume-summary-detail.component';

describe('ResumeSummary Management Detail Component', () => {
  let comp: ResumeSummaryDetailComponent;
  let fixture: ComponentFixture<ResumeSummaryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeSummaryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeSummary: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeSummaryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeSummaryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeSummary on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeSummary).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
