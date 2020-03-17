import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AuthResponse, User} from '../types/interfaces';
import {environment} from '../../environments/environment';
import {Observable, Subject, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    public error$ = new Subject();

    constructor(private  http: HttpClient) {
    }


    login(user: User): Observable<any> {
        return this.http.post(`${environment.url}/api/authenticate`, user).pipe(
            tap(this.setToken),
            catchError(this.handleError.bind(this))
        );
    }

    private handleError(errorObj: HttpErrorResponse) {
        console.log('handleError', errorObj);
        const status = errorObj.error.status;
        if (status) {
            switch (status) {
                case 401:
                    this.error$.next('401 неверный логин/пароль');
                    break;
                case 403:
                    this.error$.next('403 нет прав');
                    break;
                case 500:
                    this.error$.next('500 неизвестная ошибка сервера');
                    break;
                default:
                    this.error$.next(errorObj.error.status + ' ' + errorObj.error.message);
            }
        } else {
            this.error$.next(' неизвестная ошибка ');
        }
        return throwError(errorObj);
    }

    logout() {
        this.setToken(null);
    }

    get token(): string {
        const jwtToken = localStorage.getItem('jwtToken');
        const helper = new JwtHelperService();
        if (helper.isTokenExpired(jwtToken)) {
            this.logout();
            return null;
        }
        return localStorage.getItem('jwtToken');
    }

    private setToken(res: AuthResponse | null) {
        if (res) {
            const helper = new JwtHelperService();
            const decodedToken = helper.decodeToken(res.jwtToken);
            console.log('setToken', res);
            localStorage.setItem('jwtToken', res.jwtToken);
            localStorage.setItem('jwtToken-name', decodedToken.sub);
        } else {
            localStorage.clear();
        }
    }

    isAuthenticated(): boolean {
        return !!this.token;
    }

    public getUserName() {
        const name = localStorage.getItem('jwtToken-name');
        return name;
    }
}
