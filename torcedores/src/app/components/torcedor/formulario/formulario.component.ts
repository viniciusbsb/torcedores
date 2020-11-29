import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {TorcedorService} from '../../../service/torcedor.service';
import {of} from 'rxjs';
import {switchMap, tap} from 'rxjs/operators';
import {Torcedor} from '../../../model/torcedor';
import {TipoTelefone} from '../../../model/tipo-telefone';

@Component({
    selector: 'app-formulario',
    templateUrl: './formulario.component.html',
    styleUrls: ['./formulario.component.scss']
})
export class FormularioComponent implements OnInit {

    blocked = false;
    formCadastro!: FormGroup;
    formTelefone!: FormGroup;
    tipoTelefones: string[] = [];

    constructor( private fb: FormBuilder
               , private activatedRoute: ActivatedRoute
               , private torcedorService: TorcedorService ) {
        this.createForm();
        this.createFormTelefone();
    }

    ngOnInit(): void {
        this.load();
    }

    private load(): void {

        this.tipoTelefones = Object.keys( TipoTelefone ).map( (key: any) => TipoTelefone[key] );

        of({}).pipe(
            switchMap( () => this.activatedRoute.params ),
            switchMap( params => this.torcedorService.findById( params.id ) ),
            tap( (torcedor) => this.populateForm( torcedor ) )
        )
        .subscribe();
    }

    private populateForm( torcedor: Torcedor ): void {

        this.formCadastro.patchValue( torcedor );
    }

    private createForm(): void {

        this.formCadastro = this.fb.group( {
            id: [],
            nome: [],
            cpf: [],
            endereco: this.fb.group( {
                id: [],
                cep: [],
                numero: [],
                logradouro: [],
                complemento: [],
                bairro: [],
                localidade: [],
                uf: []
            } ),
            telefones: this.fb.array([ this.createFormTelefone() ] )
        } );
    }

    private createFormTelefone(): FormGroup {

        return this.fb.group({
            id: [],
            telefone: [],
            tipoTelefone: [],
            isPrincipal: []
        });
    }

    public getForm(form: AbstractControl): FormGroup {

        return form as FormGroup;
    }


    get telefones(): FormArray {

        return this.formCadastro.get( 'telefones' ) as FormArray;
    }

}
