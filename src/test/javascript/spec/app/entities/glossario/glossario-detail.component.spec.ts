/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { KbaseTestModule } from '../../../test.module';
import { GlossarioDetailComponent } from 'app/entities/glossario/glossario-detail.component';
import { Glossario } from 'app/shared/model/glossario.model';

describe('Component Tests', () => {
    describe('Glossario Management Detail Component', () => {
        let comp: GlossarioDetailComponent;
        let fixture: ComponentFixture<GlossarioDetailComponent>;
        const route = ({ data: of({ glossario: new Glossario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [KbaseTestModule],
                declarations: [GlossarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GlossarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GlossarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.glossario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
