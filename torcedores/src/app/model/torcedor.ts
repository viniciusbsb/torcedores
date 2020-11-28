import {Endereco} from './endereco';
import {Telefone} from './telefone';

export interface Torcedor {

    id?: number;
    nome?: string;
    cpf?: string;
    endereco?: Endereco;
    telefones?: Telefone[];

}
