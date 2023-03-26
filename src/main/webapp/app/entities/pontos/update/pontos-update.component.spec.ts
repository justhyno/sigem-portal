import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PontosFormService } from './pontos-form.service';
import { PontosService } from '../service/pontos.service';
import { IPontos } from '../pontos.model';
import { ISpatialUnit } from 'app/entities/spatial-unit/spatial-unit.model';
import { SpatialUnitService } from 'app/entities/spatial-unit/service/spatial-unit.service';

import { PontosUpdateComponent } from './pontos-update.component';

describe('Pontos Management Update Component', () => {
  let comp: PontosUpdateComponent;
  let fixture: ComponentFixture<PontosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pontosFormService: PontosFormService;
  let pontosService: PontosService;
  let spatialUnitService: SpatialUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PontosUpdateComponent],
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
      .overrideTemplate(PontosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PontosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pontosFormService = TestBed.inject(PontosFormService);
    pontosService = TestBed.inject(PontosService);
    spatialUnitService = TestBed.inject(SpatialUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SpatialUnit query and add missing value', () => {
      const pontos: IPontos = { id: 456 };
      const spatialUnit: ISpatialUnit = { id: 17800 };
      pontos.spatialUnit = spatialUnit;

      const spatialUnitCollection: ISpatialUnit[] = [{ id: 67746 }];
      jest.spyOn(spatialUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: spatialUnitCollection })));
      const additionalSpatialUnits = [spatialUnit];
      const expectedCollection: ISpatialUnit[] = [...additionalSpatialUnits, ...spatialUnitCollection];
      jest.spyOn(spatialUnitService, 'addSpatialUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pontos });
      comp.ngOnInit();

      expect(spatialUnitService.query).toHaveBeenCalled();
      expect(spatialUnitService.addSpatialUnitToCollectionIfMissing).toHaveBeenCalledWith(
        spatialUnitCollection,
        ...additionalSpatialUnits.map(expect.objectContaining)
      );
      expect(comp.spatialUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pontos: IPontos = { id: 456 };
      const spatialUnit: ISpatialUnit = { id: 60800 };
      pontos.spatialUnit = spatialUnit;

      activatedRoute.data = of({ pontos });
      comp.ngOnInit();

      expect(comp.spatialUnitsSharedCollection).toContain(spatialUnit);
      expect(comp.pontos).toEqual(pontos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPontos>>();
      const pontos = { id: 123 };
      jest.spyOn(pontosFormService, 'getPontos').mockReturnValue(pontos);
      jest.spyOn(pontosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pontos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pontos }));
      saveSubject.complete();

      // THEN
      expect(pontosFormService.getPontos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pontosService.update).toHaveBeenCalledWith(expect.objectContaining(pontos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPontos>>();
      const pontos = { id: 123 };
      jest.spyOn(pontosFormService, 'getPontos').mockReturnValue({ id: null });
      jest.spyOn(pontosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pontos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pontos }));
      saveSubject.complete();

      // THEN
      expect(pontosFormService.getPontos).toHaveBeenCalled();
      expect(pontosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPontos>>();
      const pontos = { id: 123 };
      jest.spyOn(pontosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pontos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pontosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSpatialUnit', () => {
      it('Should forward to spatialUnitService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(spatialUnitService, 'compareSpatialUnit');
        comp.compareSpatialUnit(entity, entity2);
        expect(spatialUnitService.compareSpatialUnit).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
