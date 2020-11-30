import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Endereco} from '../model/endereco';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {


  constructor(private http: HttpClient) {
  }

  public findEnderecoByCep( cep: string ): Observable<Endereco> {

    return this.http.get<Endereco>(`${environment.url}/api/enderecos/integracao/${cep}` );
  }

  public findById( id: number ): Observable<Endereco> {

    return this.http.get<Endereco>(`${environment.url}/api/enderecos/${id}` );
  }

  public save( endereco: Endereco ): Observable<Endereco> {

    return this.http.post<Endereco>(`${environment.url}/api/enderecos/`, endereco );
  }

  public update( id: number, endereco: Endereco ): Observable<Endereco> {

    return this.http.put<Endereco>(`${environment.url}/api/enderecos/${id}`, endereco );
  }

}
