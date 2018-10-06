export interface IGlossario {
    id?: number;
    titulo?: string;
    descricao?: any;
}

export class Glossario implements IGlossario {
    constructor(public id?: number, public titulo?: string, public descricao?: any) {}
}
