import {TipoTelefone} from './tipo-telefone';

export interface Telefone {

    id?: number;
    telefone?: string;
    tipoTelefone?: TipoTelefone;
    isPrincipal?: boolean;
}
