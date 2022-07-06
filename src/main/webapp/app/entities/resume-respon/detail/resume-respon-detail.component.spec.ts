import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeResponDetailComponent } from './resume-respon-detail.component';

describe('ResumeRespon Management Detail Component', () => {
  let comp: ResumeResponDetailComponent;
  let fixture: ComponentFixture<ResumeResponDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeResponDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeRespon: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeResponDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeResponDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeRespon on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeRespon).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
