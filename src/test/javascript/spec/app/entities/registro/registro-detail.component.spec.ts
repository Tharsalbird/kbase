/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { RegistroDetailComponent } from 'app/entities/registro/registro-detail.component';
import { Registro } from 'app/shared/model/registro.model';

describe('Component Tests', () => {
    describe('Registro Management Detail Component', () => {
        let comp: RegistroDetailComponent;
        let fixture: ComponentFixture<RegistroDetailComponent>;
        const route = ({ data: of({ registro: new Registro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RegistroDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RegistroDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RegistroDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.registro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
