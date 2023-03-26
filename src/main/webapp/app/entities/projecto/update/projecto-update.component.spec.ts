import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProjectoFormService } from './projecto-form.service';
import { ProjectoService } from '../service/projecto.service';
import { IProjecto } from '../projecto.model';

import { ProjectoUpdateComponent } from './projecto-update.component';

describe('Projecto Management Update Component', () => {
  let comp: ProjectoUpdateComponent;
  let fixture: ComponentFixture<ProjectoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectoFormService: ProjectoFormService;
  let projectoService: ProjectoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProjectoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProjectoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectoFormService = TestBed.inject(ProjectoFormService);
    projectoService = TestBed.inject(ProjectoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const projecto: IProjecto = { id: 456 };

      activatedRoute.data = of({ projecto });
      comp.ngOnInit();

      expect(comp.projecto).toEqual(projecto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjecto>>();
      const projecto = { id: 123 };
      jest.spyOn(projectoFormService, 'getProjecto').mockReturnValue(projecto);
      jest.spyOn(projectoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projecto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projecto }));
      saveSubject.complete();

      // THEN
      expect(projectoFormService.getProjecto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectoService.update).toHaveBeenCalledWith(expect.objectContaining(projecto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjecto>>();
      const projecto = { id: 123 };
      jest.spyOn(projectoFormService, 'getProjecto').mockReturnValue({ id: null });
      jest.spyOn(projectoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projecto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: projecto }));
      saveSubject.complete();

      // THEN
      expect(projectoFormService.getProjecto).toHaveBeenCalled();
      expect(projectoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProjecto>>();
      const projecto = { id: 123 };
      jest.spyOn(projectoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ projecto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
