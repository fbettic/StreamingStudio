import { Component } from '@angular/core';

@Component({
  selector: 'app-splash-screen',
  standalone: true,
  imports: [],
  templateUrl: './splash-screen.component.html',
  styleUrl: './splash-screen.component.scss',
})
export class SplashScreenComponent {
  show: boolean = false;

  constructor() {
    if (!sessionStorage.getItem('splash') || sessionStorage.getItem('splash')==="false") {
      this.show = true;
      this.showSplashScreen();
      sessionStorage.setItem('splash', 'true');
    }
  }

  showSplashScreen() {
    setTimeout(() => {
      const logo = document.getElementById('logo');
      if (logo) {
        logo.classList.add('show');
      }
    }, 100);

    setTimeout(() => {
      const splashScreen = document.getElementById('splash-screen');
      if (splashScreen) {
        splashScreen.classList.add('hide');
      }
    }, 3500);

    setTimeout(() => {
      this.show = false
    }, 4000);
  }
}
