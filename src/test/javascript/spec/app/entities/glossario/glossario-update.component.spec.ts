/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { GlossarioUpdateComponent } from 'app/entities/glossario/glossario-update.component';
import { GlossarioService } from 'app/entities/glossario/glossario.service';
import { Glossario } from 'app/shared/model/glossario.model';

describe('Component Tests', () => {
    describe('Glossario Management Update Component', () => {
        let comp: GlossarioUpdateComponent;
        let fixture: ComponentFixture<GlossarioUpdateComponent>;
        let service: GlossarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [GlossarioUpdateComponent]
            })
                .overrideTemplate(GlossarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GlossarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Glossario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.glossario = entity;
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
                    const entity = new Glossario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.glossario = entity;
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
