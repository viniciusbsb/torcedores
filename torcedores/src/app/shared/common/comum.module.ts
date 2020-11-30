import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {NgxMaskModule} from 'ngx-mask';
import {CustomDialogComponent} from '../components/custom-dialog/custom-dialog.component';
import {AppMaterialModule} from '../../app-material.module';


@NgModule({
    declarations: [ CustomDialogComponent ],
    imports: [
        CommonModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgxMaskModule.forRoot(),
        AppMaterialModule
    ],
    exports: [
        NgxMaskModule
    ]
})
export class ComumModule {
}
