/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { ChaveErroDetailComponent } from 'app/entities/chave-erro/chave-erro-detail.component';
import { ChaveErro } from 'app/shared/model/chave-erro.model';

describe('Component Tests', () => {
    describe('ChaveErro Management Detail Component', () => {
        let comp: ChaveErroDetailComponent;
        let fixture: ComponentFixture<ChaveErroDetailComponent>;
        const route = ({ data: of({ chaveErro: new ChaveErro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [ChaveErroDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChaveErroDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChaveErroDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chaveErro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
