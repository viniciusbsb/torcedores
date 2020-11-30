import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TorcedorService} from '../../../service/torcedor.service';
import {EMPTY, Observable, of} from 'rxjs';
import {catchError, finalize, switchMap, tap} from 'rxjs/operators';
import {Torcedor} from '../../../model/torcedor';
import {TipoTelefone} from '../../../model/tipo-telefone';
import {EnderecoService} from '../../../service/endereco.service';

@Component({
    selector: 'app-formulario',
    templateUrl: './formulario.component.html',
    styleUrls: ['./formulario.component.scss']
})
export class FormularioComponent implements OnInit {

    blocked = false;
    formCadastro!: FormGroup;
    tipoTelefones: string[] = [];
    isUpdate = false;

    constructor( private fb: FormBuilder
               , private router: Router
               , private activatedRoute: ActivatedRoute
               , private enderecoService: EnderecoService
               , private torcedorService: TorcedorService ) {
        this.createForm();
        this.createFormTelefone();
    }

    ngOnInit(): void {
        this.load();
    }

    private load(): void {

        this.tipoTelefones = [ TipoTelefone[ TipoTelefone.CELULAR ]
            , TipoTelefone[ TipoTelefone.RESIDENCIAL ]
            , TipoTelefone[ TipoTelefone.COMERCIAL ] ];

        of({}).pipe(
            switchMap( () => this.activatedRoute.params ),
            switchMap( params => this.torcedorService.findById( params.id ) ),
            tap( (torcedor) => this.populateForm( torcedor ) )
        )
        .subscribe();
    }

    private populateForm( torcedor: Torcedor ): void {

        this.isUpdate = true;
        this.telefones.controls.splice( 0 );
        torcedor.telefones?.map( telefone => this.telefones.push( this.createFormTelefone() ) );
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
                logradouro: [ { value: '', disabled: true } ],
                complemento: [],
                bairro: [ { value: '', disabled: true } ],
                localidade: [ { value: '', disabled: true } ],
                uf: [ { value: '',  disabled: true } ]
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

    public voltar(): void {

        this.router.navigate( [ '/torcedor' ] );
    }

    public deleteTelefone( index: number ): void {

        this.telefones.controls.splice( index, 1 );
    }

    public addTelefone(): void {

        this.telefones.controls.push( this.createFormTelefone() );
    }

    public alterarCep( evt: any ): void {

        const cep = this.formCadastro.controls.endereco.get( 'cep' )?.value;

        if ( cep.length > 7 ) {
            of({}).pipe(
                switchMap( () => {
                    this.blocked = true;
                    return this.enderecoService.findEnderecoByCep(cep);
                } ),
                tap( (endereco) => this.formCadastro.controls.endereco.patchValue(endereco) ),
                finalize( () => this.blocked = false ),
                catchError( err => {
                    this.formCadastro.controls.endereco.reset();
                    this.formCadastro.controls.endereco.get('cep')?.setValue( cep );
                    return EMPTY;
                } )
            ).subscribe();
        }
    }

    public salvarTorcedor(): void {

        if ( this.formCadastro.valid ) {
            this.telefones.controls.forEach( p => {
                if ( p.valid ) {
                    p.get( 'isPrincipal' )?.setValue( !!p.get( 'isPrincipal' )?.value );
                }
            } );

            const telefonesInvalidos = this.telefones.controls.some( formTelefone => !formTelefone.valid );

            if ( !telefonesInvalidos ) {
                const torcedor = this.formCadastro.getRawValue() as Torcedor;

                of({}).pipe(
                    switchMap( () => {
                        this.blocked = true;
                        let observable = this.torcedorService.update( torcedor.id as number, torcedor );
                        if ( !this.isUpdate ) {
                            observable = this.torcedorService.save( torcedor );
                        }
                        return observable;
                    } ),
                    tap( (torcedorSalvo: Torcedor) => this.handleSucess( torcedorSalvo ) ),
                    finalize( () => this.blocked = false ),
                    catchError( err => this.handleError( err ) )
                ).subscribe();

            }else{

            }
        }else{

            console.log( 'Formulario invalido' );
        }

    }

    private handleError( err: any ): Observable<any> {

        console.error( err );
        return EMPTY;
    }

    private handleSucess( torcedor: Torcedor ): void {

        console.log( torcedor );
    }

}
