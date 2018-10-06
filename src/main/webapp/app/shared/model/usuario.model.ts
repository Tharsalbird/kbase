export const enum TipoUsuario {
    ANALISTA = 'ANALISTA',
    GESTOR = 'GESTOR'
}

export interface IUsuario {
    id?: number;
    nome?: string;
    email?: string;
    login?: string;
    tipo?: TipoUsuario;
}

export class Usuario implements IUsuario {
    constructor(public id?: number, public nome?: string, public email?: string, public login?: string, public tipo?: TipoUsuario) {}
}
