import { Component, OnInit } from '@angular/core';
import { OverlayContainer } from '@angular/cdk/overlay';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/common/service/storage.service';
import { InteractionService } from 'src/app/common/service/Interaction.service';

@Component({
    selector: 'app-project',
    templateUrl: './project.component.html',
    styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {

    tabLoadTimes: Date[] = [];

    getTimeLoaded(index: number) {
        if (!this.tabLoadTimes[index]) {
            this.tabLoadTimes[index] = new Date();
        }
        return this.tabLoadTimes[index];
    }

    constructor(
        private overlayContainer: OverlayContainer,
        private router: Router,
        private storage: StorageService,
        private interactionService: InteractionService
    ) {
    }

    ngOnInit(): void {
        this.interactionService.pageChange('Project');
    }

}
