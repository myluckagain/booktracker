import {Injectable} from '@angular/core';
import {Observable, Subject, throwError} from 'rxjs';
import {Genre, Page} from '../types/interfaces';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class GenreService {
    constructor(private http: HttpClient) {
    }

    public error$ = new Subject();

    getAll(page: number): Observable<Page<Genre[]>> {
        return this.http.get<Page<Genre[]>>(`${environment.url}/api/genres?page=${page}`);

    }

    remove(id: string): Observable<void> {
        return this.http.delete<void>(`${environment.url}/api/genres/${id}`).pipe(
            catchError(this.handleError.bind(this))
        );
    }

    private handleError(errorObj: HttpErrorResponse) {
        console.log('handleError', errorObj);
        const message = errorObj.error.message;
        if (message) {
            this.error$.next(errorObj.error.message);
        }
        return throwError(errorObj);
    }


    get(id: string): Observable<Genre> {
        return this.http.get<Genre>(`${environment.url}/api/genres/${id}`);
    }

    create(genre: Genre): Observable<Genre> {
        return this.http.post<Genre>(`${environment.url}/api/genres`, genre).pipe(
            catchError(this.handleError.bind(this))
        );
    }

    update(genre: Genre): Observable<Genre> {
        return this.http.put<Genre>(`${environment.url}/api/genres/${genre.id}`, genre).pipe(
            catchError(this.handleError.bind(this))
        );
    }
}
