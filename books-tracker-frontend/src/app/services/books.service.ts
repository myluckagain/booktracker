import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Book, Page} from '../types/interfaces';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class BooksService {
    constructor(private http: HttpClient) {
    }


    getAll(page: number): Observable<Page<Book[]>> {
        return this.http.get<Page<Book[]>>(`${environment.url}/api/books?page=${page}`);
    }

    remove(id: string): Observable<void> {
        return this.http.delete<void>(`${environment.url}/api/book/${id}`);
    }

    get(id: string): Observable<Book> {
        return this.http.get<Book>(`${environment.url}/api/book/${id}`);
    }

    create(book: Book): Observable<Book> {
        return this.http.post<Book>(`${environment.url}/api/book`, book);
    }

    update(book: Book): Observable<Book> {
        return this.http.put<Book>(`${environment.url}/api/book/${book.id}`, book);
    }
}

