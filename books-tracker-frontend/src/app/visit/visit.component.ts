import {Component, OnInit} from '@angular/core';
import {Visit} from '../types/interfaces';
import {VisitService} from '../services/visit.service';

@Component({
    selector: 'app-visit',
    templateUrl: './visit.component.html',
    styleUrls: ['./visit.component.scss']
})
export class VisitComponent implements OnInit {
    curPage = 0;
    pages: Array<number>;
    visits: Visit[];


    constructor(private visitService: VisitService) {
    }

    ngOnInit() {
        this.getVisits(0);

    }

    getVisits(curPage: number) {
        this.visitService.getAll(this.curPage).subscribe(res => {
            this.visits = res.content;

            this.pages = new Array(res.totalPages);
            console.log(res);
        });
    }

    setPage(i: number, event: any) {
        event.preventDefault();
        this.curPage = i;
        this.getVisits(this.curPage);
    }
}
