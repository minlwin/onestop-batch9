import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { LoginUserState } from '../../services/security/login-user.state';

@Component({
  selector: 'app-members',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './members.component.html',
  styles: ``
})
export class MembersComponent {

  constructor(private loginUser:LoginUserState, private router:Router) {
  }

  signOut() {
    this.loginUser.signOut()
    this.router.navigate(['/anonymous'])
  }
}
