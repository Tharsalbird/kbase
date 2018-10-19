/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { ChaveErroUpdateComponent } from 'app/entities/chave-erro/chave-erro-update.component';
import { ChaveErroService } from 'app/entities/chave-erro/chave-erro.service';
import { ChaveErro } from 'app/shared/model/chave-erro.model';

describe('Component Tests', () => {
    describe('ChaveErro Management Update Component', () => {
        let comp: ChaveErroUpdateComponent;
        let fixture: ComponentFixture<ChaveErroUpdateComponent>;
        let service: ChaveErroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [ChaveErroUpdateComponent]
            })
                .overrideTemplate(ChaveErroUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChaveErroUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChaveErroService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ChaveErro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chaveErro = entity;
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
                    const entity = new ChaveErro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chaveErro = entity;
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
