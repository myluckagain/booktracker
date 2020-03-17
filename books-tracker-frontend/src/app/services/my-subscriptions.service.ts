import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Book, MyProfileDto, Page} from '../types/interfaces';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {catchError, map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class MySubscriptionsService {
    constructor(private http: HttpClient) {
    }

    getMyBookNames(): Observable<string[]> {
        return this.http.get<string[]>(`${environment.url}/api/profile/booknames`);

    }

    updateMyBookNames(bookNames: string[]): Observable<string[]> {
        return this.http.put<string[]>(`${environment.url}/api/profile/booknames`, bookNames);
    }

    getMyAuthors(): Observable<string[]> {
        return this.http.get<string[]>(`${environment.url}/api/profile/authors`);

    }

    updateMyAuthors(authors: string[]): Observable<string[]> {
        return this.http.put<string[]>(`${environment.url}/api/profile/authors`, authors);
    }

    getMyGenres(): Observable<string[]> {
        return this.http.get<string[]>(`${environment.url}/api/profile/genres`);

    }

    updateMyGenres(genres: string[]): Observable<string[]> {
        return this.http.put<string[]>(`${environment.url}/api/profile/genres`, genres);
    }


    getMyBooks(page: number): Observable<Page<Book[]>> {
        return this.http.get<Page<Book[]>>(`${environment.url}/api/profile/books?page=${page}`);
    }

    getMyProfile(): Observable<MyProfileDto> {
        return this.http.get<MyProfileDto>(`${environment.url}/api/profile`);
    }

    updateMyProfile(profile: MyProfileDto): Observable<MyProfileDto> {
        return this.http.put<MyProfileDto>(`${environment.url}/api/profile/edit`, profile);
    }

    postFile(fileToUpload: File): Observable<boolean> {
        const endpoint = 'your-destination-url';
        const formData: FormData = new FormData();
        formData.append('fileKey', fileToUpload, fileToUpload.name);

        return this.http
            .post(endpoint, formData).pipe(
                map(() => {
                    return true;
                })
                ,
                catchError(this.handleError.bind(this)))
            ;
    }

    private handleError(error: HttpErrorResponse) {
        console.log('handleError', error);
    }
}
