/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { SecaoUpdateComponent } from 'app/entities/secao/secao-update.component';
import { SecaoService } from 'app/entities/secao/secao.service';
import { Secao } from 'app/shared/model/secao.model';

describe('Component Tests', () => {
    describe('Secao Management Update Component', () => {
        let comp: SecaoUpdateComponent;
        let fixture: ComponentFixture<SecaoUpdateComponent>;
        let service: SecaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [SecaoUpdateComponent]
            })
                .overrideTemplate(SecaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SecaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SecaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Secao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.secao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Secao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.secao = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
