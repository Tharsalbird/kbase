/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { KbaseTestModule } from '../../../test.module';
import { SecaoDeleteDialogComponent } from 'app/entities/secao/secao-delete-dialog.component';
import { SecaoService } from 'app/entities/secao/secao.service';

describe('Component Tests', () => {
    describe('Secao Management Delete Component', () => {
        let comp: SecaoDeleteDialogComponent;
        let fixture: ComponentFixture<SecaoDeleteDialogComponent>;
        let service: SecaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [SecaoDeleteDialogComponent]
            })
                .overrideTemplate(SecaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SecaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SecaoService);
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
