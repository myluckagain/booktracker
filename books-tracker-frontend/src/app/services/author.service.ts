import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Author, Page} from '../types/interfaces';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class AuthorService {
    constructor(private http: HttpClient) {
    }

    getAll(page: number): Observable<Page<Author[]>> {
        return this.http.get<Page<Author[]>>(`${environment.url}/api/authors?page=${page}`);

    }

    remove(id: string): Observable<void> {
        return this.http.delete<void>(`${environment.url}/api/authors/${id}`);
    }

    get(id: string): Observable<Author> {
        return this.http.get<Author>(`${environment.url}/api/authors/${id}`);
    }

    create(author: Author): Observable<Author> {
        return this.http.post<Author>(`${environment.url}/api/authors`, author);
    }

    update(author: Author): Observable<Author> {
        return this.http.put<Author>(`${environment.url}/api/authors/${author.id}`, author);
    }
}

