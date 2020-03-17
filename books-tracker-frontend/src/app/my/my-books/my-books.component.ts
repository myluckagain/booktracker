import {Component, OnDestroy, OnInit} from '@angular/core';
import {Book} from '../../types/interfaces';
import {Subscription} from 'rxjs';
import {MySubscriptionsService} from '../../services/my-subscriptions.service';

@Component({
    selector: 'app-my-books',
    templateUrl: './my-books.component.html',
    styleUrls: ['./my-books.component.scss']
})
export class MyBooksComponent implements OnInit, OnDestroy {

    books: Book[];
    gSub: Subscription;
    totalPages = 0;
    searchStr = '';

    constructor(private  service: MySubscriptionsService,
    ) {
    }

    ngOnInit() {
        this.getBooks(0);
    }

    getBooks(page: number) {
        this.gSub = this.service.getMyBooks(page)
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

    }
}
