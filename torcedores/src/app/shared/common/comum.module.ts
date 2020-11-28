import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {NgxMaskModule} from 'ngx-mask';


@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgxMaskModule.forRoot()
    ],
    exports: [
        NgxMaskModule
    ]
})
export class ComumModule {
}
