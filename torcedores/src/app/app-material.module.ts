import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatProgressBarModule} from '@angular/material/progress-bar';

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        MatToolbarModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatProgressBarModule,
    ],
    exports: [
        MatToolbarModule,
        MatCardModule,
        MatButtonModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatProgressBarModule,
    ],
    providers: [
        {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {floatLabel: 'always', appearance: 'outline'}}
    ]
})
export class AppMaterialModule {
}
