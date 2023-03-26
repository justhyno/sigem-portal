import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProjecto } from '../projecto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../projecto.test-samples';

import { ProjectoService } from './projecto.service';

const requireRestSample: IProjecto = {
  ...sampleWithRequiredData,
};

describe('Projecto Service', () => {
  let service: ProjectoService;
  let httpMock: HttpTestingController;
  let expectedResult: IProjecto | IProjecto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProjectoService);
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

    it('should create a Projecto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const projecto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(projecto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Projecto', () => {
      const projecto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(projecto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Projecto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Projecto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Projecto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProjectoToCollectionIfMissing', () => {
      it('should add a Projecto to an empty array', () => {
        const projecto: IProjecto = sampleWithRequiredData;
        expectedResult = service.addProjectoToCollectionIfMissing([], projecto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projecto);
      });

      it('should not add a Projecto to an array that contains it', () => {
        const projecto: IProjecto = sampleWithRequiredData;
        const projectoCollection: IProjecto[] = [
          {
            ...projecto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProjectoToCollectionIfMissing(projectoCollection, projecto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Projecto to an array that doesn't contain it", () => {
        const projecto: IProjecto = sampleWithRequiredData;
        const projectoCollection: IProjecto[] = [sampleWithPartialData];
        expectedResult = service.addProjectoToCollectionIfMissing(projectoCollection, projecto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projecto);
      });

      it('should add only unique Projecto to an array', () => {
        const projectoArray: IProjecto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const projectoCollection: IProjecto[] = [sampleWithRequiredData];
        expectedResult = service.addProjectoToCollectionIfMissing(projectoCollection, ...projectoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const projecto: IProjecto = sampleWithRequiredData;
        const projecto2: IProjecto = sampleWithPartialData;
        expectedResult = service.addProjectoToCollectionIfMissing([], projecto, projecto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(projecto);
        expect(expectedResult).toContain(projecto2);
      });

      it('should accept null and undefined values', () => {
        const projecto: IProjecto = sampleWithRequiredData;
        expectedResult = service.addProjectoToCollectionIfMissing([], null, projecto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(projecto);
      });

      it('should return initial array if no Projecto is added', () => {
        const projectoCollection: IProjecto[] = [sampleWithRequiredData];
        expectedResult = service.addProjectoToCollectionIfMissing(projectoCollection, undefined, null);
        expect(expectedResult).toEqual(projectoCollection);
      });
    });

    describe('compareProjecto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProjecto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProjecto(entity1, entity2);
        const compareResult2 = service.compareProjecto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProjecto(entity1, entity2);
        const compareResult2 = service.compareProjecto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProjecto(entity1, entity2);
        const compareResult2 = service.compareProjecto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
