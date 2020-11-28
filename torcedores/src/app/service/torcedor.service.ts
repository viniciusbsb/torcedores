import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Torcedor} from '../model/torcedor';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TorcedorService {

    constructor(private http: HttpClient) {
    }

    public findTorcedorByCpfOrNome(cpf: string, nome: string): Observable<Torcedor[]> {

        const params = new HttpParams()
            .set( 'cpf', cpf )
            .set( 'nome', nome );

        return this.http.get<Torcedor[]>(`${environment.url}/api/torcedores/search`, { params });
    }
}
