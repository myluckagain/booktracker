import {Component, OnInit} from '@angular/core';
import {Author} from '../types/interfaces';
import {AuthorService} from '../services/author.service';

@Component({
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})
export class AuthorsComponent implements OnInit {
    authors: Author[];
    totalPages = 0;

    constructor(private authorsService: AuthorService) {
    }

    ngOnInit() {
        this.getAuthors(0);
    }

    getAuthors(curPage: number) {
        this.authorsService.getAll(curPage).subscribe(res => {
            this.authors = res.content;
            this.totalPages = res.totalPages;
            console.log(res);
        });
    }

    loadPage(curPage: number) {
        this.getAuthors(curPage);
    }
}
