import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';

@Component({
    selector: 'app-paginator',
    templateUrl: './paginator.component.html',
    styleUrls: ['./paginator.component.scss']
})
export class PaginatorComponent implements OnChanges {

    constructor() {
    }

    @Input() totalPages = 0;
    @Output() onPageChange: EventEmitter<number> = new EventEmitter();
    pages: Array<number>;
    curPage = 0;


    ngOnChanges() {
        this.curPage = 0;
        this.pages = new Array<number>(this.totalPages);
        this.onPageChange.emit(0);
    }

    onClickPage(pageNumber: number, event: any) {
        event.preventDefault();
        this.curPage = pageNumber;
        this.onPageChange.emit(this.curPage);
    }
}
