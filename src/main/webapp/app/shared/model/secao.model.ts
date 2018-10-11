import { Moment } from 'moment';

export const enum Modelo {
    F_A_Q = 'F_A_Q',
    ERRO = 'ERRO',
    ARTIGO = 'ARTIGO'
}

export interface ISecao {
    id?: number;
    nome?: string;
    modelo?: Modelo;
    editavel?: boolean;
    dataCriacao?: Moment;
}

export class Secao implements ISecao {
    constructor(public id?: number, public nome?: string, public modelo?: Modelo, public editavel?: boolean, public dataCriacao?: Moment) {
        this.editavel = false;
    }
}
