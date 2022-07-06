import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResumeSoftxDetailComponent } from './resume-softx-detail.component';

describe('ResumeSoftx Management Detail Component', () => {
  let comp: ResumeSoftxDetailComponent;
  let fixture: ComponentFixture<ResumeSoftxDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ResumeSoftxDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ resumeSoftx: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ResumeSoftxDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ResumeSoftxDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load resumeSoftx on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.resumeSoftx).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
