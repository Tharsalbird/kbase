/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KbaseTestModule } from '../../../test.module';
import { RotuloDeleteDialogComponent } from 'app/entities/rotulo/rotulo-delete-dialog.component';
import { RotuloService } from 'app/entities/rotulo/rotulo.service';

describe('Component Tests', () => {
    describe('Rotulo Management Delete Component', () => {
        let comp: RotuloDeleteDialogComponent;
        let fixture: ComponentFixture<RotuloDeleteDialogComponent>;
        let service: RotuloService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RotuloDeleteDialogComponent]
            })
                .overrideTemplate(RotuloDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RotuloDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RotuloService);
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
