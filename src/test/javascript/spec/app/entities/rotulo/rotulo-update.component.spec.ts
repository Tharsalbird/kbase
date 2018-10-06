/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { RotuloUpdateComponent } from 'app/entities/rotulo/rotulo-update.component';
import { RotuloService } from 'app/entities/rotulo/rotulo.service';
import { Rotulo } from 'app/shared/model/rotulo.model';

describe('Component Tests', () => {
    describe('Rotulo Management Update Component', () => {
        let comp: RotuloUpdateComponent;
        let fixture: ComponentFixture<RotuloUpdateComponent>;
        let service: RotuloService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RotuloUpdateComponent]
            })
                .overrideTemplate(RotuloUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RotuloUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RotuloService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Rotulo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rotulo = entity;
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
                    const entity = new Rotulo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.rotulo = entity;
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
