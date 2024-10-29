import { AfterViewInit, Component, computed, effect, ErrorHandler, Inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { LoginUserState } from './services/security/login-user.state';
import { GlobalErrorService } from './services/commons/global-error.service';

declare const bootstrap:any

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styles: [],
})
export class AppComponent implements AfterViewInit{

  messages = computed(() => this.errorService.errors())
  needToLogin = computed(() => this.errorService.needToLogin())

  errorDialog:any

  constructor(
    loginUserSate:LoginUserState,
    router:Router,
    @Inject(ErrorHandler)
    private errorService:GlobalErrorService
  ) {
    effect(() => {

      if(this.needToLogin()) {
        router.navigate(['/anonymous'])
      }

      if(this.messages()) {
        this.errorDialog?.show()
      } else if(loginUserSate.role() == undefined) {
          router.navigate(['/anonymous'])
      }
    })
  }

  ngAfterViewInit(): void {
    this.errorDialog = new bootstrap.Modal('#errorDialog')
  }

}
