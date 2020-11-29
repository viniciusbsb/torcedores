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
import {MatDividerModule} from '@angular/material/divider';
import {RouteReuseStrategy} from '@angular/router';
import {CacheRouteReuseStrategy} from './service/cache-route-reuse.strategy';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSelectModule} from '@angular/material/select';

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
        MatDividerModule,
        MatTabsModule,
        MatCheckboxModule,
        MatSelectModule,
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
        MatDividerModule,
        MatTabsModule,
        MatCheckboxModule,
        MatSelectModule,
    ],
    providers: [
        { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {floatLabel: 'always', appearance: 'outline'}},
        { provide: RouteReuseStrategy, useClass: CacheRouteReuseStrategy },
    ]
})
export class AppMaterialModule {
}
