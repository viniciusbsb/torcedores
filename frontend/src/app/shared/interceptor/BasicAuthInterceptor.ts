import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

    constructor() {}

    public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const auth = localStorage.getItem( 'auth' );
        req = req.clone({
            setHeaders: {
                Authorization: `Basic ${auth}`
            }
        });

        return next.handle( req );
    }

}
