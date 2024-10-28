import { ErrorHandler, Injectable, signal } from "@angular/core";

@Injectable()
export class GlobalErrorService implements ErrorHandler{

  errors = signal<string[] | undefined>(undefined)

  handleError(error: any): void {
    if(error.error) {
      this.errors.set(error.error)
      return
    }
    console.error(error)
  }
}
