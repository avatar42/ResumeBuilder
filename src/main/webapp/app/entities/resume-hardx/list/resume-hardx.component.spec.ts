import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeHardxService } from '../service/resume-hardx.service';

import { ResumeHardxComponent } from './resume-hardx.component';

describe('ResumeHardx Management Component', () => {
  let comp: ResumeHardxComponent;
  let fixture: ComponentFixture<ResumeHardxComponent>;
  let service: ResumeHardxService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeHardxComponent],
    })
      .overrideTemplate(ResumeHardxComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeHardxComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeHardxService);

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
    expect(comp.resumeHardxes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
