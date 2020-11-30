import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    user = '';
    password = '';

    @Output()
    loginEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor( private router: Router ) {
    }

    ngOnInit(): void {
    }

    public login(): void {

      const auth = btoa( `${this.user}:${this.password}` );
      localStorage.setItem( 'auth', auth );
      this.router.navigate( [ 'torcedor' ] );
      this.loginEvent.emit( true );
    }


}
