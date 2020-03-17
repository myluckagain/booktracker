import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Book} from '../types/interfaces';
import {BooksService} from '../services/books.service';

@Component({
    selector: 'app-books',
    templateUrl: './books.component.html',
    styleUrls: ['./books.component.scss']
})
export class BooksComponent implements OnInit, OnDestroy {

    books: Book[];
    gSub: Subscription;
    dSub: Subscription;
    totalPages = 0;
    searchStr = '';

    constructor(private service: BooksService) {
    }

    ngOnInit() {
        this.getBooks(0);
    }

    getBooks(page: number) {
        this.gSub = this.service.getAll(page)
            .subscribe((res) => {
                console.log(res);
                this.books = res.content;
                this.totalPages = res.totalPages;
            });
    }

    loadPage(curPage: number) {
        this.getBooks(curPage);
    }

    ngOnDestroy() {
        if (this.gSub) {
            this.gSub.unsubscribe();
        }
        if (this.dSub) {
            this.dSub.unsubscribe();
        }
    }
}
