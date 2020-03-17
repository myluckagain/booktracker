import {Component, OnInit} from '@angular/core';
import {Genre} from '../types/interfaces';
import {GenreService} from '../services/genre.service';

@Component({
    selector: 'app-genres',
    templateUrl: './genres.component.html',
    styleUrls: ['./genres.component.scss']
})
export class GenresComponent implements OnInit {

    genres: Genre[];
    totalPages = 0;

    constructor(public genresService: GenreService) {
    }

    ngOnInit() {
        this.getGenres(0);
    }

    getGenres(curPage: number) {
        this.genresService.getAll(curPage).subscribe(res => {
            this.genres = res.content;
            this.totalPages = res.totalPages;
            console.log(res);
        });
    }

    loadPage(curPage: number) {
        this.getGenres(curPage);
    }
}
