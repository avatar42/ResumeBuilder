import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeSoftwareService } from '../service/resume-software.service';

import { ResumeSoftwareComponent } from './resume-software.component';

describe('ResumeSoftware Management Component', () => {
  let comp: ResumeSoftwareComponent;
  let fixture: ComponentFixture<ResumeSoftwareComponent>;
  let service: ResumeSoftwareService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeSoftwareComponent],
    })
      .overrideTemplate(ResumeSoftwareComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeSoftwareComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeSoftwareService);

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
    expect(comp.resumeSoftwares?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
