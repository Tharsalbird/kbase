export interface IRotulo {
    id?: number;
    rotulo?: string;
}

export class Rotulo implements IRotulo {
    constructor(public id?: number, public rotulo?: string) {}
}
