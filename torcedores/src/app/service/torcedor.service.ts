import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {EMPTY, Observable} from 'rxjs';
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

    public findById( id: number ): Observable<Torcedor> {

        if ( id ) {

            return this.http.get<Torcedor>(`${environment.url}/api/torcedores/${id}`);
        }else{

            return EMPTY;
        }
    }

    public save( torcedor: Torcedor ): Observable<Torcedor> {

        return this.http.post<Torcedor>(`${environment.url}/api/torcedores/`, torcedor );
    }

    public update( id: number, torcedor: Torcedor ): Observable<Torcedor> {

        return this.http.put<Torcedor>(`${environment.url}/api/torcedores/${id}`, torcedor );
    }

    public delete( id: number ): Observable<any> {

        return this.http.delete<any>(`${environment.url}/api/torcedores/${id}` );
    }

}
