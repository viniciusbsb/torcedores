import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppMaterialModule} from './app-material.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {BasicAuthInterceptor} from './shared/interceptor/BasicAuthInterceptor';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        AppMaterialModule,
        HttpClientModule
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
