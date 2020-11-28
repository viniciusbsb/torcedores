import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {TorcedorService} from '../../../service/torcedor.service';
import {catchError, finalize, switchMap, tap} from 'rxjs/operators';
import {Torcedor} from '../../../model/torcedor';
import {EMPTY, Observable, of} from 'rxjs';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

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

    constructor( private fb: FormBuilder, private torcedorService: TorcedorService ) {

        this.dataSource = new MatTableDataSource<Torcedor>();
        this.formConsultar = this.fb.group( {
            cpf: [],
            nome: []
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
            catchError( this.handleError )
        ).subscribe();
    }

    private handleError( err: any ): Observable<any> {
        console.log( err );
        return EMPTY;
    }

    private popularTorcedores( torcedores: Torcedor[] ): void {
        this.dataSource.data = torcedores;
    }

    public limpar(): void {

        this.formConsultar?.reset();
        this.cpfElement?.nativeElement.focus();
    }

}
