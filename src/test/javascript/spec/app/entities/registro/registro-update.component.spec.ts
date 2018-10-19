/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { RegistroUpdateComponent } from 'app/entities/registro/registro-update.component';
import { RegistroService } from 'app/entities/registro/registro.service';
import { Registro } from 'app/shared/model/registro.model';

describe('Component Tests', () => {
    describe('Registro Management Update Component', () => {
        let comp: RegistroUpdateComponent;
        let fixture: ComponentFixture<RegistroUpdateComponent>;
        let service: RegistroService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RegistroUpdateComponent]
            })
                .overrideTemplate(RegistroUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RegistroUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistroService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Registro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.registro = entity;
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
                    const entity = new Registro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.registro = entity;
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
