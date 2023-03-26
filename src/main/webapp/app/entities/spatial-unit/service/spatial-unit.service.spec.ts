import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISpatialUnit } from '../spatial-unit.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../spatial-unit.test-samples';

import { SpatialUnitService } from './spatial-unit.service';

const requireRestSample: ISpatialUnit = {
  ...sampleWithRequiredData,
};

describe('SpatialUnit Service', () => {
  let service: SpatialUnitService;
  let httpMock: HttpTestingController;
  let expectedResult: ISpatialUnit | ISpatialUnit[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SpatialUnitService);
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

    it('should create a SpatialUnit', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const spatialUnit = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(spatialUnit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SpatialUnit', () => {
      const spatialUnit = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(spatialUnit).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SpatialUnit', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SpatialUnit', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SpatialUnit', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSpatialUnitToCollectionIfMissing', () => {
      it('should add a SpatialUnit to an empty array', () => {
        const spatialUnit: ISpatialUnit = sampleWithRequiredData;
        expectedResult = service.addSpatialUnitToCollectionIfMissing([], spatialUnit);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(spatialUnit);
      });

      it('should not add a SpatialUnit to an array that contains it', () => {
        const spatialUnit: ISpatialUnit = sampleWithRequiredData;
        const spatialUnitCollection: ISpatialUnit[] = [
          {
            ...spatialUnit,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSpatialUnitToCollectionIfMissing(spatialUnitCollection, spatialUnit);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SpatialUnit to an array that doesn't contain it", () => {
        const spatialUnit: ISpatialUnit = sampleWithRequiredData;
        const spatialUnitCollection: ISpatialUnit[] = [sampleWithPartialData];
        expectedResult = service.addSpatialUnitToCollectionIfMissing(spatialUnitCollection, spatialUnit);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(spatialUnit);
      });

      it('should add only unique SpatialUnit to an array', () => {
        const spatialUnitArray: ISpatialUnit[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const spatialUnitCollection: ISpatialUnit[] = [sampleWithRequiredData];
        expectedResult = service.addSpatialUnitToCollectionIfMissing(spatialUnitCollection, ...spatialUnitArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const spatialUnit: ISpatialUnit = sampleWithRequiredData;
        const spatialUnit2: ISpatialUnit = sampleWithPartialData;
        expectedResult = service.addSpatialUnitToCollectionIfMissing([], spatialUnit, spatialUnit2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(spatialUnit);
        expect(expectedResult).toContain(spatialUnit2);
      });

      it('should accept null and undefined values', () => {
        const spatialUnit: ISpatialUnit = sampleWithRequiredData;
        expectedResult = service.addSpatialUnitToCollectionIfMissing([], null, spatialUnit, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(spatialUnit);
      });

      it('should return initial array if no SpatialUnit is added', () => {
        const spatialUnitCollection: ISpatialUnit[] = [sampleWithRequiredData];
        expectedResult = service.addSpatialUnitToCollectionIfMissing(spatialUnitCollection, undefined, null);
        expect(expectedResult).toEqual(spatialUnitCollection);
      });
    });

    describe('compareSpatialUnit', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSpatialUnit(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSpatialUnit(entity1, entity2);
        const compareResult2 = service.compareSpatialUnit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSpatialUnit(entity1, entity2);
        const compareResult2 = service.compareSpatialUnit(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSpatialUnit(entity1, entity2);
        const compareResult2 = service.compareSpatialUnit(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
