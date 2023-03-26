import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SpatialUnitFormService } from './spatial-unit-form.service';
import { SpatialUnitService } from '../service/spatial-unit.service';
import { ISpatialUnit } from '../spatial-unit.model';
import { IFicha } from 'app/entities/ficha/ficha.model';
import { FichaService } from 'app/entities/ficha/service/ficha.service';

import { SpatialUnitUpdateComponent } from './spatial-unit-update.component';

describe('SpatialUnit Management Update Component', () => {
  let comp: SpatialUnitUpdateComponent;
  let fixture: ComponentFixture<SpatialUnitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let spatialUnitFormService: SpatialUnitFormService;
  let spatialUnitService: SpatialUnitService;
  let fichaService: FichaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SpatialUnitUpdateComponent],
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
      .overrideTemplate(SpatialUnitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SpatialUnitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    spatialUnitFormService = TestBed.inject(SpatialUnitFormService);
    spatialUnitService = TestBed.inject(SpatialUnitService);
    fichaService = TestBed.inject(FichaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ficha query and add missing value', () => {
      const spatialUnit: ISpatialUnit = { id: 456 };
      const ficha: IFicha = { id: 56625 };
      spatialUnit.ficha = ficha;

      const fichaCollection: IFicha[] = [{ id: 41344 }];
      jest.spyOn(fichaService, 'query').mockReturnValue(of(new HttpResponse({ body: fichaCollection })));
      const additionalFichas = [ficha];
      const expectedCollection: IFicha[] = [...additionalFichas, ...fichaCollection];
      jest.spyOn(fichaService, 'addFichaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ spatialUnit });
      comp.ngOnInit();

      expect(fichaService.query).toHaveBeenCalled();
      expect(fichaService.addFichaToCollectionIfMissing).toHaveBeenCalledWith(
        fichaCollection,
        ...additionalFichas.map(expect.objectContaining)
      );
      expect(comp.fichasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const spatialUnit: ISpatialUnit = { id: 456 };
      const ficha: IFicha = { id: 89950 };
      spatialUnit.ficha = ficha;

      activatedRoute.data = of({ spatialUnit });
      comp.ngOnInit();

      expect(comp.fichasSharedCollection).toContain(ficha);
      expect(comp.spatialUnit).toEqual(spatialUnit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpatialUnit>>();
      const spatialUnit = { id: 123 };
      jest.spyOn(spatialUnitFormService, 'getSpatialUnit').mockReturnValue(spatialUnit);
      jest.spyOn(spatialUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spatialUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: spatialUnit }));
      saveSubject.complete();

      // THEN
      expect(spatialUnitFormService.getSpatialUnit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(spatialUnitService.update).toHaveBeenCalledWith(expect.objectContaining(spatialUnit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpatialUnit>>();
      const spatialUnit = { id: 123 };
      jest.spyOn(spatialUnitFormService, 'getSpatialUnit').mockReturnValue({ id: null });
      jest.spyOn(spatialUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spatialUnit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: spatialUnit }));
      saveSubject.complete();

      // THEN
      expect(spatialUnitFormService.getSpatialUnit).toHaveBeenCalled();
      expect(spatialUnitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISpatialUnit>>();
      const spatialUnit = { id: 123 };
      jest.spyOn(spatialUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ spatialUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(spatialUnitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFicha', () => {
      it('Should forward to fichaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fichaService, 'compareFicha');
        comp.compareFicha(entity, entity2);
        expect(fichaService.compareFicha).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
