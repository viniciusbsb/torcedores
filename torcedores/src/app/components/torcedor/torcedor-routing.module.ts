import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ConsultarComponent} from './consultar/consultar.component';
import {FormularioComponent} from './formulario/formulario.component';

const routes: Routes = [
    {path: '', component: ConsultarComponent},
    {path: 'editar/:id', component: FormularioComponent},
    {path: 'incluir', component: FormularioComponent}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TorcedorRoutingModule {
}
