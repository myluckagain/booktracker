import {Component, OnDestroy, OnInit} from '@angular/core';
import {Book} from '../types/interfaces';
import {Subscription} from 'rxjs';
import {VisitService} from '../services/visit.service';
import {switchMap} from 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-books-for-visit',
    templateUrl: './books-for-visit.component.html',
    styleUrls: ['./books-for-visit.component.scss']
})
export class BooksForVisitComponent implements OnInit, OnDestroy {

    books: Book[];
    gSub: Subscription;
    dSub: Subscription;
    totalPages = 0;
    searchStr = '';
    visitId: string;

    constructor(private service: VisitService, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.queryParams.pipe(
            switchMap(params => {
                    console.log(params);
                    if (params.visitId) {
                        this.visitId = params.visitId;
                        return this.service.getBooksForVisit(params.visitId, 0);
                    }
                }
            )
        ).subscribe((res) => {
            this.books = res.content;
        });
    }

    getBooks(page: number) {
        this.gSub = this.service.getBooksForVisit(this.visitId, page)
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
