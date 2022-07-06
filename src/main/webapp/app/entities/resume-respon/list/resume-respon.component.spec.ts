import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeResponService } from '../service/resume-respon.service';

import { ResumeResponComponent } from './resume-respon.component';

describe('ResumeRespon Management Component', () => {
  let comp: ResumeResponComponent;
  let fixture: ComponentFixture<ResumeResponComponent>;
  let service: ResumeResponService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeResponComponent],
    })
      .overrideTemplate(ResumeResponComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeResponComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeResponService);

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
    expect(comp.resumeRespons?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
