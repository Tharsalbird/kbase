export interface IChaveErro {
    id?: number;
    titulo?: string;
    descricao?: any;
}

export class ChaveErro implements IChaveErro {
    constructor(public id?: number, public titulo?: string, public descricao?: any) {}
}
