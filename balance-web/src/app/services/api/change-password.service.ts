import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient } from "@angular/common/http";
import { LoginUser } from "../security/login-user.state";

const URL = `${environment.baseUrl}/password`

@Injectable({providedIn: 'root'})
export class ChangePasswordService {

  constructor(private http:HttpClient) {}

  changePassword(form:any) {
    return this.http.post<LoginUser>(URL, form)
  }
}
