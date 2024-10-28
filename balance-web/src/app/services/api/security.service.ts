import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { LoginUser } from "../security/login-user.state";

const URL = `${environment.baseUrl}/security`

@Injectable({providedIn: 'root'})
export class SecurityService {

  constructor(private http:HttpClient) {}

  signIn(form:any) {
    return this.http.post<LoginUser>(`${URL}/signin`, form)
  }

  signUp(form:any) {
    return this.http.post<LoginUser>(`${URL}/signup`, form)
  }

  refresh(form:any) {
    return this.http.post<LoginUser>(`${URL}/refresh`, form)
  }

  validate(form:any) {
    return this.http.post<any>(`${URL}/validate`, form)
  }

}
