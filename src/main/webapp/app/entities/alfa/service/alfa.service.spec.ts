import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAlfa } from '../alfa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../alfa.test-samples';

import { AlfaService, RestAlfa } from './alfa.service';

const requireRestSample: RestAlfa = {
  ...sampleWithRequiredData,
  dataNascimento: sampleWithRequiredData.dataNascimento?.format(DATE_FORMAT),
  dataLevantamento: sampleWithRequiredData.dataLevantamento?.format(DATE_FORMAT),
};

describe('Alfa Service', () => {
  let service: AlfaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAlfa | IAlfa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AlfaService);
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

    it('should create a Alfa', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const alfa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(alfa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Alfa', () => {
      const alfa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(alfa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Alfa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Alfa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Alfa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAlfaToCollectionIfMissing', () => {
      it('should add a Alfa to an empty array', () => {
        const alfa: IAlfa = sampleWithRequiredData;
        expectedResult = service.addAlfaToCollectionIfMissing([], alfa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alfa);
      });

      it('should not add a Alfa to an array that contains it', () => {
        const alfa: IAlfa = sampleWithRequiredData;
        const alfaCollection: IAlfa[] = [
          {
            ...alfa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAlfaToCollectionIfMissing(alfaCollection, alfa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Alfa to an array that doesn't contain it", () => {
        const alfa: IAlfa = sampleWithRequiredData;
        const alfaCollection: IAlfa[] = [sampleWithPartialData];
        expectedResult = service.addAlfaToCollectionIfMissing(alfaCollection, alfa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alfa);
      });

      it('should add only unique Alfa to an array', () => {
        const alfaArray: IAlfa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const alfaCollection: IAlfa[] = [sampleWithRequiredData];
        expectedResult = service.addAlfaToCollectionIfMissing(alfaCollection, ...alfaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const alfa: IAlfa = sampleWithRequiredData;
        const alfa2: IAlfa = sampleWithPartialData;
        expectedResult = service.addAlfaToCollectionIfMissing([], alfa, alfa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(alfa);
        expect(expectedResult).toContain(alfa2);
      });

      it('should accept null and undefined values', () => {
        const alfa: IAlfa = sampleWithRequiredData;
        expectedResult = service.addAlfaToCollectionIfMissing([], null, alfa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(alfa);
      });

      it('should return initial array if no Alfa is added', () => {
        const alfaCollection: IAlfa[] = [sampleWithRequiredData];
        expectedResult = service.addAlfaToCollectionIfMissing(alfaCollection, undefined, null);
        expect(expectedResult).toEqual(alfaCollection);
      });
    });

    describe('compareAlfa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAlfa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAlfa(entity1, entity2);
        const compareResult2 = service.compareAlfa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAlfa(entity1, entity2);
        const compareResult2 = service.compareAlfa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAlfa(entity1, entity2);
        const compareResult2 = service.compareAlfa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
