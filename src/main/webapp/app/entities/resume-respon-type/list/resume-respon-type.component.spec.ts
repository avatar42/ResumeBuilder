import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ResumeResponTypeService } from '../service/resume-respon-type.service';

import { ResumeResponTypeComponent } from './resume-respon-type.component';

describe('ResumeResponType Management Component', () => {
  let comp: ResumeResponTypeComponent;
  let fixture: ComponentFixture<ResumeResponTypeComponent>;
  let service: ResumeResponTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ResumeResponTypeComponent],
    })
      .overrideTemplate(ResumeResponTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ResumeResponTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ResumeResponTypeService);

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
    expect(comp.resumeResponTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
