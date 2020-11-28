import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TorcedorRoutingModule} from './torcedor-routing.module';
import {ConsultarComponent} from './consultar/consultar.component';
import {FormularioComponent} from './formulario/formulario.component';
import {AppMaterialModule} from '../../app-material.module';
import {ComumModule} from '../../shared/common/comum.module';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
    declarations: [ConsultarComponent, FormularioComponent],
    imports: [
        CommonModule,
        TorcedorRoutingModule,
        AppMaterialModule,
        ComumModule,
        ReactiveFormsModule
    ]
})
export class TorcedorModule {
}
