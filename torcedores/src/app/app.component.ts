import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    title = 'torcedores';
    isLogged = false;

    constructor() {
    }

    public ngOnInit(): void {

        this.logged( false );
    }

    public logged( evt: any ): void {
        this.isLogged = !!localStorage.getItem( 'auth' );
    }

}
