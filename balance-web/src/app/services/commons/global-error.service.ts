import { ErrorHandler, Injectable, signal } from "@angular/core";
import { NeedToLoginError } from "../security/token-refresh.interceptor";

@Injectable()
export class GlobalErrorService implements ErrorHandler{

  errors = signal<string[] | undefined>(undefined)
  needToLogin = signal<boolean>(false)

  handleError(error: any): void {
    this.needToLogin.set(false)
    console.error(error)
    if(error.error) {
      this.errors.set(error.error)
    }

    if(error instanceof NeedToLoginError) {
      this.needToLogin.set(true)
    }
  }
}
