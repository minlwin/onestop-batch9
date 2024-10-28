import { Component, effect } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LoginUserState } from './services/security/login-user.state';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styles: [],
})
export class AppComponent {

  constructor(loginUserSate:LoginUserState, router:Router) {
    effect(() => {
      const role = loginUserSate.role()

      if(role == "Member") {
        router.navigate(['/member'])
      } else if(role == "Admin") {
        router.navigate(['/admin'])
      } else {
        router.navigate(['/anonymous'])
      }
    })
  }
}
