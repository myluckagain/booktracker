import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../types/interfaces';
import {environment} from '../../environments/environment';

@Injectable({providedIn: 'root'})
export class UserService {
    constructor(private http: HttpClient) {
    }

    getAll(): Observable<User[]> {
        return this.http.get<User[]>(`${environment.url}/api/users`);
    }

    remove(id: string): Observable<void> {
        return this.http.delete<void>(`${environment.url}/api/users/${id}`);
    }
}
