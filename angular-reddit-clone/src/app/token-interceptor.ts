import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { AuthService } from './service/auth.service';
import { LoginResponse } from './model/login-response';

@Injectable({
    providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor(public authService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const jwt = this.authService.getJwtToken();

        try {
            const payload = JSON.parse(atob(jwt.split('.')[1]));
            const expiration = new Date(payload.exp * 1000)
            console.log(payload);
            
        } catch (error) {
            console.log((error));
        }
        
        

        if (jwt) {
            return next.handle(this.addTokenToRequest(req, jwt)).pipe(
                catchError(error => {
                    if (error instanceof HttpErrorResponse && error.status === 403) {
                        return this.handleAuthErrors(req, next);
                    }
                    return throwError(error);
                })
            );
        }

        return next.handle(req);

    }

    private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler) {

        if (!this.isTokenRefreshing) {
            this.isTokenRefreshing = true;
            this.refreshTokenSubject.next(null);
            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: LoginResponse) => {
                    this.isTokenRefreshing = false;
                    this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);
                    return next.handle(this.addTokenToRequest(req, refreshTokenResponse.authenticationToken));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
                filter(result => result !== null),
                take(1),
                switchMap((res) => {
                    return next.handle(this.addTokenToRequest(req, this.authService.getJwtToken()))
                })
            );
        }

    }

    private addTokenToRequest(req: HttpRequest<any>, jwt: string): HttpRequest<any> {
        return req.clone({ headers: req.headers.set('Authorization',`Bearer ${jwt}`) });
    }

}