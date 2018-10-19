/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KbaseTestModule } from '../../../test.module';
import { RegistroDeleteDialogComponent } from 'app/entities/registro/registro-delete-dialog.component';
import { RegistroService } from 'app/entities/registro/registro.service';

describe('Component Tests', () => {
    describe('Registro Management Delete Component', () => {
        let comp: RegistroDeleteDialogComponent;
        let fixture: ComponentFixture<RegistroDeleteDialogComponent>;
        let service: RegistroService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RegistroDeleteDialogComponent]
            })
                .overrideTemplate(RegistroDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegistroDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegistroService);
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
