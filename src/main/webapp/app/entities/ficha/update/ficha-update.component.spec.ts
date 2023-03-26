import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FichaFormService } from './ficha-form.service';
import { FichaService } from '../service/ficha.service';
import { IFicha } from '../ficha.model';
import { IProjecto } from 'app/entities/projecto/projecto.model';
import { ProjectoService } from 'app/entities/projecto/service/projecto.service';

import { FichaUpdateComponent } from './ficha-update.component';

describe('Ficha Management Update Component', () => {
  let comp: FichaUpdateComponent;
  let fixture: ComponentFixture<FichaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fichaFormService: FichaFormService;
  let fichaService: FichaService;
  let projectoService: ProjectoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FichaUpdateComponent],
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
      .overrideTemplate(FichaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FichaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fichaFormService = TestBed.inject(FichaFormService);
    fichaService = TestBed.inject(FichaService);
    projectoService = TestBed.inject(ProjectoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Projecto query and add missing value', () => {
      const ficha: IFicha = { id: 456 };
      const projecto: IProjecto = { id: 2583 };
      ficha.projecto = projecto;

      const projectoCollection: IProjecto[] = [{ id: 10029 }];
      jest.spyOn(projectoService, 'query').mockReturnValue(of(new HttpResponse({ body: projectoCollection })));
      const additionalProjectos = [projecto];
      const expectedCollection: IProjecto[] = [...additionalProjectos, ...projectoCollection];
      jest.spyOn(projectoService, 'addProjectoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ficha });
      comp.ngOnInit();

      expect(projectoService.query).toHaveBeenCalled();
      expect(projectoService.addProjectoToCollectionIfMissing).toHaveBeenCalledWith(
        projectoCollection,
        ...additionalProjectos.map(expect.objectContaining)
      );
      expect(comp.projectosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ficha: IFicha = { id: 456 };
      const projecto: IProjecto = { id: 50246 };
      ficha.projecto = projecto;

      activatedRoute.data = of({ ficha });
      comp.ngOnInit();

      expect(comp.projectosSharedCollection).toContain(projecto);
      expect(comp.ficha).toEqual(ficha);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFicha>>();
      const ficha = { id: 123 };
      jest.spyOn(fichaFormService, 'getFicha').mockReturnValue(ficha);
      jest.spyOn(fichaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ficha });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ficha }));
      saveSubject.complete();

      // THEN
      expect(fichaFormService.getFicha).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fichaService.update).toHaveBeenCalledWith(expect.objectContaining(ficha));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFicha>>();
      const ficha = { id: 123 };
      jest.spyOn(fichaFormService, 'getFicha').mockReturnValue({ id: null });
      jest.spyOn(fichaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ficha: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ficha }));
      saveSubject.complete();

      // THEN
      expect(fichaFormService.getFicha).toHaveBeenCalled();
      expect(fichaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFicha>>();
      const ficha = { id: 123 };
      jest.spyOn(fichaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ficha });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fichaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProjecto', () => {
      it('Should forward to projectoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectoService, 'compareProjecto');
        comp.compareProjecto(entity, entity2);
        expect(projectoService.compareProjecto).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
