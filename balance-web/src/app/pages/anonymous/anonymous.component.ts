import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoginUserState } from '../../services/security/login-user.state';

@Component({
  selector: 'app-anonymous',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './anonymous.component.html',
  styles: ``
})
export class AnonymousComponent {

}
