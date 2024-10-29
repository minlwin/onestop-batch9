import { ErrorHandler, Injectable, signal } from "@angular/core";
import { NeedToLoginError } from "../security/token-refresh.interceptor";

@Injectable()
export class GlobalErrorService implements ErrorHandler{

  errors = signal<string[] | undefined>(undefined)
  needToLogin = signal<boolean>(false)

  handleError(error: any): void {
    console.error(error)

    this.needToLogin.set(error instanceof NeedToLoginError)

    if(error.error) {
      this.errors.set(error.error)
    }
  }
}
