import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    title = 'torcedores';

    constructor(private activatedRoute: ActivatedRoute, private  router: Router) {
    }

    public ngOnInit(): void {

        this.router.navigate(['torcedor']);
    }

}
