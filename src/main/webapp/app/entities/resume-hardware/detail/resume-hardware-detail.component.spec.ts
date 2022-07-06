import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeHardwareDetailComponent } from './resume-hardware-detail.component';

describe('ResumeHardware Management Detail Component', () => {
  let comp: ResumeHardwareDetailComponent;
  let fixture: ComponentFixture<ResumeHardwareDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeHardwareDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeHardware: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeHardwareDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeHardwareDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeHardware on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeHardware).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
