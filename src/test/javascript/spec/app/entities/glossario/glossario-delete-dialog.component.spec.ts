/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KbaseTestModule } from '../../../test.module';
import { GlossarioDeleteDialogComponent } from 'app/entities/glossario/glossario-delete-dialog.component';
import { GlossarioService } from 'app/entities/glossario/glossario.service';

describe('Component Tests', () => {
    describe('Glossario Management Delete Component', () => {
        let comp: GlossarioDeleteDialogComponent;
        let fixture: ComponentFixture<GlossarioDeleteDialogComponent>;
        let service: GlossarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [GlossarioDeleteDialogComponent]
            })
                .overrideTemplate(GlossarioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GlossarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GlossarioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
