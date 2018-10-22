import { IRotulo } from 'app/shared/model//rotulo.model';

export interface IRegistro {
    id?: number;
    titulo?: string;
    texto?: any;
    secaoNome?: string;
    secaoId?: number;
    publico?: boolean;
    rotulos?: IRotulo[];
}

export class Registro implements IRegistro {
    constructor(
        public id?: number,
        public titulo?: string,
        public texto?: any,
        public secaoNome?: string,
        public secaoId?: number,
        public publico?: boolean,
        public rotulos?: IRotulo[]
    ) {}
}
