import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book, Page, Visit} from '../types/interfaces';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class VisitService {
    constructor(private http: HttpClient) {
    }

    getAll(page: number): Observable<Page<Visit[]>> {
        return this.http.get<Page<Visit[]>>(`${environment.url}/api/visits?page=${page}`);
    }

    getBooksForVisit(id, page: number): Observable<Page<Book[]>> {
        return this.http.get<Page<Book[]>>(`${environment.url}/api/visit/${id}/books?page=${page}`);
    }

}
