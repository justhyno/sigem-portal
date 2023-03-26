import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFicha } from '../ficha.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ficha.test-samples';

import { FichaService } from './ficha.service';

const requireRestSample: IFicha = {
  ...sampleWithRequiredData,
};

describe('Ficha Service', () => {
  let service: FichaService;
  let httpMock: HttpTestingController;
  let expectedResult: IFicha | IFicha[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FichaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Ficha', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ficha = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ficha).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ficha', () => {
      const ficha = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ficha).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ficha', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ficha', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Ficha', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFichaToCollectionIfMissing', () => {
      it('should add a Ficha to an empty array', () => {
        const ficha: IFicha = sampleWithRequiredData;
        expectedResult = service.addFichaToCollectionIfMissing([], ficha);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ficha);
      });

      it('should not add a Ficha to an array that contains it', () => {
        const ficha: IFicha = sampleWithRequiredData;
        const fichaCollection: IFicha[] = [
          {
            ...ficha,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFichaToCollectionIfMissing(fichaCollection, ficha);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ficha to an array that doesn't contain it", () => {
        const ficha: IFicha = sampleWithRequiredData;
        const fichaCollection: IFicha[] = [sampleWithPartialData];
        expectedResult = service.addFichaToCollectionIfMissing(fichaCollection, ficha);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ficha);
      });

      it('should add only unique Ficha to an array', () => {
        const fichaArray: IFicha[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fichaCollection: IFicha[] = [sampleWithRequiredData];
        expectedResult = service.addFichaToCollectionIfMissing(fichaCollection, ...fichaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ficha: IFicha = sampleWithRequiredData;
        const ficha2: IFicha = sampleWithPartialData;
        expectedResult = service.addFichaToCollectionIfMissing([], ficha, ficha2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ficha);
        expect(expectedResult).toContain(ficha2);
      });

      it('should accept null and undefined values', () => {
        const ficha: IFicha = sampleWithRequiredData;
        expectedResult = service.addFichaToCollectionIfMissing([], null, ficha, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ficha);
      });

      it('should return initial array if no Ficha is added', () => {
        const fichaCollection: IFicha[] = [sampleWithRequiredData];
        expectedResult = service.addFichaToCollectionIfMissing(fichaCollection, undefined, null);
        expect(expectedResult).toEqual(fichaCollection);
      });
    });

    describe('compareFicha', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFicha(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFicha(entity1, entity2);
        const compareResult2 = service.compareFicha(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFicha(entity1, entity2);
        const compareResult2 = service.compareFicha(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFicha(entity1, entity2);
        const compareResult2 = service.compareFicha(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
