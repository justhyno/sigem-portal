import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AlfaFormService } from './alfa-form.service';
import { AlfaService } from '../service/alfa.service';
import { IAlfa } from '../alfa.model';
import { IFicha } from 'app/entities/ficha/ficha.model';
import { FichaService } from 'app/entities/ficha/service/ficha.service';

import { AlfaUpdateComponent } from './alfa-update.component';

describe('Alfa Management Update Component', () => {
  let comp: AlfaUpdateComponent;
  let fixture: ComponentFixture<AlfaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let alfaFormService: AlfaFormService;
  let alfaService: AlfaService;
  let fichaService: FichaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AlfaUpdateComponent],
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
      .overrideTemplate(AlfaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AlfaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    alfaFormService = TestBed.inject(AlfaFormService);
    alfaService = TestBed.inject(AlfaService);
    fichaService = TestBed.inject(FichaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Ficha query and add missing value', () => {
      const alfa: IAlfa = { id: 456 };
      const ficha: IFicha = { id: 75811 };
      alfa.ficha = ficha;

      const fichaCollection: IFicha[] = [{ id: 24400 }];
      jest.spyOn(fichaService, 'query').mockReturnValue(of(new HttpResponse({ body: fichaCollection })));
      const additionalFichas = [ficha];
      const expectedCollection: IFicha[] = [...additionalFichas, ...fichaCollection];
      jest.spyOn(fichaService, 'addFichaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ alfa });
      comp.ngOnInit();

      expect(fichaService.query).toHaveBeenCalled();
      expect(fichaService.addFichaToCollectionIfMissing).toHaveBeenCalledWith(
        fichaCollection,
        ...additionalFichas.map(expect.objectContaining)
      );
      expect(comp.fichasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const alfa: IAlfa = { id: 456 };
      const ficha: IFicha = { id: 81077 };
      alfa.ficha = ficha;

      activatedRoute.data = of({ alfa });
      comp.ngOnInit();

      expect(comp.fichasSharedCollection).toContain(ficha);
      expect(comp.alfa).toEqual(alfa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlfa>>();
      const alfa = { id: 123 };
      jest.spyOn(alfaFormService, 'getAlfa').mockReturnValue(alfa);
      jest.spyOn(alfaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alfa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alfa }));
      saveSubject.complete();

      // THEN
      expect(alfaFormService.getAlfa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(alfaService.update).toHaveBeenCalledWith(expect.objectContaining(alfa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlfa>>();
      const alfa = { id: 123 };
      jest.spyOn(alfaFormService, 'getAlfa').mockReturnValue({ id: null });
      jest.spyOn(alfaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alfa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: alfa }));
      saveSubject.complete();

      // THEN
      expect(alfaFormService.getAlfa).toHaveBeenCalled();
      expect(alfaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAlfa>>();
      const alfa = { id: 123 };
      jest.spyOn(alfaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ alfa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(alfaService.update).toHaveBeenCalled();
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
