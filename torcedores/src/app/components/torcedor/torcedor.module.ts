import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TorcedorRoutingModule } from './torcedor-routing.module';
import { ConsultarComponent } from './consultar/consultar.component';
import { FormularioComponent } from './formulario/formulario.component';


@NgModule({
  declarations: [ConsultarComponent, FormularioComponent],
  imports: [
    CommonModule,
    TorcedorRoutingModule
  ]
})
export class TorcedorModule { }
