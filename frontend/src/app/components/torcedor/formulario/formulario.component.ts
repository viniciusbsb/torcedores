import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TorcedorService} from '../../../service/torcedor.service';
import {EMPTY, Observable, of} from 'rxjs';
import {catchError, finalize, switchMap, tap} from 'rxjs/operators';
import {Torcedor} from '../../../model/torcedor';
import {TipoTelefone} from '../../../model/tipo-telefone';
import {EnderecoService} from '../../../service/endereco.service';
import {MatDialog} from '@angular/material/dialog';
import {CustomDialogComponent, DialogData} from '../../../shared/components/custom-dialog/custom-dialog.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {HttpErrorResponse} from '@angular/common/http';

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

    constructor(private fb: FormBuilder,
                private router: Router,
                private dialog: MatDialog,
                private snackBar: MatSnackBar,
                private activatedRoute: ActivatedRoute,
                private enderecoService: EnderecoService,
                private torcedorService: TorcedorService) {
        this.createForm();
        this.createFormTelefone();
    }

    ngOnInit(): void {
        this.load();
    }

    private load(): void {

        this.tipoTelefones = [TipoTelefone[TipoTelefone.CELULAR]
            , TipoTelefone[TipoTelefone.RESIDENCIAL]
            , TipoTelefone[TipoTelefone.COMERCIAL]];

        of({}).pipe(
            switchMap(() => this.activatedRoute.params),
            switchMap(params => this.torcedorService.findById(params.id)),
            tap((torcedor) => this.populateForm(torcedor))
        )
            .subscribe();
    }

    private populateForm(torcedor: Torcedor): void {

        this.isUpdate = true;
        this.telefones.controls.splice(0);
        torcedor.telefones?.map(telefone => this.telefones.push(this.createFormTelefone()));
        this.formCadastro.patchValue(torcedor);
    }

    private createForm(): void {

        this.formCadastro = this.fb.group({
            id: [],
            nome: ['', Validators.required],
            cpf: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            endereco: this.fb.group({
                id: [],
                cep: ['', Validators.required],
                numero: ['', Validators.required],
                logradouro: [{value: '', disabled: true}],
                complemento: ['', Validators.required],
                bairro: [{value: '', disabled: true}],
                localidade: [{value: '', disabled: true}],
                uf: [{value: '', disabled: true}]
            }),
            telefones: this.fb.array([this.createFormTelefone()])
        });
    }

    private createFormTelefone(): FormGroup {

        return this.fb.group({
            id: [],
            telefone: ['', Validators.required],
            tipoTelefone: ['', Validators.required],
            isPrincipal: []
        });
    }

    public getForm(form: AbstractControl): FormGroup {

        return form as FormGroup;
    }


    get telefones(): FormArray {

        return this.formCadastro.get('telefones') as FormArray;
    }

    public voltar(): void {

        this.router.navigate(['/torcedor']);
    }

    public deleteTelefone(index: number): void {

        this.telefones.controls.splice(index, 1);
    }

    public addTelefone(): void {

        this.telefones.controls.push(this.createFormTelefone());
    }

    public alterarCep(evt: any): void {
        const cep = this.formCadastro.controls.endereco.get('cep')?.value;

        of({}).pipe(
            switchMap(() => {
                this.blocked = true;
                return this.enderecoService.findEnderecoByCep(cep);
            }),
            tap((endereco) => this.formCadastro.controls.endereco.patchValue(endereco)),
            finalize(() => this.blocked = false),
            catchError(err => this.handleCepError(err, cep))
        ).subscribe();
    }

    private handleCepError(err: HttpErrorResponse, cep: string): Observable<any> {

        console.error( err );
        this.dialog.open(CustomDialogComponent, {
            width: '350px',
            data: {message: 'Endereço não localizado.', info: true} as DialogData
        });
        this.formCadastro.controls.endereco.reset();
        this.formCadastro.controls.endereco.get('cep')?.setValue(cep);
        return EMPTY;
    }

    public salvarTorcedor(): void {

        const telefonesInvalidos = this.telefones.controls.some(formTelefone => !formTelefone.valid);

        if (this.formCadastro.valid && !telefonesInvalidos) {
            let qtdPrincipal = 0;
            this.telefones.controls.forEach(p => {
                if (p.valid) {
                    const isPrincipal = !!p.get('isPrincipal')?.value;
                    p.get('isPrincipal')?.setValue( isPrincipal );

                    if ( isPrincipal ) {
                        ++qtdPrincipal;
                    }
                }
            });

            if ( qtdPrincipal > 1 ) {

                this.dialog.open(CustomDialogComponent, {
                    width: '400px',
                    data: {message: 'Somente pode ter um telefone principal.', info: true} as DialogData
                });
            } else {

                const torcedor = this.formCadastro.getRawValue() as Torcedor;

                of({}).pipe(
                    switchMap(() => {
                        this.blocked = true;
                        let observable = this.torcedorService.update(torcedor.id as number, torcedor);
                        if (!this.isUpdate) {
                            observable = this.torcedorService.save(torcedor);
                        }
                        return observable;
                    }),
                    tap((torcedorSalvo: Torcedor) => this.handleSucess(torcedorSalvo)),
                    finalize(() => this.blocked = false),
                    catchError(err => this.handleError(err))
                ).subscribe();
            }
        } else {

            this.dialog.open(CustomDialogComponent, {
                width: '300px',
                data: {message: 'Campos obrigatórios.', info: true} as DialogData
            });
        }
    }

    private handleError(err: any): Observable<any> {
        const HTTP_STATUS_CONFLICT = 409;
        const HTTP_STATUS_UNPROCESSABLE_ENTITY = 422;

        console.error(err);

        if (err.status === HTTP_STATUS_UNPROCESSABLE_ENTITY) {
            const msgs: any = [];
            Object.keys(err.error).forEach(field => {
                if (this.formCadastro.controls[field]) {
                    this.formCadastro.controls[field].setErrors({required: true});
                    msgs.push(err.error[field]);
                }
            });
            if (msgs.length > 0) {

                this.dialog.open(CustomDialogComponent, {
                    width: '450px',
                    data: {message: msgs.join(', '), info: true} as DialogData
                });
            }
        } else if (err.status === HTTP_STATUS_CONFLICT) {

            this.dialog.open(CustomDialogComponent, {
                width: '450px',
                data: {message: 'CPF já cadastrado.', info: true} as DialogData
            });
        } else {

            this.dialog.open(CustomDialogComponent, {
                width: '450px',
                data: {
                    message: 'Erro ao executar a operação! Favor entrar em contato com o Administrador do sistema.',
                    info: true
                } as DialogData
            });
        }
        return EMPTY;
    }

    private handleSucess(torcedor: Torcedor): void {
        let msg = `Torcedor ${torcedor.nome} incluído com sucesso!`;
        if (this.isUpdate) {
            msg = `Torcedor ${torcedor.nome} alterado com sucesso!`;
        } else {
            this.voltar();
        }

        this.snackBar.open(msg);
    }

}
