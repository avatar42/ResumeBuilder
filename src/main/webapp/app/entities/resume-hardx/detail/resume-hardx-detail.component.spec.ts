import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeHardxDetailComponent } from './resume-hardx-detail.component';

describe('ResumeHardx Management Detail Component', () => {
  let comp: ResumeHardxDetailComponent;
  let fixture: ComponentFixture<ResumeHardxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeHardxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeHardx: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeHardxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeHardxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeHardx on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeHardx).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
