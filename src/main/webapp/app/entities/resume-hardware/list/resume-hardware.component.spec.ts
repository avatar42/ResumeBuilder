import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeHardwareService } from '../service/resume-hardware.service';

import { ResumeHardwareComponent } from './resume-hardware.component';

describe('ResumeHardware Management Component', () => {
  let comp: ResumeHardwareComponent;
  let fixture: ComponentFixture<ResumeHardwareComponent>;
  let service: ResumeHardwareService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeHardwareComponent],
    })
      .overrideTemplate(ResumeHardwareComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeHardwareComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeHardwareService);

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
    expect(comp.resumeHardwares?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
