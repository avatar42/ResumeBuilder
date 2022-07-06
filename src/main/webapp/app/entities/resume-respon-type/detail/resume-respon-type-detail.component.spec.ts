import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeResponTypeDetailComponent } from './resume-respon-type-detail.component';

describe('ResumeResponType Management Detail Component', () => {
  let comp: ResumeResponTypeDetailComponent;
  let fixture: ComponentFixture<ResumeResponTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeResponTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeResponType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeResponTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeResponTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeResponType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeResponType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
