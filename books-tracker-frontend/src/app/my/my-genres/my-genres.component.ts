import {Component, OnInit} from '@angular/core';
import {MySubscriptionsService} from '../../services/my-subscriptions.service';

@Component({
    selector: 'app-my-genres',
    templateUrl: './my-genres.component.html',
    styleUrls: ['./my-genres.component.scss']
})
export class MyGenresComponent implements OnInit {

    genres: string[];
    name = '';

    constructor(private subscriptionsService: MySubscriptionsService) {
    }

    ngOnInit() {
        this.subscriptionsService.getMyGenres().subscribe(res => {
            this.genres = res;
        });

    }

    create() {
        if (!this.name.trim()) {
            return;
        }
        if (this.genres.includes(this.name.trim())) {
            return;
        }
        const genres: string[] = this.genres;
        genres.push(this.name.trim());
        this.updateMyGenres(genres);
    }

    updateMyGenres(genres: string[]) {
        this.subscriptionsService.updateMyGenres(genres).subscribe(
            // tslint:disable-next-line:no-shadowed-variable
            res => {
                this.name = '';
                this.genres = res;
            }
        );
    }

    remove(genre: string) {
        const genres: string[] = this.genres.filter(g => g !== genre);
        this.subscriptionsService.updateMyGenres(genres).subscribe(
            res => {
                this.genres = res;
            });
    }

}
