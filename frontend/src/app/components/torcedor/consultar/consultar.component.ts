import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {TorcedorService} from '../../../service/torcedor.service';
import {catchError, finalize, switchMap, tap} from 'rxjs/operators';
import {Torcedor} from '../../../model/torcedor';
import {EMPTY, Observable, of} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {CustomDialogComponent, DialogData} from '../../../shared/components/custom-dialog/custom-dialog.component';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
    selector: 'app-consultar',
    templateUrl: './consultar.component.html',
    styleUrls: ['./consultar.component.scss']
})
export class ConsultarComponent implements OnInit, AfterViewInit {

    blocked = false;
    displayedColumns: string[] = ['id', 'nome', 'cpf', 'acao'];
    formConsultar: FormGroup;
    dataSource: MatTableDataSource<Torcedor>;
    @ViewChild('cpf') cpfElement!: ElementRef;
    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    constructor( private fb: FormBuilder,
                 private torcedorService: TorcedorService,
                 private snackBar: MatSnackBar,
                 private dialog: MatDialog,
                 private router: Router) {

        this.dataSource = new MatTableDataSource<Torcedor>();
        this.formConsultar = this.fb.group( {
            cpf: [''],
            nome: ['']
        } );
    }

    ngOnInit(): void {
    }

    public ngAfterViewInit(): void {

        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    public consultar(): void {

        const { cpf, nome } = this.formConsultar?.getRawValue();
        of({}).pipe(
            switchMap( () => {
                this.blocked = true;
                return this.torcedorService.findTorcedorByCpfOrNome( cpf, nome );
            } ),
            tap( (torcedores) => this.popularTorcedores( torcedores ) ),
            finalize( () => this.blocked = false ),
            catchError( err => this.handleError( err ) )
        ).subscribe();
    }

    private handleError( err: any ): Observable<any> {

        this.dialog.open( CustomDialogComponent, {
            width: '300px',
            data: { message: 'Falha ao consultar os torcedores.', info: true } as DialogData
        } );

        console.error( err );
        return EMPTY;
    }

    private popularTorcedores( torcedores: Torcedor[] ): void {
        this.dataSource.data = torcedores;
    }

    public limpar(): void {

        this.formConsultar?.reset();
        this.cpfElement?.nativeElement.focus();
    }

    public editar( torcedor: Torcedor ): void {

        this.router.navigate( [ 'torcedor', 'editar', torcedor.id  ] );
    }

    public incluir(): void {

        this.router.navigate( [ 'torcedor', 'incluir' ] );
    }

    public delete( id: number ): void {

        const dialogRef = this.dialog.open( CustomDialogComponent, {
            width: '300px',
            data: { message: 'Deseja excluir o torcedor?' } as DialogData
        } );

        dialogRef.afterClosed().subscribe( (data: DialogData) => {
            if ( data.result ) {
                of({}).pipe(
                    switchMap( () => {

                        this.blocked = true;
                        return this.torcedorService.delete( id );
                    } ),
                    tap( () => this.handleDelete() ),
                    finalize( () => this.blocked = false ),
                    catchError( err => this.handleError( err ) )
                ).subscribe();
            }
        } );

    }

    private handleDelete(): void {

        this.snackBar.open( 'Torcedor excluído com sucesso!' );
        this.consultar();
    }

}
