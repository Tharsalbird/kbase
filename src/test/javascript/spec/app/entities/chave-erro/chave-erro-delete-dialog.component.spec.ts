/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KbaseTestModule } from '../../../test.module';
import { ChaveErroDeleteDialogComponent } from 'app/entities/chave-erro/chave-erro-delete-dialog.component';
import { ChaveErroService } from 'app/entities/chave-erro/chave-erro.service';

describe('Component Tests', () => {
    describe('ChaveErro Management Delete Component', () => {
        let comp: ChaveErroDeleteDialogComponent;
        let fixture: ComponentFixture<ChaveErroDeleteDialogComponent>;
        let service: ChaveErroService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [ChaveErroDeleteDialogComponent]
            })
                .overrideTemplate(ChaveErroDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChaveErroDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChaveErroService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
