/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { RotuloDetailComponent } from 'app/entities/rotulo/rotulo-detail.component';
import { Rotulo } from 'app/shared/model/rotulo.model';

describe('Component Tests', () => {
    describe('Rotulo Management Detail Component', () => {
        let comp: RotuloDetailComponent;
        let fixture: ComponentFixture<RotuloDetailComponent>;
        const route = ({ data: of({ rotulo: new Rotulo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [RotuloDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RotuloDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RotuloDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rotulo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
