import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeSoftwareDetailComponent } from './resume-software-detail.component';

describe('ResumeSoftware Management Detail Component', () => {
  let comp: ResumeSoftwareDetailComponent;
  let fixture: ComponentFixture<ResumeSoftwareDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeSoftwareDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeSoftware: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeSoftwareDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeSoftwareDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeSoftware on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeSoftware).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
