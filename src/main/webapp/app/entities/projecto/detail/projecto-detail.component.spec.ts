import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectoDetailComponent } from './projecto-detail.component';

describe('Projecto Management Detail Component', () => {
  let comp: ProjectoDetailComponent;
  let fixture: ComponentFixture<ProjectoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projecto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projecto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projecto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
