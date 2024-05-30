import { Component, inject } from '@angular/core';
import { LoadingScreenService } from '../../services/loading-screen.service';

@Component({
  selector: 'app-loading-screen',
  standalone: true,
  imports: [],
  templateUrl: './loading-screen.component.html',
  styleUrl: './loading-screen.component.scss',
})
export class LoadingScreenComponent {
  private _loadingScreenService: LoadingScreenService =
    inject(LoadingScreenService);
  show: boolean = true;

  constructor() {
    this._loadingScreenService.loading.subscribe((show) => {
      if (show) {
        this.show = show;
      } else {
        const loadingScreen = document.getElementById('loading-screen');
        if (loadingScreen) {
          loadingScreen.classList.add('hide');
        }
        setTimeout(() => {
          this.show = show;
        }, 500);
      }
    });
  }
}
